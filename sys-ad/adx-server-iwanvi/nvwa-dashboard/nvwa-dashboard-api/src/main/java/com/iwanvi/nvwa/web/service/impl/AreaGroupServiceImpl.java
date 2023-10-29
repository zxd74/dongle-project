package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdPositionPriceMapper;
import com.iwanvi.nvwa.dao.AreaGroupMapper;
import com.iwanvi.nvwa.dao.AreaMapper;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.web.service.*;
import com.iwanvi.nvwa.web.util.ControllerException;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * class
 *
 * @author hao
 * @date 2018/10/16.
 */
@Service
public class AreaGroupServiceImpl implements AreaGroupService {

    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private AreaGroupMapper areaGroupMapper;
    @Autowired
    private AdDicService adDicService;
    @Autowired
    private AdPositionPriceMapper adPositionPriceMapper;
    @Autowired
    private AgentPriceService agentPriceService;
    @Autowired
    private AdPositionPriceService adPositionPriceService;
    @Autowired
    private SysCrontabService sysCrontabService;

    private List<Integer> sellTypeList = Lists.newArrayList(WebConstants.SELL_TYPE_CPM,WebConstants.SELL_TYPE_CPC);

    @Override
    @Transactional
    public void add(AreaGroup areaGroup) {
        if (areaGroup != null) {
            if (StringUtils.isBlank(areaGroup.getGroupName().trim())) {
                throw new ControllerException("请输入地域名！");
            }
            areaGroup.setCreateTime(new Date());
            areaGroup.setStatus(Constants.STATE_VALID);
            areaGroupMapper.insert(areaGroup);
            areaGroupMapper.countByExample(null);

            //获取有效广告位
            AdPosition adPosition = new AdPosition();
            adPosition.setStatus(Constants.STATE_VALID);
            List<AdPositionPrice> positionList = adPositionPriceService.getPositionByPrice();
            //获取行业
            List<Dictionary> industryList = adDicService.getDicByGroupId(Constants.ADVERTIDER_INDUSTRY);
            //添加对应地域的底价

            for (AdPositionPrice price1 : positionList) {
                for (Dictionary industry : industryList) {
                    //底价维度暂时去掉售卖类型
//                    for (Integer sellType : sellTypeList) {
//                        price.setSellType(sellType);
                    AdPositionPrice price = new AdPositionPrice();
                    price.setAreaLevel(areaGroup.getId());
                    price.setUpdateUser(areaGroup.getCreateUser());
                    price.setUpdateTime(new Date());
                    price.setStatus(Constants.STATE_VALID);
                    price.setPositionId(price1.getPositionId());
                    price.setIndustry(industry.getId());
                    price.setPrice(Constants.PRICE_DEFAULT_10);
                    adPositionPriceMapper.insert(price);
                    adPositionPriceMapper.countByExample(null);
                    sysCrontabService.addSysCrontab(price.getId(), Constants.OBJ_AD_POSITION_PRICE, Constants.OP_ADD);
                }
            }
            //调用 代理商底价接口
            agentPriceService.addPriceWithArea(areaGroup.getId());
            sysCrontabService.addSysCrontab(areaGroup.getId(), Constants.OBJ_AREA_GROUP, Constants.OP_ADD);
        }
    }

    @Override
    @Transactional
    public void update(AreaGroup areaGroup) {
        if (areaGroup != null && areaGroup.getId() != null) {
            areaGroupMapper.updateByPrimaryKeySelective(areaGroup);
			sysCrontabService.addSysCrontab(areaGroup.getId(), Constants.OBJ_AREA_GROUP, Constants.OP_UPDATE);
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (id != null) {
            //先删除底价
            AdPositionPriceExample example = new AdPositionPriceExample();
            example.createCriteria().andAreaLevelEqualTo(id);
            List<AdPositionPrice> list = adPositionPriceMapper.selectByExample(example);
            for (AdPositionPrice adPositionPrice : list) {
                adPositionPriceMapper.deleteByPrimaryKey(adPositionPrice.getId());
                sysCrontabService.addSysCrontab(adPositionPrice.getId(), Constants.OBJ_AK_ADP, Constants.OP_REMOVE);
            }
            agentPriceService.rmPriceWithArea(id);
            areaGroupMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public AreaGroup get(Integer id) {
        AreaGroup areaGroup = areaGroupMapper.selectByPrimaryKey(id);
        if (areaGroup != null && StringUtils.isNotBlank(areaGroup.getAreaIds())) {
            List<Integer> areaIds = StringUtils.str2List4Int(areaGroup.getAreaIds(), Constants.SIGN_COMMA);
            AreaExample areaExample = new AreaExample();
            areaExample.createCriteria().andIdIn(areaIds);
            areaGroup.setAreaList(areaMapper.selectByExample(areaExample));

            return areaGroup;
        }
        return null;
    }

    @Override
    public List<AreaGroup> selectForList(AreaGroup areaGroup) {
        AreaGroupExample example = new AreaGroupExample();
        AreaGroupExample.Criteria criteria = example.createCriteria();
        if (areaGroup != null) {
            if (areaGroup.getGroupType() != null) {
                criteria.andGroupTypeEqualTo(areaGroup.getGroupType());
            }
        }
        criteria.andStatusEqualTo(Constants.STATE_VALID);
        List<AreaGroup> list = areaGroupMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<Area> selectAreaList(String areaName) {
        AreaExample example = new AreaExample();
        example.createCriteria().andAreaNameLike("%" + areaName + "%");
        return areaMapper.selectByExample(example);
    }

    /**
     * 检查areaGroup名称是否存在
     */
    @Override
    public boolean checkAreaGroupExist(AreaGroup areaGroup) {
        boolean isExists = false;
        if ( areaGroup != null) {
            AreaGroupExample example = new AreaGroupExample();
            example.createCriteria().andGroupNameEqualTo(areaGroup.getGroupName()).andGroupTypeEqualTo(areaGroup.getGroupType());
            List<AreaGroup> list = areaGroupMapper.selectByExample(example);
            if (list != null) {
                if ( !(list.size() == 1 && list.get(0).getId().equals(areaGroup.getId())) && list.size() != 0) {
                    isExists = true;
                }
            }
        }
        return isExists;
    }

    /**
     * 绑定example
     * @param areaGroup
     * @return
     */
    /*private AreaGroupExample bindingExample(AreaGroup areaGroup) {
        AreaGroupExample example = new AreaGroupExample();
        if ( areaGroup != null) {
            AreaGroupExample.Criteria criteria = example.createCriteria();
            if (areaGroup.getGroupType() != null) {
                criteria.andGroupTypeEqualTo(areaGroup.getGroupType());
            }
        }
        return example;
    }*/
}
