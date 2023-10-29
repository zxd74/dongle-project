package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.common.utils.UUIDUtils;
import com.iwanvi.nvwa.dao.AdvertiserDspMapper;
import com.iwanvi.nvwa.dao.FlowConsumerMapper;
import com.iwanvi.nvwa.dao.model.AdvertiserDsp;
import com.iwanvi.nvwa.dao.model.AdvertiserDspExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdvertiserDspService;
import com.iwanvi.nvwa.web.service.SysCrontabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AdvertiserDspServiceImpl implements AdvertiserDspService {

    @Autowired
    private AdvertiserDspMapper advertiserDspMapper;
    @Autowired
    private FlowConsumerMapper flowConsumerMapper;
    @Autowired
    private SysCrontabService sysCrontabService;

    @Override
    @Transactional
    public void add(AdvertiserDsp advertiserDsp) {
        advertiserDsp.setCreateTime(new Date());
        advertiserDsp.setStatus(Constants.STATE_VALID);
        advertiserDsp.setExaminesStatus(Constants.STATE_WAIT_AUDIT);
        advertiserDspMapper.insertSelective(advertiserDsp);
    }

    @Override
    @Transactional
    public void update(AdvertiserDsp advertiserDsp) {
        advertiserDspMapper.updateByPrimaryKeySelective(advertiserDsp);
        sysCrontabService.addSysCrontab(advertiserDsp.getId(), Constants.OBJ_DSP_ADVERTISER, Constants.OP_UPDATE);
    }

    @Override
    public AdvertiserDsp get(Integer id) {
        return advertiserDspMapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<AdvertiserDsp> selectForPage(AdvertiserDsp advertiserDsp, Integer cp, Integer ps) {
        AdvertiserDspExample example = bindingExample(advertiserDsp);
        Page<AdvertiserDsp> page;
        int count = advertiserDspMapper.countByExample(example);
        if (cp != null && ps != null) {
            page = new Page<AdvertiserDsp>(count, cp, ps);
        } else {
            page = new Page<AdvertiserDsp>(count);
        }
        example.setStart(page.getStartPosition());
        example.setLimit(page.getPageSize());
        List<AdvertiserDsp> dataList = advertiserDspMapper.selectByExample(example);
        for (AdvertiserDsp advertiser : dataList) {
            advertiser.setConsumerName(flowConsumerMapper.selectByPrimaryKey(advertiser.getFlowConsumerId()).getConsumerName());
        }
        page.bindData(dataList);
        return page;
    }

    @Override
    public boolean checkNameExist(AdvertiserDsp advertiserDsp) {
        boolean isExistes = false;
        if (advertiserDsp != null && StringUtils.isNotBlank(advertiserDsp.getName()) && advertiserDsp.getFlowConsumerId() != null) {
            AdvertiserDspExample example = new AdvertiserDspExample();
            example.createCriteria().andNameEqualTo(advertiserDsp.getName()).andFlowConsumerIdEqualTo(advertiserDsp.getFlowConsumerId())
                    .andStatusNotEqualTo(Constants.STATE_INVALID);
            List<AdvertiserDsp> advers = advertiserDspMapper.selectByExample(example);
            if ( advers != null && advers.size() > 0 && !advers.get(0).getId().equals(advertiserDsp.getId())) {
                isExistes = true;
            }
        }
        return isExistes;
    }

//    private boolean checkAdverKey(String key){
//        boolean isExistes = false;
//        if (StringUtils.isNotBlank(key)) {
//            AdvertiserDspExample example = new AdvertiserDspExample();
//            example.createCriteria().andCustomerKeyEqualTo(key);
//            int count = advertiserDspMapper.countByExample(example);
//            if ( count > 0 ) {
//                isExistes = true;
//            }
//        }
//        return isExistes;
//    }

    private AdvertiserDspExample bindingExample(AdvertiserDsp adver){
        AdvertiserDspExample example = new AdvertiserDspExample();
        AdvertiserDspExample.Criteria criteria = example.createCriteria();
        if (adver != null) {
            if (adver.getFlowConsumerId() != null) {
                criteria.andFlowConsumerIdEqualTo(adver.getFlowConsumerId());
            }
            if (StringUtils.isNotBlank(adver.getName())) {
                criteria.andNameLike("%" + adver.getName() + "%");
            }
            if (StringUtils.isNotBlank(adver.getDspAdvertiserId())) {
                criteria.andDspAdvertiserIdLike("%" + adver.getDspAdvertiserId() + "%");
            }
            if (adver.getStatus() != null) {
                criteria.andStatusEqualTo(adver.getStatus());
            }
        }
        example.setOrderByClause(" status desc, id desc ");
        return example;
    }
}
