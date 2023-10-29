package com.iwanvi.nvwa.web.service.impl;

import com.google.common.collect.Lists;
import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdPositionMappingMapper;
import com.iwanvi.nvwa.dao.AppsMapper;
import com.iwanvi.nvwa.dao.FlowConsumerMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionMapping;
import com.iwanvi.nvwa.dao.model.AdPositionMappingExample;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.web.service.AdPositionService;
import com.iwanvi.nvwa.web.service.PositionMapperingService;
import com.iwanvi.nvwa.web.util.MatcherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PositionMappingServiceImpl implements PositionMapperingService {

    @Autowired
    private AdPositionMappingMapper adPositionMappingMapper;
    @Autowired
    private FlowConsumerMapper flowConsumerMapper;

    @Override
    @Transactional
    public void insertOrUpdate(AdPositionMapping adPositionMapping) {

        checkParam(adPositionMapping);
        if (!checkMappingNumber(adPositionMapping)) {
            throw new ServiceException("请输入正确的数字");
        }
        if (StringUtils.isNotBlank(adPositionMapping.getConsumerPositionId())) {
            AdPositionMappingExample positionExample = new AdPositionMappingExample();
            positionExample.createCriteria().andConsumerPositionIdEqualTo(adPositionMapping.getConsumerPositionId());
            List<AdPositionMapping> List = adPositionMappingMapper.selectByExample(positionExample);
            if (!(List.size() == 1 && List.get(0).getId().equals(adPositionMapping.getId())) && List.size() != 0) {
                throw new ServiceException("平台广告位id已存在,请勿重复添加！");
            }
        }
        if (adPositionMapping.getForecastCtr() != null) {
            adPositionMapping.setForecastCtr(adPositionMapping.getForecastCtr() / 100);
        }
        if (adPositionMapping.getId() == null) {
            adPositionMapping.setCreateTime(new Date());
            adPositionMapping.setStatus(Constants.STATE_VALID);
            adPositionMappingMapper.insert(adPositionMapping);
            adPositionMappingMapper.countByExample(null);
            return;
        }
        adPositionMapping.setUpdateTime(new Date());
        adPositionMappingMapper.updateByPrimaryKey(adPositionMapping);
    }

    @Override
    @Transactional
    public void delete(Integer id, Integer uid) {
        if (id == null) {
            throw new ServiceException("参数错误！");
        }
        AdPositionMapping adPositionMapping = new AdPositionMapping();
        adPositionMapping.setId(id);
        adPositionMapping.setUpdateUser(uid);
        adPositionMapping.setUpdateTime(new Date());
        adPositionMapping.setStatus(Constants.STATE_INVALID);
        adPositionMappingMapper.updateByPrimaryKeySelective(adPositionMapping);
    }

    @Override
    public AdPositionMapping get(Integer id) {
        return null;
    }

    @Override
    public List<AdPositionMapping> selectForList(AdPositionMapping adPositionMapping) {
        adPositionMapping.setStatus(Constants.STATE_VALID);
        AdPositionMappingExample example = new AdPositionMappingExample();
        AdPositionMappingExample.Criteria criteria = example.createCriteria();
        if (adPositionMapping.getAppId() != null){
            criteria.andAppIdEqualTo(adPositionMapping.getAppId());
        }
        if (adPositionMapping.getFlowConsumerId() != null){
            criteria.andFlowConsumerIdEqualTo(adPositionMapping.getFlowConsumerId());
        }
        if (StringUtils.isNotBlank(adPositionMapping.getConsumerIds())) {
            criteria.andFlowConsumerIdIn(Lists.transform(StringUtils.str2List(adPositionMapping.getConsumerIds(), Constants.SIGN_COMMA), s -> Integer.parseInt(s)));
        }
        if (StringUtils.isNotBlank(adPositionMapping.getPositionIds())) {
            criteria.andAdPositionIdIn(Lists.transform(StringUtils.str2List(adPositionMapping.getPositionIds(), Constants.SIGN_COMMA), posId -> Integer.parseInt(posId)));
        }
        List<AdPositionMapping> list = adPositionMappingMapper.selectByExample(example);
        return list;
    }

    /**
     * 判断数字属性是否是数字
     * @param mapping
     * @return
     */
    private boolean checkMappingNumber(AdPositionMapping mapping) {
        boolean result = true;
        if (mapping.getForecastCtr() != null && !MatcherUtils.isPositionNumber(mapping.getForecastCtr().toString())) {
            result = false;
        }
        if (mapping.getForecastRtbPrice() != null && !MatcherUtils.isPositionNumber(mapping.getForecastRtbPrice().toString())) {
            result = false;
        }
        if (mapping.getDspFloor() != null && !MatcherUtils.isPositionNumber(mapping.getDspFloor().toString())) {
            result = false;
        }
        return result;
    }
    @Override
    public List<AdPositionMapping> selectForPage(AdPositionMapping adPositionMapping) {
        return null;
    }

    @Override
    @Transactional
    public void delete(AdPosition adPosition) {

        List<Integer> mappingListIds = Lists.transform(adPosition.getMappingList(),AdPositionMapping::getId);
        AdPositionMappingExample example = new AdPositionMappingExample();
        example.createCriteria().andAdPositionIdEqualTo(adPosition.getId());
        List<AdPositionMapping> oldList = adPositionMappingMapper.selectByExample(example);
        for (AdPositionMapping adPositionMapping : oldList) {
            if ( !mappingListIds.contains(adPositionMapping.getId())) {
                adPositionMappingMapper.deleteByPrimaryKey(adPositionMapping.getId());
            }
        }
    }

    private void checkParam(AdPositionMapping adPositionMapping) {
        if (adPositionMapping.getFlowConsumerId() == null) {
            throw new ServiceException("请选择平台");
        }
        FlowConsumer flowConsumer = flowConsumerMapper.selectByPrimaryKey(adPositionMapping.getFlowConsumerId());
        //如果为sdk 则广告位id必填
        if (flowConsumer.getConsumerType() == 97) {
            if (StringUtils.isBlank(adPositionMapping.getConsumerPositionId())) {
                throw new ServiceException("请填写平台广告位id");
            }
            if (StringUtils.isBlank(adPositionMapping.getConsumerPositionName())) {
                throw new ServiceException("请填写平台广告位名称");
            }
            if (adPositionMapping.getForecastRtbPrice() == null) {
                throw new ServiceException("请填写RTB出价");
            }
        } else {
            if (StringUtils.isBlank(adPositionMapping.getConsumerPositionId()) && adPositionMapping.getWidth() == null
                    && adPositionMapping.getHeight() == null) {
                throw new ServiceException("广告位id和宽高不能同时为空");
            }
            if ((adPositionMapping.getWidth() == null && adPositionMapping.getHeight() != null)
                    || ((adPositionMapping.getWidth() != null && adPositionMapping.getHeight() == null))) {
                throw new ServiceException("缺少宽或高");
            }
        }
    }
}
