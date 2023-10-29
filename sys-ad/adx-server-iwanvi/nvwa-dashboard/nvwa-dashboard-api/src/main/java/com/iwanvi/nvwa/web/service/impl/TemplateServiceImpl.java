package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdPositionService;
import com.iwanvi.nvwa.web.service.SysCrontabService;
import com.iwanvi.nvwa.web.service.TemplateModuleRelationService;
import com.iwanvi.nvwa.web.service.TemplateService;
import com.iwanvi.nvwa.web.util.ControllerException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwanvi.nvwa.web.util.MatcherUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	private TemplateMapper templateMapper;
	@Autowired
	private AdPositionMapper adPositionMapper;
	@Autowired
	private AdPositionService adPositionService;
	@Autowired
	private TemplateModuleRelationService templateModuleRelationService;
	@Autowired
	private TemplateModuleRelationMapper templateModuleRelationMapper;
	@Autowired
	private ModuleMapper moduleMapper;
	@Autowired
	private SysCrontabService sysCrontabService;
	@Autowired
	private EntityMapper entityMapper;

	private static final Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);

	@Override
	@Transactional()
	public void add(Template template) {
		if (template != null) {
			if (StringUtils.isBlank(template.getName().trim())) {
				throw new ControllerException("请输入正确的模版名！");
			}
			template.setCreateTime(new Date());
			template.setStatus(Constants.STATE_VALID);
			templateMapper.insert(template);
			templateMapper.countByExample(null);
			for (TemplateModuleRelation templateModuleRelation : template.getModuleList()) {
				if (checkModule(templateModuleRelation)) {
					templateModuleRelation.settId(template.getId());
					templateModuleRelation.setCreateUser(template.getCreateUser());
					templateModuleRelation.setCreateTime(new Date());
					templateModuleRelation.setStatus(Constants.STATE_VALID);
					templateModuleRelationService.add(templateModuleRelation);
				}
			}
		}
	}

	@Override
	@Transactional()
	public void update(Template template) {
		template.setUpdateTime(new Date());
		templateMapper.updateByPrimaryKeySelective(template);
		for (TemplateModuleRelation templateModuleRelation : template.getModuleList()) {
			// 如果组件关系id为空说明是新组件，插入
			if (templateModuleRelation.getId() == null) {
				if (checkModule(templateModuleRelation)) {
					templateModuleRelation.settId(template.getId());
					templateModuleRelation.setCreateUser(template.getCreateUser());
					templateModuleRelation.setCreateTime(new Date());
					templateModuleRelation.setStatus(Constants.STATE_VALID);
					templateModuleRelationService.add(templateModuleRelation);
				}
				// id不为空则更新组件
			} else {
				templateModuleRelation.setUpdateUser(template.getCreateUser());
				templateModuleRelation.setUpdateTime(new Date());
				// 判断组件是否需要删除
				if (!checkModule(templateModuleRelation)) {
					templateModuleRelation.setStatus(Constants.STATE_INVALID);
				}
				templateModuleRelationService.update(templateModuleRelation);
			}
		}
		// 修改模版,模版下所有广告位发消息同步到引擎更新redis
		AdPositionExample positionExample = new AdPositionExample();
		positionExample.createCriteria().andTemplateIdLike("%," + template.getId() + ",%");
		List<AdPosition> positionList = adPositionMapper.selectByExample(positionExample);
		for (AdPosition adPosition : positionList) {
			sysCrontabService.addSysCrontab(adPosition.getId(), Constants.OBJ_APP_POSITION, Constants.OP_UPDATE);
			logger.info("insert adposition sysCrontab success ! positionId:{}", adPosition.getId());
			adPositionService.writeToRedis(adPosition);
		}
	}

	/**
	 * 删除模版：需要先删除广告位底价，广告位，以及模版组件
	 *
	 * @param id
	 * @param userId
	 */
	@Override
	@Transactional()
	public void delete(Integer id, Integer userId) {
		if (id != null) {
			Template template = new Template();
			template.setId(id);
			template.setStatus(Constants.STATE_INVALID);
			template.setUpdateUser(userId);
			template.setUpdateTime(new Date());
			// 先遍历删除该模版下的广告位以及广告位下的底价
			AdPosition adPosition = new AdPosition();
			adPosition.setStatus(Constants.STATE_INVALID);
			adPosition.setUpdateTime(new Date());
			AdPositionExample example = new AdPositionExample();
//            example.createCriteria().andFlowPositionIdEqualTo(id);
			List<AdPosition> list = adPositionMapper.selectByExample(example);
			AdPosition tempAdposition = new AdPosition();
			for (AdPosition position : list) {
				// 删除广告位及底价
				adPosition.setId(position.getId());
				adPositionService.delete(adPosition);
			}
			templateMapper.updateByPrimaryKey(template);
		}
	}

	@Override
	public Template get(Integer id) {
		if (id != null) {
			Template template = templateMapper.selectByPrimaryKey(id);
			TemplateModuleRelationExample example = new TemplateModuleRelationExample();
			example.createCriteria().andTIdEqualTo(template.getId());
			List<TemplateModuleRelation> list = templateModuleRelationService.get(id);
			// 反显格式
			List<TemplateModuleRelation> respList = Lists.newArrayList();
			for (int i = 1; i <= 8; i++) {
				TemplateModuleRelation module = new TemplateModuleRelation();
				module.setmId(i);
				respList.add(module);
			}
			for (TemplateModuleRelation templateModuleRelation : list) {
				respList.set(templateModuleRelation.getmId() - 1, templateModuleRelation);
			}
			template.setModuleList(respList);
			return template;
		}
		return null;
	}

	@Override
	public List<Template> selectForList(Template template) {
		TemplateExample example = bindingExample(template);
		return templateMapper.selectByExample(example);
	}

	@Override
	public Page<Template> selectForPage(Template template, Integer cp, Integer ps) {
		Page<Template> page;
		int count = templateMapper.countTemplate(template);
		List<Template> list = Lists.newArrayList();
		if (cp != null && ps != null) {
			page = new Page<Template>(count, cp, ps);
		} else {
			page = new Page<Template>(count);
		}
		template.setStart(page.getStartPosition());
		template.setLimit(page.getPageSize());
		list = templateMapper.selectTemplate(template);
		// 查询模版包含的素材类型和 在用数量
		for (Template template1 : list) {
			TemplateModuleRelationExample example = new TemplateModuleRelationExample();
			example.createCriteria().andTIdEqualTo(template1.getId()).andStatusEqualTo(Constants.STATE_VALID);
			List<TemplateModuleRelation> relationList = templateModuleRelationMapper.selectByExample(example);
			StringBuilder sb = new StringBuilder();
			// 素材类型
			for (TemplateModuleRelation templateModuleRelation : relationList) {
				sb.append(moduleMapper.selectByPrimaryKey(templateModuleRelation.getmId()).getName());
				sb.append(Constants.SIGN_COMMA);
			}
			template1.setMaterialName(
					sb.lastIndexOf(Constants.SIGN_COMMA) > 0 ? sb.substring(0, sb.lastIndexOf(Constants.SIGN_COMMA))
							: sb.toString());

			// 在用广告位数量
			AdPositionExample positionExample = new AdPositionExample();
			positionExample.createCriteria().andTemplateIdLike("%," + template1.getId() + ",%");
			int positionCount = adPositionMapper.countByExample(positionExample);
			template1.setPositionNum(positionCount);
		}
		page.bindData(list);
		return page;

	}

	/**
	 * 检查模版名称是否存在
	 *
	 * @param template
	 * @return
	 */
	@Override
	public boolean checkTemplateExist(Template template) {
		boolean isExists = false;
		if (template != null) {
			TemplateExample example = new TemplateExample();
			example.createCriteria().andNameEqualTo(template.getName());
			List<Template> list = templateMapper.selectByExample(example);
			if (list != null) {
				if (!(list.size() == 1 && list.get(0).getId().equals(template.getId())) && list.size() != 0) {
					isExists = true;
				}
			}
		}
		return isExists;
	}

	@Override
	public void updateStatus(Template template) throws ServerException {
		if (template == null) {
			throw new ServerException("参数错误！");
		}
		template.setUpdateTime(new Date());
		templateMapper.updateByPrimaryKeySelective(template);
	}

	@Override
	public boolean modifiable(Integer id) {
		EntityExample example = new EntityExample();
		example.createCriteria().andTemplateIdEqualTo(id);
		List<Entity> list = entityMapper.selectByExample(example);
		return list.size() > 0 ? false : true;
	}

	@Override
	public List<Template> listByPosition(Integer positionId) {
		AdPosition adPosition = adPositionMapper.selectByPrimaryKey(positionId);
		List<String> list = StringUtils.str2List(adPosition.getTemplateId(), Constants.SIGN_COMMA);
		List<Integer> templateIds = Lists.transform(list, id -> StringUtils.isBlank(id) ? null : Integer.parseInt(id));
		TemplateExample example = new TemplateExample();
		example.createCriteria().andIdIn(templateIds).andStatusEqualTo(Constants.STATE_VALID);
		return templateMapper.selectByExample(example);
	}

	@Override
	public boolean modifiable(Integer id, Integer positionId) {
		if (id == null && positionId == null) {
			throw new ServiceException("参数错误");
		}
		EntityExample example = new EntityExample();
		example.createCriteria().andTemplateIdEqualTo(id).andAdPositionEqualTo(positionId)
				.andEntityStateNotEqualTo(Constants.STATE_INVALID);
		List<Entity> list = entityMapper.selectByExample(example);
		return list.size() > 0 ? false : true;
	}

	/**
	 * 绑定example
	 *
	 * @param template
	 * @return
	 */
	private TemplateExample bindingExample(Template template) {
		TemplateExample example = new TemplateExample();
		if (template != null) {
			TemplateExample.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(template.getName())) {
				criteria.andNameLike("%" + template.getName() + "%");
			}
		}
		example.setOrderByClause("status desc,id desc");
		return example;
	}

	/**
	 * 检查是否为 有效模版
	 *
	 * @param module
	 * @return
	 */
	private boolean checkModule(TemplateModuleRelation module) {
		Template template = templateMapper.selectByPrimaryKey(module.gettId());
		if (module.getmId() == 1 || module.getmId() == 2 || module.getmId() == 8) {
			if (module.getWordLimit() == null) {
				return false;
			}
			if (!MatcherUtils.isPositiveInteger(module.getWordLimit().toString())) {
				throw new ControllerException("请输入正确的数字");
			}
		} else if (module.getmId() == 4 && template != null && template.getType() != 217) {
			if (module.getWidth() == null || module.getHeight() == null || module.getDuration() == null) {
				throw new ControllerException("缺少宽高或时长");
			}
			if (!MatcherUtils.isPositiveInteger(module.getWidth().toString())
					&& !MatcherUtils.isPositiveInteger(module.getHeight().toString())
					&& !MatcherUtils.isPositiveInteger(module.getDuration().toString())) {
				throw new ControllerException("请输入正确的数字");
			}
			return true;
		} else {
			if (module.getmId() == 5 && module.getHeight() == null && module.getWidth() == null) {
				throw new ControllerException("主图片必填!");
			}
			if (module.getHeight() == null && module.getWidth() == null) {
				return false;
			}
			if ((module.getWidth() != null && module.getHeight() == null)
					|| (module.getWidth() == null && module.getHeight() != null)) {
				throw new ControllerException("缺少宽或高");
			}
			if (!MatcherUtils.isPositiveInteger(module.getWidth().toString())
					|| !MatcherUtils.isPositiveInteger(module.getHeight().toString())) {
				throw new ControllerException("请输入正确的数字");
			}
		}
		return true;
	}

	/**
	 * 检查组建是否需要删除
	 * 
	 * @param templateModuleRelation
	 * @return
	 */
	private boolean moduleNeedDel(TemplateModuleRelation templateModuleRelation) {
		return templateModuleRelation.getHeight() == null && templateModuleRelation.getWidth() == null
				&& templateModuleRelation.getCode() == null && templateModuleRelation.getWordLimit() == null;
	}

}
