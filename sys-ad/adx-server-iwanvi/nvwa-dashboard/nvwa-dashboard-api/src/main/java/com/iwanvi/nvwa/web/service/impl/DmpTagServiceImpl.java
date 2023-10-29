package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.DmpTagMapper;
import com.iwanvi.nvwa.dao.model.DmpTag;
import com.iwanvi.nvwa.dao.model.DmpTagExample;
import com.iwanvi.nvwa.web.service.DmpTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmpTagServiceImpl implements DmpTagService {
    @Autowired
    private DmpTagMapper dmpTagMapper;

    @Transactional
    @Override
    public void add(DmpTag dmpTag) {
        if (dmpTag != null && StringUtils.isNotBlank(dmpTag.getName())) {
            if (!checkNameInDb(dmpTag)) {
                dmpTagMapper.insertSelective(dmpTag);
            } else {
                throw new ServiceException("已有重名标签");
            }
        }
    }

    @Transactional
    @Override
    public void update(DmpTag dmpTag) {
        if (dmpTag != null && dmpTag.getId() != null) {
            dmpTag.setName(null);
            dmpTag.setStatus(Constants.STATE_INVALID);
            dmpTagMapper.updateByPrimaryKeySelective(dmpTag);
        }
    }

    @Override
    public List<DmpTag> selectForList(Integer tid) {
        DmpTagExample example = new DmpTagExample();
        example.createCriteria().andTidEqualTo(tid).andStatusEqualTo(Constants.STATE_VALID);
        return dmpTagMapper.selectByExample(example);
    }

    private boolean checkNameInDb(DmpTag dmpTag) {
        boolean isExisted = false;
        if (dmpTag != null && StringUtils.isNotBlank(dmpTag.getName()) && dmpTag.getTid() != null) {
            DmpTagExample example = new DmpTagExample();
            example.createCriteria().andTidEqualTo(dmpTag.getTid()).andNameEqualTo(dmpTag.getName());
            int count = dmpTagMapper.countByExample(example);
            if (count > 0) {
                isExisted = true;
            }
        }
        return isExisted;
    }
}
