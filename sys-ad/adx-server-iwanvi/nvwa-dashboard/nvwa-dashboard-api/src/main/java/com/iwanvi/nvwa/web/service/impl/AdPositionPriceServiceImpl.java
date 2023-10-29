package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.*;
import com.iwanvi.nvwa.web.util.ControllerException;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * service: 广告位底价管理
 *
 * @author hao
 * @date 2018/10/15.
 */
@Service
public class AdPositionPriceServiceImpl implements AdPositionPriceService {

    private static final Logger logger = LoggerFactory.getLogger(AdPositionPriceServiceImpl.class);

    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private AreaGroupMapper areaGroupMapper;
    @Autowired
    private AdPositionPriceMapper adPositionPriceMapper;
    @Autowired
    private AdPositionMapper adPositionMapper;
    @Autowired
    private AdDicService adDicService;
    @Autowired
    private AdPositionService adPositionService;
    @Autowired
    private AgentPriceService agentPriceService;
    @Autowired
    private SysCrontabService sysCrontabService;
    @Autowired
    private IndustryMapper industryMapper;
    @Autowired
    private AppsMapper appsMapper;

    private List<Integer> sellTypeList = Lists.newArrayList(WebConstants.SELL_TYPE_CPM,WebConstants.SELL_TYPE_CPC);

    @Override
    @Transactional
    public void add(AdPositionPrice adPositionPrice) {
        if (adPositionPrice != null && adPositionPrice.getPositionId() != null) {

            //判断该广告位底价是否存在
            AdPositionPriceExample example = new AdPositionPriceExample();
            example.createCriteria().andPositionIdEqualTo(adPositionPrice.getPositionId()).andAppIdEqualTo(adPositionPrice.getAppId())
            .andAreaLevelEqualTo(adPositionPrice.getAreaLevel()).andIndustryEqualTo(adPositionPrice.getIndustry());
            List<AdPositionPrice> list = adPositionPriceMapper.selectByExample(example);
            if (list.size() > 0) {
                throw new ControllerException("该底价已存在");
            }
            adPositionPrice.setUpdateTime(new Date());
            adPositionPrice.setStatus(Constants.STATE_VALID);
            adPositionPrice.setPrice(adPositionPrice.getPrice() * 100);
            adPositionPriceMapper.insert(adPositionPrice);
            adPositionPriceMapper.countByExample(null);
            sysCrontabService.addSysCrontab(adPositionPrice.getId(), Constants.OBJ_AD_POSITION_PRICE, Constants.OP_ADD);
            logger.info("insert into sysCrontab success! priceId:{}",adPositionPrice.getId());
        }
    }

    @Override
    @Transactional
    public void update(AdPositionPrice adPositionPrice) {
        if (adPositionPrice != null) {
            if (adPositionPrice.getPrice() != null) {
                adPositionPrice.setPrice(adPositionPrice.getPrice() * 100);
            }
            adPositionPrice.setUpdateTime(new Date());
            adPositionPriceMapper.updateByPrimaryKeySelective(adPositionPrice);
            sysCrontabService.addSysCrontab(adPositionPrice.getId(), Constants.OBJ_AD_POSITION_PRICE, Constants.OP_UPDATE);
            logger.info("update sysCrontab price success! priceId:{}",adPositionPrice.getId());
            adPositionPrice = adPositionPriceMapper.selectByPrimaryKey(adPositionPrice.getId());
            
            sysCrontabService.addSysCrontab(adPositionPrice.getPositionId(), Constants.OBJ_APP_POSITION, Constants.OP_UPDATE);
            logger.info("update sysCrontab position success! positionId:{}",adPositionPrice.getPositionId());
        }
    }

    @Override
    @Transactional
    public void delete(Integer id, Integer userId) {
        if (id != null) {
            AdPositionPrice adPositionPrice = new AdPositionPrice();
            adPositionPrice.setId(id);
            adPositionPrice.setUpdateUser(userId);
            adPositionPrice.setStatus(Constants.STATE_INVALID);
            adPositionPrice.setUpdateTime(new Date());
            adPositionPriceMapper.updateByPrimaryKeySelective(adPositionPrice);
        }
    }

