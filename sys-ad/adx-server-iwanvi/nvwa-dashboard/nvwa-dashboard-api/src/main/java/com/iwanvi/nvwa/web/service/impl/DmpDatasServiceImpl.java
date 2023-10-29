package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.DmpDataDefinitionMapper;
import com.iwanvi.nvwa.dao.DmpDatasMapper;
import com.iwanvi.nvwa.dao.DmpDatasourceMapper;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.DmpDatasService;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

@Service
public class DmpDatasServiceImpl implements DmpDatasService {
    @Autowired
    private DmpDatasMapper dmpDatasMapper;
    @Autowired
    private DmpDatasourceMapper dmpDatasourceMapper;
    @Autowired
    private DmpDataDefinitionMapper dmpDataDefinitionMapper;
    @Autowired
    private RedisDao redisDao;


    @Transactional
    @Override
    public void add(DmpDatas dmpDatas) {
        if (dmpDatas != null && StringUtils.isNotBlank(dmpDatas.getName())) {
            if (!checkNameInDb(dmpDatas.getName())) {
                String fileKey = StringUtils.buildString(WebConstants.DATASOURCE_DOWNLOAD_FILE_OK_KEY, dmpDatas.getSid());
                String fileDownDate = redisDao.get(fileKey);
                if (StringUtils.isBlank(fileDownDate)) {
                    throw new ServiceException("数据集依赖的文件还未下载完成，不能使用该数据集");
                }
                dmpDatas.setStatus(Constants.STATE_VALID);
                dmpDatasMapper.insertSelective(dmpDatas);
                dmpDatasMapper.countByExample(null);
                if (!CollectionUtils.isEmpty(dmpDatas.getDefinitions())) {
                    for (DmpDataDefinition definition : dmpDatas.getDefinitions()) {
                        definition.setDid(dmpDatas.getId());
                        dmpDataDefinitionMapper.insertSelective(definition);
                    }
                }
            } else {
                throw new ServiceException("已有重名数据集");
            }
        }
    }

    @Transactional
    @Override
    public void update(DmpDatas dmpDatas) {
        if (dmpDatas != null && dmpDatas.getId() != null) {
            dmpDatas.setName(null);
            dmpDatasMapper.updateByPrimaryKeySelective(dmpDatas);
        }
    }

    @Override
    public Page<DmpDatas> selectForPage(DmpDatas dmpDatas, Integer cp, Integer ps) {
        Page<DmpDatas> page;
        DmpDatasExample example = bindingExample(dmpDatas);
        int count = dmpDatasMapper.countByExample(example);
        List<DmpDatas> list = Lists.newArrayList();
        if (cp != null && ps != null) {
            page = new Page<DmpDatas>(count, cp, ps);
        } else {
            page = new Page<DmpDatas>(count);
        }
        example.setStart(page.getStartPosition());
        example.setLimit(page.getPageSize());
        list = dmpDatasMapper.selectByExample(example);
        fillList(list);
        page.bindData(list);
        return page;
    }

    @Override
    public List<List<String>> preview(Integer sid, Integer signCode, String sign) {
        if (sid != null) {
            String fileKey = StringUtils.buildString(WebConstants.DATASOURCE_DOWNLOAD_FILE_OK_KEY, sid);
            String fileDownDate = redisDao.get(fileKey);
            if (StringUtils.isBlank(fileDownDate)) {
                throw new ServiceException("数据集依赖的文件还未下载完成，不能使用该数据集");
            }
            DmpDatasource datasource = dmpDatasourceMapper.selectByPrimaryKey(sid);
            if (datasource != null) {
                String paths = datasource.getPaths();
                if (StringUtils.isNotBlank(paths)) {
                    List<String> pathList = StringUtils.str2List(paths, Constants.SIGN_COMMA);
                    if (!CollectionUtils.isEmpty(pathList)) {
                        File localFile = null;
                        try {
                            String path = pathList.get(Constants.INTEGER_0);
                            String fileName = path.substring(path.lastIndexOf(Constants.SIGN_OBLIQUELINE));
                            String localpath = StringUtils.concat(WebConstants.DATASOURCE_DOWNLOAD_PATH,sid,
                                    Constants.SIGN_OBLIQUELINE,fileName);
                            localFile = new File(localpath);
                        } catch (Exception e) {
                            throw new ServiceException("数据源文件解析失败");
                        }
                        if (localFile.exists()) {
                            try {
                                FileReader reader = new FileReader(localFile);
                                BufferedReader br = new BufferedReader(reader);
                                String line;
                                String signStr = WebConstants.getSign(signCode);
                                if (StringUtils.isBlank(signStr)) {
                                    signStr = sign;
                                }
                                if (WebConstants.isContainsSpecialChar(signStr)) {
                                    signStr = "\\" + signStr;
                                }
                                int maxCols = 0;
                                List<List<String>> previewList = Lists.newArrayList();
                                for (int i = 0; i < WebConstants.DMP_DATA_READ_LINE; i++) {
                                    line = br.readLine();
                                    List<String> dataArray = StringUtils.str2List(line, signStr);
                                    if (maxCols < dataArray.size()) {
                                        maxCols = dataArray.size();
                                    }
                                    previewList.add(dataArray);
                                }
                                br.close();
                                for (List<String> itemList : previewList) {
                                    if (maxCols > itemList.size()) {
                                        for (int i = 0; i < maxCols - itemList.size(); i++) {
                                            itemList.add(StringUtils.EMPTY);
                                        }
                                    }
                                }
                                return previewList;
                            } catch (Exception e) {
                                throw new ServiceException("数据文件读取失败，请稍后再试。",e);
                            }
                        } else {
                            throw new ServiceException("数据文件还未下载。请重新提交数据源进行下载。");
                        }
                    }
                }
            }
        }
        return null;
    }

    private boolean checkNameInDb (String name) {
        boolean isExisted = false;
        if (StringUtils.isNotBlank(name)) {
            DmpDatasExample example = new DmpDatasExample();
            example.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andNameEqualTo(name);
            int count = dmpDatasMapper.countByExample(example);
            if (count > 0) {
                isExisted = true;
            }
        }
        return isExisted;
    }

    private DmpDatasExample bindingExample(DmpDatas datas) {
        DmpDatasExample example = new DmpDatasExample();
        if (datas != null) {
            DmpDatasExample.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(datas.getName())) {
                criteria.andNameLike("%" + datas.getName() + "%");
            }
        }
        example.setOrderByClause(" id desc ");
        return example;
    }

    private void fillList(List<DmpDatas> datas) {
        if (!CollectionUtils.isEmpty(datas)) {
            DmpDatasource datasource;
            List<DmpDataDefinition> definitions;
            DmpDataDefinitionExample example;
            for (DmpDatas data : datas) {
                datasource = dmpDatasourceMapper.selectByPrimaryKey(data.getSid());
                if (datasource != null) {
                    data.setsName(datasource.getName());
                }
                example = new DmpDataDefinitionExample();
                example.createCriteria().andDidEqualTo(data.getId());
                definitions = dmpDataDefinitionMapper.selectByExample(example);
                data.setDefinitions(definitions);
            }
        }
    }
}
