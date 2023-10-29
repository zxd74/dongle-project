package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdvertiserDspMapper;
import com.iwanvi.nvwa.dao.EntityDspMapper;
import com.iwanvi.nvwa.dao.FlowConsumerMapper;
import com.iwanvi.nvwa.dao.model.AdvertiserDsp;
import com.iwanvi.nvwa.dao.model.EntityDsp;
import com.iwanvi.nvwa.dao.model.EntityDspExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.EntityDspService;
import com.iwanvi.nvwa.web.service.SysCrontabService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class EntityDspServiceImpl implements EntityDspService {

    private static final Logger logger = LoggerFactory.getLogger(EntityDspServiceImpl.class);

    @Autowired
    private EntityDspMapper entityDspMapper;
    @Autowired
    private FlowConsumerMapper flowConsumerMapper;
    @Autowired
    private AdvertiserDspMapper advertiserDspMapper;
    @Autowired
    private SysCrontabService sysCrontabService;
    @Override
    @Transactional
    public void update(EntityDsp entityDsp) {
        if (entityDsp != null ) {
            boolean needCrontb = false;
            EntityDsp oldEntity = entityDspMapper.selectByPrimaryKey(entityDsp.getId());
            AdvertiserDsp advertiserDsp = advertiserDspMapper.selectByPrimaryKey(oldEntity.getAdvertiserId());
            entityDspMapper.updateByPrimaryKeySelective(entityDsp);
            if ( entityDsp.getExaminesStatus() != null && !entityDsp.getExaminesStatus().equals(oldEntity.getExaminesStatus())) {
                needCrontb = true;
            }
            if ( entityDsp.getStatus() != null && !entityDsp.getStatus().equals(oldEntity.getStatus())) {
                needCrontb = true;
            }
            if (needCrontb) {
                sysCrontabService.addSysCrontab(entityDsp.getId(), Constants.OBJ_DSP_ENTITY, Constants.OP_UPDATE);
                logger.info("add entityDsy to sysCrontab success ! entityDspId :{}",entityDsp.getId());
            }
            logger.info("update entityDsp success !");
        }
    }

    @Override
    public Page<EntityDsp> selectForPage(EntityDsp entityDsp, Integer cp, Integer ps) {
        Page<EntityDsp> page;
        EntityDspExample example = bindingExample(entityDsp);
        int count = (int)entityDspMapper.countByExample(example);
        List<EntityDsp> list = Lists.newArrayList();
        if (entityDsp.getCp() != null && entityDsp.getPs() != null) {
            page = new Page<EntityDsp>(count, entityDsp.getCp(), entityDsp.getPs());
        } else {
            page = new Page<EntityDsp>(count);
        }
        example.page(page.getCurrentPageNum()-1, page.getPageSize());
        list = entityDspMapper.selectByExample(example);
        for (EntityDsp entity : list) {
            if (entity.getConsumerId() != null) {
                entity.setConsumerName(flowConsumerMapper.selectByPrimaryKey(entity.getConsumerId()).getConsumerName());
            }
            if (entity.getAdvertiserId() != null) {
                entity.setAdvertiserName(advertiserDspMapper.selectByPrimaryKey(entity.getAdvertiserId()).getName());
            }
        }
        page.bindData(list);
        return page;
    }

    @Override
    public EntityDsp get(Integer id) {
        EntityDsp entityDsp = entityDspMapper.selectByPrimaryKey(id);
        if (entityDsp.getConsumerId() != null) {
            entityDsp.setDspId(flowConsumerMapper.selectByPrimaryKey(entityDsp.getConsumerId()).getDspId());
        }
        if (entityDsp.getAdvertiserId() != null) {
            entityDsp.setAdvertiserName(advertiserDspMapper.selectByPrimaryKey(entityDsp.getAdvertiserId()).getName());
        }
        return entityDsp;
    }

    /**
     * 检查创意是否过期
     */
    @Override
    public void checkEntityOutOfDate() {
        try {
            EntityDspExample entityDspExample = new EntityDspExample();
            entityDspExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID);
            List<EntityDsp> list = entityDspMapper.selectByExample(entityDspExample);
            for (EntityDsp entityDsp : list) {
                if (StringUtils.isNotBlank(entityDsp.getExpireDate())) {
                    Date enityDate = DateUtils.parse(entityDsp.getExpireDate(), DateUtils.SHORT_FORMAT_EX);
                    if (enityDate.compareTo(new Date()) < 0) {
                        entityDsp.setStatus(Constants.STATE_INVALID);
                        entityDspMapper.updateByPrimaryKeySelective(entityDsp);
                        logger.info("entityDsp exprie and update success ! , entityDspId:{}", entityDsp.getId());
                    }
                }
            }
        } catch (Exception e) {
            logger.info("check entityDsp expire error ! ",e);
        }
    }

    private EntityDspExample bindingExample(EntityDsp entityDsp) {
        EntityDspExample example = new EntityDspExample();
        if (entityDsp != null) {
            EntityDspExample.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(entityDsp.getDspCreativeId())) {
                criteria.andDspCreativeIdEqualTo(entityDsp.getDspCreativeId());
            }
            if (entityDsp.getConsumerId() != null) {
                criteria.andConsumerIdEqualTo(entityDsp.getConsumerId());
            }
            if (StringUtils.isNotBlank(entityDsp.getName())) {
                criteria.andNameLike("%" + entityDsp.getName() + "%");
            }
            if (entityDsp.getStatus() != null) {
                criteria.andStatusEqualTo(entityDsp.getStatus());
            }
            if (entityDsp.getAdvertiserId() != null) {
                criteria.andAdvertiserIdEqualTo(entityDsp.getAdvertiserId());
            }
            example.setOrderByClause("status desc, id desc");

        }
        return example;
    }
}