    @Override
    @Transactional
    public void deleteByPosition(AdPosition adPosition) {
        if (adPosition != null) {
            AdPositionPrice adPositionPrice = new AdPositionPrice();
            if (adPosition.getId() != null) {
                adPositionPrice.setPositionId(adPosition.getId());
            }
            adPositionPrice.setUpdateUser(adPosition.getUpdateUser());
            adPositionPrice.setUpdateTime(new Date());
            adPositionPrice.setStatus(Constants.STATE_INVALID);
            adPositionPriceMapper.updateByPrimaryKeySelective(adPositionPrice);
        }
    }

    @Override
    public AdPositionPrice get(Integer id) {
        return adPositionPriceMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AdPositionPrice> selectForList(AdPositionPrice adPositionPrice) {
        AdPositionPriceExample example = bindingExample(adPositionPrice);
        return adPositionPriceMapper.selectByExample(example);
    }

    @Override
    public Page<AdPositionPrice> selectForPage(AdPositionPrice adPositionPrice, Integer cp, Integer ps) {
        Page<AdPositionPrice> page;
        AdPositionPriceExample example = bindingExample(adPositionPrice);
        int count = adPositionPriceMapper.countByExample(example);
        List<AdPositionPrice> list = Lists.newArrayList();
        if (cp != null && ps != null) {
            page = new Page<AdPositionPrice>(count, cp, ps);
        } else {
            page = new Page<AdPositionPrice>(count);
        }
        example.setStart(page.getStartPosition());
        example.setLimit(page.getPageSize());
        list = adPositionPriceMapper.selectByExample(example);
        for (AdPositionPrice price : list) {
            price.setPositionName(adPositionService.get(price.getPositionId()).getName());
            price.setPrice(price.getPrice() / 100);
            if (price.getIndustry() != null) {
                price.setIndustryName(industryMapper.selectByPrimaryKey(price.getIndustry()).getName());
            }
            if (price.getAreaLevel() != null) {
                price.setAreaLevelName(areaGroupMapper.selectByPrimaryKey(price.getAreaLevel()).getGroupName());
            }
            if (price.getPositionId() != null) {
                AdPosition adPosition = adPositionMapper.selectByPrimaryKey(price.getPositionId());
            }
            if (price.getAppId() != null) {
                price.setAppName(appsMapper.selectByPrimaryKey(price.getAppId()).getAppName());
            }
        }
        page.bindData(list);
        return page;
    }

    /**
     * check 底价是否存在
     * @return
     */
    @Override
    public boolean checkPriceExist(AdPositionPrice price) {
        boolean isExists = false;
        if ( price != null ) {
            AdPositionPriceExample example = new AdPositionPriceExample();
            example.createCriteria().andPositionIdEqualTo(price.getPositionId()).andAppIdEqualTo(price.getAppId())
                .andAreaLevelEqualTo(price.getAreaLevel()).andIndustryEqualTo(price.getIndustry());
             int count  = adPositionPriceMapper.countByExample(example);
            if (count > 0) {
                isExists = true;
            }
        }
        return isExists;
    }

    @Override
    @Transactional
    public void addIndustry(String industryName) {
        if (StringUtils.isNotBlank(industryName)) {
            Dictionary dictionary = new Dictionary();
            dictionary.setDicValue(industryName);
            adDicService.addIndustry(dictionary);
            DictionaryExample example = new DictionaryExample();
            example.createCriteria().andDicValueEqualTo(industryName).andDicGroupEqualTo(Constants.ADVERTIDER_INDUSTRY);
            List<Dictionary> list = dictionaryMapper.selectByExample(example);
            Integer id = list.get(0).getId();
            //调用代理商低价接口
            agentPriceService.addPriceWithIndustry(id);
            //添加该行业相关底价
            if (id != null) {
                //获取已经设置底价的广告位
                List<AdPositionPrice> positionList = getPositionByPrice();
                //获取地域等级列表
                AreaGroupExample groupExample = new AreaGroupExample();
                groupExample.createCriteria().andGroupTypeEqualTo(Constants.INTEGER_2).andStatusEqualTo(Constants.STATE_VALID);
                List<AreaGroup> groupList =  areaGroupMapper.selectByExample(groupExample);
                //遍历添加底价
                AdPositionPrice price = new AdPositionPrice();
                price.setIndustry(id);
                price.setStatus(Constants.STATE_VALID);
                for (AdPositionPrice position : positionList) {
                    for (AreaGroup areaGroup : groupList) {
                        //底价维度暂时去掉售卖类型
//                        for (Integer sellType : sellTypeList) {
//                            price.setSellType(sellType);
                        price.setPositionId(position.getPositionId());
                        price.setAreaLevel(areaGroup.getId());
                        price.setPrice(Constants.PRICE_DEFAULT_10);
                        adPositionPriceMapper.insert(price);
                        AdPositionPriceExample priceExample = new AdPositionPriceExample();
                        priceExample.createCriteria().andPositionIdEqualTo(price.getPositionId())
                                .andIndustryEqualTo(price.getIndustry()).andAreaLevelEqualTo(price.getAreaLevel());
                        List<AdPositionPrice> prices = adPositionPriceMapper.selectByExample(priceExample);
                        if (prices.size() > 0) {
                            sysCrontabService.addSysCrontab(prices.get(0).getId(), Constants.OBJ_AD_POSITION_PRICE, Constants.OP_ADD);
                        }
//                        }
                    }
                }
            }
        }
    }

    /**
     * 绑定example
     * @param price
     * @return
     */
    private AdPositionPriceExample bindingExample(AdPositionPrice price) {
        AdPositionPriceExample example = new AdPositionPriceExample();
        AdPositionPriceExample.Criteria criteria = example.createCriteria();
        List<Integer> list ;
        if (price != null) {
            if (price.getIndustry() != null) {
                criteria.andIndustryEqualTo(price.getIndustry());
            }
            if (price.getTerminal() != null) {
                AdPositionExample positionExample1 = new AdPositionExample();
                positionExample1.createCriteria().andTerminalEqualTo(price.getTerminal());
                List<AdPosition> positionList1 = adPositionMapper.selectByExample(positionExample1);
                List<Integer> posIds = Lists.transform(positionList1, AdPosition::getId);
                criteria.andPositionIdIn(CollectionUtils.isEmpty(posIds) ? Lists.newArrayList(Integer.MAX_VALUE) : posIds);
            }
            if (price.getAreaLevel() != null) {
                criteria.andAreaLevelEqualTo(price.getAreaLevel());
            }
            if (StringUtils.isNotBlank(price.getPositionName())) {
                AdPositionExample positionExample = new AdPositionExample();
                positionExample.createCriteria().andNameLike("%" + price.getPositionName() + "%");
                List<AdPosition> positionList = adPositionMapper.selectByExample(positionExample);
                list = Lists.newArrayList();
                List<Integer> ids = Lists.transform(positionList, AdPosition::getId);
                list.addAll(ids);
                if (CollectionUtils.isEmpty(list)) {
                    list.add(Integer.MAX_VALUE);
                }
                criteria.andPositionIdIn(list);
            }
            criteria.andStatusEqualTo(Constants.STATE_VALID);
        }
        return example;
    }

    /**
     * 查看行业名称是否存在
     * @param industryName
     * @return
     */
    @Override
    public boolean checkIndustryExist(String industryName) {
        boolean isExists = false;
        if (StringUtils.isNotBlank(industryName)) {
            DictionaryExample example = new DictionaryExample();
            example.createCriteria().andDicGroupEqualTo(Constants.ADVERTIDER_INDUSTRY).andDicValueEqualTo(industryName);
            int count = dictionaryMapper.countByExample(example);
            if (count > 0) {
                isExists = true;
            }
        }
        return isExists;
    }

    @Override
    public List<AdPositionPrice> getPositionByPrice() {
        return adPositionPriceMapper.getPositionByPrice();
    }

    @Override
    @Transactional
    public void deleteIndustry(Integer id) {
        if ( id != null) {
            AdPositionPriceExample example = new AdPositionPriceExample();
            example.createCriteria().andIndustryEqualTo(id);
            List<AdPositionPrice> list = adPositionPriceMapper.selectByExample(example);
            for (AdPositionPrice adPositionPrice : list) {
                adPositionPriceMapper.deleteByPrimaryKey(adPositionPrice.getId());
                sysCrontabService.addSysCrontab(adPositionPrice.getId(), Constants.OBJ_AD_POSITION_PRICE, Constants.OP_UPDATE);
            }
            dictionaryMapper.deleteByPrimaryKey(id);
            //调用代理商底价接口
            agentPriceService.rmPriceWithIndustry(id);
        }
    }
}
