package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.DmpDatasMapper;
import com.iwanvi.nvwa.dao.DmpDatasourceMapper;
import com.iwanvi.nvwa.dao.DmpJudgeMapper;
import com.iwanvi.nvwa.dao.UserMapper;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.DmpDatasourceService;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class DmpDatasourceServiceImpl implements DmpDatasourceService {
    @Autowired
    private DmpDatasourceMapper dmpDatasourceMapper;
    @Autowired
    private DmpDatasMapper dmpDatasMapper;
    @Autowired
    private DmpJudgeMapper dmpJudgeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisDao redisDao;

    @Transactional
    @Override
    public void add(DmpDatasource datasource) {
        if (datasource != null && StringUtils.isNotBlank(datasource.getName())) {
            if (!checkNameInDb(datasource.getName())) {
                datasource.setStatus(Constants.STATE_VALID);
                dmpDatasourceMapper.insertSelective(datasource);
                dmpDatasourceMapper.countByExample(null);
                redisDao.leftPush(WebConstants.DATASOURCE_DOWNLOAD_MQ,
                        WebConstants.DATASOURCE_DOWNLOAD_MQ_KEY + datasource.getId());
            } else {
                throw new ServiceException("数据源'" + datasource.getName() + "'已存在");
            }
        }
    }

    @Transactional
    @Override
    public void update(DmpDatasource datasource) {
        if (datasource != null && datasource.getId() != null) {
            DmpDatasource sourceDb = dmpDatasourceMapper.selectByPrimaryKey(datasource.getId());
//            datasource.setName(null);
            if (StringUtils.isNotBlank(datasource.getName())) {
                DmpDatasourceExample example = new DmpDatasourceExample();
                example.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andNameEqualTo(datasource.getName());
                List<DmpDatasource> dsList = dmpDatasourceMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(dsList) && dsList.get(Constants.INTEGER_0).getId() != datasource.getId()) {
                    throw new ServiceException("已存在重名数据源");
                }
            }
            dmpDatasourceMapper.updateByPrimaryKeySelective(datasource);
//            if (StringUtils.isNotBlank(datasource.getPaths())
//                    && !datasource.getPaths().equals(sourceDb.getPaths())) {
                redisDao.leftPush(WebConstants.DATASOURCE_DOWNLOAD_MQ,
                        WebConstants.DATASOURCE_DOWNLOAD_MQ_KEY + datasource.getId());
//            }
        }
    }

    @Override
    public Page<DmpDatasource> selectForPage(DmpDatasource datasource, Integer cp, Integer ps) {
        Page<DmpDatasource> page;
        DmpDatasourceExample example = bindingExample(datasource);
        int count = dmpDatasourceMapper.countByExample(example);
        List<DmpDatasource> list = Lists.newArrayList();
        if (cp != null && ps != null) {
            page = new Page<DmpDatasource>(count, cp, ps);
        } else {
            page = new Page<DmpDatasource>(count);
        }
        example.setStart(page.getStartPosition());
        example.setLimit(page.getPageSize());
        list = dmpDatasourceMapper.selectByExampleWithBLOBs(example);
        fillList(list);
        page.bindData(list);
        return page;
    }

    @Override
    public void rejudge(Integer id) {
        DmpDatasExample datasExample = new DmpDatasExample();
        datasExample.createCriteria().andSidEqualTo(id).andStatusEqualTo(Constants.STATE_VALID);
        List<DmpDatas> datasList = dmpDatasMapper.selectByExample(datasExample);
        List<Integer> dataIdList = Lists.transform(datasList, DmpDatas::getId);
        if (!CollectionUtils.isEmpty(dataIdList)) {
            DmpJudgeExample example = new DmpJudgeExample();
            example.createCriteria().andDidIn(dataIdList);
            List<DmpJudge> judges = dmpJudgeMapper.selectByExample(example);
            for (DmpJudge judge : judges) {
                redisDao.leftPush(WebConstants.RULE_JUDGE_MQ,WebConstants.RULE_JUDGE_MQ_KEY + judge.getId());
            }
        }
    }

    private DmpDatasourceExample bindingExample(DmpDatasource datasource) {
        DmpDatasourceExample example = new DmpDatasourceExample();
        if (datasource != null) {
            DmpDatasourceExample.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(datasource.getName())) {
                criteria.andNameLike("%" + datasource.getName() + "%");
            }
            if (datasource.getStatus() != null) {
                criteria.andStatusEqualTo(datasource.getStatus());
            }
        }
        example.setOrderByClause(" id desc ");
        return example;
    }

    private boolean checkNameInDb(String name){
        boolean isExisted = false;
        DmpDatasourceExample example = new DmpDatasourceExample();
        example.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andNameEqualTo(name);
        int count = dmpDatasourceMapper.countByExample(example);
        if (count > Constants.INTEGER_0) {
            isExisted = true;
        }
        return isExisted;
    }

    private void fillList(List<DmpDatasource> list) {
        for (DmpDatasource datesource : list) {
            if (datesource.getCreateUser() != null) {
                User user = userMapper.selectByPrimaryKey(datesource.getCreateUser());
                datesource.setCreaterName(user.getRealName());
            }
            String fileKey = StringUtils.buildString(WebConstants.DATASOURCE_DOWNLOAD_FILE_OK_KEY, datesource.getId());
            String fileDownDate = redisDao.get(fileKey);
            if (StringUtils.isBlank(fileDownDate)) {
                datesource.setDownStatus("下载中");
            } else {
                datesource.setDownStatus("下载完成");
            }
        }
    }
}
