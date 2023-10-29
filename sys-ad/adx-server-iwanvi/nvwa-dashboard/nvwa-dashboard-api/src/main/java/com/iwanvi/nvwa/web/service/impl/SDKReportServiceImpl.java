package com.iwanvi.nvwa.web.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.web.service.SDKReportService;
import com.iwanvi.nvwa.web.util.ExcelUtil;
import com.iwanvi.nvwa.web.util.WebConstants;
import io.swagger.models.auth.In;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class SDKReportServiceImpl implements SDKReportService {

    private static final Logger logger = LoggerFactory.getLogger(SDKReportServiceImpl.class);

    @Autowired
    private SdkReportMapper sdkReportMapper;
    @Autowired
    private AdPositionMappingMapper adPositionMappingMapper;
    @Autowired
    private FlowConsumerMapper flowConsumerMapper;
    @Autowired
    private AppsMapper appsMapper;
    @Autowired
    private AdPositionMapper adPositionMapper;

    private static String[] REPORT_ARRAY_GDT = new String[]{"date","positionId","mediaName","exp","clk","income","cpm","ctr"};
    private static String[] REPORT_ARRAY_TT = new String[]{"date","positionId","positionName","exp","clk","ctr","cpm",
            "income"};
    private static String[] REPORT_ARRAY_DG = new String[]{"date","positionId","positionName","mediaName","linkName",
            "clk","exp","income","ctr","cpm","cpc"};
    private static String[] REPORT_ARRAY_BD = new String[]{"positionId","positionName","appId","appName","date","exp",
            "clk","income","clkIncome","expIncome","ecpm","cpc","fr","ctr"};
    private static String[] REPORT_ARRAY_360 = new String[]{"date","positionId","appName","positionType","positionName","exp","clk",
            "ctr","income","cpc","cpm"};
    private static String[] REPORT_ARRAY_COMMON = new String[]{"date","positionId","appName","positionName","exp","clk","income",
            "cpm","ctr"};

    private static final Map<Integer, String[]> UPLOAD_TYPE_MAP = Maps.newHashMap();

    static {
        UPLOAD_TYPE_MAP.put(Constants.INTEGER_1, REPORT_ARRAY_GDT);
        UPLOAD_TYPE_MAP.put(Constants.INTEGER_2, REPORT_ARRAY_TT);
        UPLOAD_TYPE_MAP.put(Constants.INTEGER_3, REPORT_ARRAY_DG);
        UPLOAD_TYPE_MAP.put(Constants.INTEGER_4, REPORT_ARRAY_BD);
        UPLOAD_TYPE_MAP.put(Constants.INTEGER_5, REPORT_ARRAY_360);
        UPLOAD_TYPE_MAP.put(Constants.INTEGER_6, REPORT_ARRAY_COMMON);
    }

    @Override
    public void importSDKReport(MultipartFile file, Integer type) {
        if (file != null && type != null) {
            String fileName = file.getOriginalFilename();
            String date = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
            String localPath = StringUtils.concat(WebConstants.UPLOAD_LOCAL_PATH, date, Constants.SIGN_OBLIQUELINE,
                    fileName);
            try {
                File localFile = new File(localPath);
                if(!localFile.getParentFile().exists()){
                    localFile.getParentFile().mkdirs();
                }
                file.transferTo(localFile);
            } catch (Exception e) {
                throw new ServiceException("file upload error",e);
            }
            String[] fields = UPLOAD_TYPE_MAP.get(type);
            if (fields != null) {
                List<List<Object>> values = ExcelUtil.getValuesFromExcel(localPath);
                if (CollectionUtils.isNotEmpty(values)) {
                    List<SdkReport> reportList = Lists.newArrayList();
                    for (List<Object> rowList : values) {
                        if (CollectionUtils.isNotEmpty(rowList)) {
                            SdkReport report = new SdkReport();
                            for (int i = 0; i < fields.length; i++) {
                                try {
                                    Field field = report.getClass().getDeclaredField(fields[i]);
                                    field.setAccessible(true);
                                    Object value = null;
                                    if (field.getType() == Double.class) {
                                        value = Double.parseDouble(rowList.get(i).toString());
                                    } else if (field.getType() == Long.class) {
                                        value = Long.parseLong(rowList.get(i).toString());
                                    } else if (field.getType() == Integer.class) {
                                        value = Integer.parseInt(rowList.get(i).toString());
                                    } else if (field.getType() == String.class) {
                                        value = rowList.get(i).toString();
                                    }
                                    field.set(report, value);
                                } catch (Exception e) {
                                    logger.error("fill fields error ", e);
                                }
                            }
                            if (report.getDate() != null) {
                                reportList.add(report);
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(reportList)) {
                        AdPositionMappingExample example = new AdPositionMappingExample();
                        AdPositionMappingExample.Criteria criteria = example.createCriteria();
                        for (SdkReport report : reportList) {
                            if (report != null) {
                                criteria.andConsumerPositionIdEqualTo(report.getPositionId());
                                List<AdPositionMapping> mappingList = adPositionMappingMapper.selectByExample(example);
                                if (CollectionUtils.isNotEmpty(mappingList)) {
                                    report.setPid(mappingList.get(Constants.INTEGER_0).getAdPositionId());
                                    AdPosition position = adPositionMapper.selectByPrimaryKey(report.getPid());
                                    if (position != null) {
                                        report.setAppId(position.getAppId().toString());
                                    }
                                    report.setType(mappingList.get(Constants.INTEGER_0).getFlowConsumerId());
                                } else {
                                    throw new ServiceException("文件中包含系统中没有的广告位ID，请修改后重新导入。");
                                }
                            }
                        }
                        Iterator<SdkReport> it = reportList.iterator();
                        while (it.hasNext()) {
                            SdkReport report = it.next();
                            SdkReportExample reportExample = new SdkReportExample();
                            reportExample.createCriteria().andDateEqualTo(report.getDate())
                                    .andPositionIdEqualTo(report.getPositionId());
                            long count = sdkReportMapper.countByExample(reportExample);
                            if (count > 0) {
                                sdkReportMapper.updateByExampleSelective(report, reportExample);
                                it.remove();
                            }
                        }
                        sdkReportMapper.batchInsert(reportList);
                    }
                } else {
                    logger.error("file is empty, file : {}", fileName);
                    throw new ServiceException("文件没有内容");
                }
            } else {
                logger.error("do not find upload template, file : {}", fileName);
                throw new ServiceException("没有找到对应的模板");
            }
            FileUtils.deleteQuietly(new File(localPath));
        } else {
            throw new ServiceException("文件或模板类型不能为空");
        }
    }

    @Override
    public List<SdkReport> sumSDKReportWithDate(String flowCons, String apps, String pids, String flowPosIds,
                                                Integer sDate, Integer eDate) {
        List<SdkReport> resultList = Lists.newArrayList();
        if (sDate != null && eDate != null) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            if (StringUtils.isNotBlank(flowCons)) {
                List<Integer> flowConIds = StringUtils.str2List4Int(flowCons, Constants.SIGN_COMMA);
                paramMap.put("types", flowConIds);
            }
            if (StringUtils.isNotBlank(apps)) {
                List<Integer> appIds = StringUtils.str2List4Int(flowCons, Constants.SIGN_COMMA);
                paramMap.put("apps", appIds);
            }
            if (StringUtils.isNotBlank(pids)) {
                List<Integer> pidList = StringUtils.str2List4Int(pids, Constants.SIGN_COMMA);
                paramMap.put("pids", pidList);
            }
            if (StringUtils.isNotBlank(flowPosIds)) {
                List<String> posIds = StringUtils.str2List(flowPosIds, Constants.SIGN_COMMA);
                paramMap.put("posIds", posIds);
            }
            resultList = sdkReportMapper.sumReportWithDate(paramMap);
            Map<String, SdkReport> quotaDayMap = Maps.newHashMap();
            for (SdkReport report : resultList) {
                quotaDayMap.put(report.getDate().toString(), report);
            }
            Date sDay = DateUtils.parse(sDate.toString(), DateUtils.SHORT_FORMAT);
            Date eDay = DateUtils.parse(eDate.toString(), DateUtils.SHORT_FORMAT);
            for (Date startDate = sDay; startDate.compareTo(eDay) <= 0; startDate = DateUtils.getNextDaysDate(startDate,
                    1)) {
                String day = DateUtils.format(startDate, DateUtils.SHORT_FORMAT);
                if (!quotaDayMap.containsKey(day)) {
                    SdkReport quota = new SdkReport();
                    quota.setDate(Integer.parseInt(day));
                    quotaDayMap.put(day, quota);
                }
            }
            resultList = Lists.newArrayList(quotaDayMap.values());
            resultList.sort((o1, o2) -> o1.getDate() < o2.getDate() ? -1 : 1);
        }
        return resultList;
    }

    @Override
    public List<SdkReport> sumSDKReportWithGroup(String flowCons, String apps, String pids, String flowPosIds,
                                                 String group, Integer sDate, Integer eDate) {
        List<SdkReport> resultList = Lists.newArrayList();
        if (sDate != null && eDate != null && StringUtils.isNotBlank(group)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            paramMap.put("groupItem", group);
            if (StringUtils.isNotBlank(flowCons)) {
                List<Integer> flowConIds = StringUtils.str2List4Int(flowCons, Constants.SIGN_COMMA);
                paramMap.put("types", flowConIds);
            }
            if (StringUtils.isNotBlank(apps)) {
                List<Integer> appIds = StringUtils.str2List4Int(apps, Constants.SIGN_COMMA);
                paramMap.put("apps", appIds);
            }
            if (StringUtils.isNotBlank(pids)) {
                List<Integer> pidList = StringUtils.str2List4Int(pids, Constants.SIGN_COMMA);
                paramMap.put("pids", pidList);
            }
            if (StringUtils.isNotBlank(flowPosIds)) {
                List<String> posIds = StringUtils.str2List(flowPosIds, Constants.SIGN_COMMA);
                paramMap.put("posIds", posIds);
            }
            resultList = sdkReportMapper.sumReportWithGroup(paramMap);
            for (SdkReport report : resultList) {
                report.setClkUV(0l);
                report.setExpUV(0l);
                report.setReqUV(0l);
                report.setClkDesire(0d);
                report.setReq(0l);
                if (report.getType() != null) {
                    FlowConsumer platform = flowConsumerMapper.selectByPrimaryKey(report.getType());
                    if (platform != null) {
                        report.setMediaName(platform.getConsumerName());
                    }
                }
                if ("app_id".equals(group)) {
                    Apps app = appsMapper.selectByPrimaryKey(Integer.parseInt(report.getItemId()));
                    if (app != null) {
                        report.setAppName(app.getAppName());
                    }
                } else if ("pid".equals(group)) {
                    AdPosition adPosition = adPositionMapper.selectByPrimaryKey(Integer.parseInt(report.getItemId()));
                    if (adPosition != null) {
                        report.setAdpName(adPosition.getName());
                    }
                } else if ("position_id".equals(group)) {
                    AdPositionMappingExample example = new AdPositionMappingExample();
                    example.createCriteria().andConsumerPositionIdEqualTo(report.getItemId());
                    List<AdPositionMapping> mappingList = adPositionMappingMapper.selectByExample(example);
                    if (CollectionUtils.isNotEmpty(mappingList)) {
                        report.setPositionName(mappingList.get(Constants.INTEGER_0).getConsumerPositionName());
                    }
                } else if ("type".equals(group)) {
                    FlowConsumer flow = flowConsumerMapper.selectByPrimaryKey(Integer.parseInt(report.getItemId()));
                    if (flow != null) {
                        report.setMediaName(flow.getConsumerName());
                    }
                }
            }
        }
        return resultList;
    }

    @Override
    public List<SdkReport> detailSDKReport(String id, String flowCons, String apps, String pids, String flowPosIds,
                                           String group, Integer sDate, Integer eDate) {
        List<SdkReport> resultList = Lists.newArrayList();
        if (sDate != null && eDate != null) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            paramMap.put("groupItem", group);
            if ("app_id".equals(group)) {
                apps = id;
            } else if ("pid".equals(group)) {
                pids = id;
            } else if ("position_id".equals(group)) {
                flowPosIds = id;
            } else if ("type".equals(group)) {
                flowCons = id;
            }
            if (StringUtils.isNotBlank(flowCons)) {
                List<Integer> flowConIds = StringUtils.str2List4Int(flowCons, Constants.SIGN_COMMA);
                paramMap.put("types", flowConIds);
            }
            if (StringUtils.isNotBlank(apps)) {
                List<Integer> appIds = StringUtils.str2List4Int(apps, Constants.SIGN_COMMA);
                paramMap.put("apps", appIds);
            }
            if (StringUtils.isNotBlank(pids)) {
                List<Integer> pidList = StringUtils.str2List4Int(pids, Constants.SIGN_COMMA);
                paramMap.put("pids", pidList);
            }
            if (StringUtils.isNotBlank(flowPosIds)) {
                List<String> posIds = StringUtils.str2List(flowPosIds, Constants.SIGN_COMMA);
                paramMap.put("posIds", posIds);
            }
            resultList = sdkReportMapper.detailReport(paramMap);
            Map<String, SdkReport> quotaDayMap = Maps.newHashMap();
            for (SdkReport report : resultList) {
                quotaDayMap.put(report.getDate().toString(), report);
            }
            Date sDay = DateUtils.parse(sDate.toString(), DateUtils.SHORT_FORMAT);
            Date eDay = DateUtils.parse(eDate.toString(), DateUtils.SHORT_FORMAT);
            for (Date startDate = sDay; startDate.compareTo(eDay) <= 0; startDate = DateUtils.getNextDaysDate(startDate,
                    1)) {
                String day = DateUtils.format(startDate, DateUtils.SHORT_FORMAT);
                if (!quotaDayMap.containsKey(day)) {
                    SdkReport quota = new SdkReport();
                    quota.setDate(Integer.parseInt(day));
                    quotaDayMap.put(day, quota);
                }
            }
            resultList = Lists.newArrayList(quotaDayMap.values());
            for (SdkReport report : resultList) {
                report.setClkUV(0l);
                report.setExpUV(0l);
                report.setReqUV(0l);
                report.setClkDesire(0d);
                report.setReq(0l);
                if ("app_id".equals(group)) {
                    Apps app = appsMapper.selectByPrimaryKey(Integer.parseInt(id));
                    if (app != null) {
                        report.setItemName(app.getAppName());
                    }
                } else if ("pid".equals(group)) {
                    AdPosition adPosition = adPositionMapper.selectByPrimaryKey(Integer.parseInt(id));
                    if (adPosition != null) {
                        report.setItemName(adPosition.getName());
                    }
                } else if ("position_id".equals(group)) {
                    AdPositionMappingExample example = new AdPositionMappingExample();
                    example.createCriteria().andConsumerPositionIdEqualTo(id);
                    List<AdPositionMapping> mappingList = adPositionMappingMapper.selectByExample(example);
                    if (CollectionUtils.isNotEmpty(mappingList)) {
                        report.setItemName(mappingList.get(Constants.INTEGER_0).getConsumerPositionName());
                    }
                } else if ("type".equals(group)) {
                    FlowConsumer flow = flowConsumerMapper.selectByPrimaryKey(Integer.parseInt(id));
                    if (flow != null) {
                        report.setItemName(flow.getConsumerName());
                    }
                }
            }
            resultList.sort((o1, o2) -> o1.getDate() < o2.getDate() ? -1 : 1);
        }
        return resultList;
    }


}
