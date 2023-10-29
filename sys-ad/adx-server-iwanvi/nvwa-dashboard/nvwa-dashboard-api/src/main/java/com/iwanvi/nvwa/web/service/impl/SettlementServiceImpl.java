package com.iwanvi.nvwa.web.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.web.service.SettlementService;
import com.iwanvi.nvwa.web.util.ExcelUtil;
import com.iwanvi.nvwa.web.util.WebConstants;
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
public class SettlementServiceImpl implements SettlementService {
    private static final Logger logger = LoggerFactory.getLogger(SettlementServiceImpl.class);
    @Autowired
    private AdPositionMappingMapper adPositionMappingMapper;
    @Autowired
    private SettlementReportMapper settlementReportMapper;
    @Autowired
    private FlowConsumerMapper flowConsumerMapper;
    @Autowired
    private AppsMapper appsMapper;
    @Autowired
    private AdPositionMapper adPositionMapper;
    @Autowired
    private AppChannelMapper appChannelMapper;

    private static String[] REPORT_ARRAY_COMMON = new String[]{"date","positionId","positionName","channelName","exp",
            "clk","fr","ctr","cpm","cpc","expIncome","clkIncome","income"};

    @Override
    public void importSettlementReport(MultipartFile file) {
        if (file != null) {
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
            List<List<Object>> values = ExcelUtil.getValuesFromExcel(localPath);
            if (CollectionUtils.isNotEmpty(values)) {
                List<SettlementReport> reportList = Lists.newArrayList();
                for (List<Object> rowList : values) {
                    if (CollectionUtils.isNotEmpty(rowList)) {
                        SettlementReport report = new SettlementReport();
                        for (int i = 0; i < REPORT_ARRAY_COMMON.length; i++) {
                            try {
                                Field field = report.getClass().getDeclaredField(REPORT_ARRAY_COMMON[i]);
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
                    for (SettlementReport report : reportList) {
                        if (report != null) {
                            criteria.andConsumerPositionIdEqualTo(report.getPositionId());
                            List<AdPositionMapping> mappingList = adPositionMappingMapper.selectByExample(example);
                            if (CollectionUtils.isNotEmpty(mappingList)) {
                                report.setPid(mappingList.get(Constants.INTEGER_0).getAdPositionId());
                                report.setFlowCon(mappingList.get(Constants.INTEGER_0).getFlowConsumerId());
                                AdPosition position = adPositionMapper.selectByPrimaryKey(report.getPid());
                                if (position != null) {
                                    report.setApp(position.getAppId());
                                }
                            } else {
                                throw new ServiceException("文件中包含系统中没有的广告位ID，请修改后重新导入。");
                            }
                            AppChannelExample channelExample = new AppChannelExample();
                            channelExample.createCriteria().andNameEqualTo(report.getChannelName());
                            List<AppChannel> channelList = appChannelMapper.selectByExample(channelExample);
                            if (CollectionUtils.isNotEmpty(channelList)) {
                                AppChannel channel = channelList.get(Constants.INTEGER_0);
                                report.setChannel(channel.getId());
                            }
                        }

                    }
                    Iterator<SettlementReport> it = reportList.iterator();
                    while (it.hasNext()) {
                        SettlementReport report = it.next();
                        SettlementReportExample reportExample = new SettlementReportExample();
                        reportExample.createCriteria().andDateEqualTo(report.getDate())
                                .andChannelEqualTo(report.getChannel())
                                .andPositionIdEqualTo(report.getPositionId());
                        long count = settlementReportMapper.countByExample(reportExample);
                        if (count > 0) {
                            settlementReportMapper.updateByExampleSelective(report, reportExample);
                            it.remove();
                        }
                    }
                    settlementReportMapper.batchInsert(reportList);
                }
            } else {
                logger.error("file is empty, file : {}", fileName);
                throw new ServiceException("文件没有内容");
            }
            FileUtils.deleteQuietly(new File(localPath));
        } else {
            throw new ServiceException("文件或模板类型不能为空");
        }
    }

    @Override
    public List<SettlementReport> sumWithDate(String flowCons, String apps, String pids, String flowPosIds,
                                              String channels, Integer sDate, Integer eDate) {
        List<SettlementReport> resultList = Lists.newArrayList();
        if (sDate != null && eDate != null) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            if (StringUtils.isNotBlank(flowCons)) {
                List<Integer> flowConIds = StringUtils.str2List4Int(flowCons, Constants.SIGN_COMMA);
                paramMap.put("flowConIds", flowConIds);
            }
            if (StringUtils.isNotBlank(apps)) {
                List<Integer> appIds = StringUtils.str2List4Int(flowCons, Constants.SIGN_COMMA);
                paramMap.put("apps", appIds);
            }
//            if (StringUtils.isNotBlank(pids)) {
//                List<Integer> pidList = StringUtils.str2List4Int(pids, Constants.SIGN_COMMA);
//                paramMap.put("pids", pidList);
//            }
            if (StringUtils.isNotBlank(flowPosIds)) {
                List<String> posIds = StringUtils.str2List(flowPosIds, Constants.SIGN_COMMA);
                paramMap.put("posIds", posIds);
            }
            if (StringUtils.isNotBlank(channels)) {
                List<String> channelList = StringUtils.str2List(channels, Constants.SIGN_COMMA);
                paramMap.put("channels", channelList);
            }
            resultList = settlementReportMapper.sumReportWithDate(paramMap);
            Map<String, SettlementReport> quotaDayMap = Maps.newHashMap();
            for (SettlementReport report : resultList) {
                quotaDayMap.put(report.getDate().toString(), report);
            }
            Date sDay = DateUtils.parse(sDate.toString(), DateUtils.SHORT_FORMAT);
            Date eDay = DateUtils.parse(eDate.toString(), DateUtils.SHORT_FORMAT);
            for (Date startDate = sDay; startDate.compareTo(eDay) <= 0; startDate = DateUtils.getNextDaysDate(startDate,
                    1)) {
                String day = DateUtils.format(startDate, DateUtils.SHORT_FORMAT);
                if (!quotaDayMap.containsKey(day)) {
                    SettlementReport quota = new SettlementReport();
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
    public List<SettlementReport> sumWithGroup(String flowCons, String apps, String pids, String flowPosIds,
                                               String channels, String group, Integer sDate, Integer eDate) {
        List<SettlementReport> resultList = Lists.newArrayList();
        if (sDate != null && eDate != null && StringUtils.isNotBlank(group)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            paramMap.put("groupItem", group);
            if (StringUtils.isNotBlank(flowCons)) {
                List<Integer> flowConIds = StringUtils.str2List4Int(flowCons, Constants.SIGN_COMMA);
                paramMap.put("flowConIds", flowConIds);
            }
            if (StringUtils.isNotBlank(apps)) {
                List<Integer> appIds = StringUtils.str2List4Int(flowCons, Constants.SIGN_COMMA);
                paramMap.put("apps", appIds);
            }
//            if (StringUtils.isNotBlank(pids)) {
//                List<Integer> pidList = StringUtils.str2List4Int(pids, Constants.SIGN_COMMA);
//                paramMap.put("pids", pidList);
//            }
            if (StringUtils.isNotBlank(flowPosIds)) {
                List<String> posIds = StringUtils.str2List(flowPosIds, Constants.SIGN_COMMA);
                paramMap.put("posIds", posIds);
            }
            if (StringUtils.isNotBlank(channels)) {
                List<String> channelList = StringUtils.str2List(channels, Constants.SIGN_COMMA);
                paramMap.put("channels", channelList);
            }
            resultList = settlementReportMapper.sumReportWithGroup(paramMap);
            for (SettlementReport report : resultList) {
                if (report.getFlowCon() != null) {
                    FlowConsumer platform = flowConsumerMapper.selectByPrimaryKey(report.getFlowCon());
                    if (platform != null) {
                        report.setFlowConName(platform.getConsumerName());
                    }
                }
                if ("flow_con".equals(group)) {
                    FlowConsumer platform = flowConsumerMapper.selectByPrimaryKey(Integer.parseInt(report.getItemId()));
                    if (platform != null) {
                        report.setFlowConName(platform.getConsumerName());
                    }
                } else if ("app".equals(group)) {
                    Apps app = appsMapper.selectByPrimaryKey(Integer.parseInt(report.getItemId()));
                    if (app != null) {
                        report.setAppName(app.getAppName());
                    }
                } else if ("position_id".equals(group)) {
                    AdPositionMappingExample example = new AdPositionMappingExample();
                    example.createCriteria().andConsumerPositionIdEqualTo(report.getItemId());
                    List<AdPositionMapping> mappingList = adPositionMappingMapper.selectByExample(example);
                    if (CollectionUtils.isNotEmpty(mappingList)) {
                        report.setPositionName(mappingList.get(Constants.INTEGER_0).getConsumerPositionName());
                    }
                } else if ("channel".equals(group)) {
                    AppChannel channel = appChannelMapper.selectByPrimaryKey(Integer.parseInt(report.getItemId()));
                    if (channel != null) {
                        report.setChannelName(channel.getCname());
                    }
                }
            }
        }
        return resultList;
    }

    @Override
    public List<SettlementReport> detailReport(String id, String flowCons, String apps, String pids, String flowPosIds,
                                               String channels, String group, Integer sDate, Integer eDate) {
        List<SettlementReport> resultList = Lists.newArrayList();
        if (sDate != null && eDate != null) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            paramMap.put("groupItem", group);
            if ("flow_con".equals(group)) {
                flowCons = id;
            } else if ("app".equals(group)) {
                apps = id;
//            } else if ("pid".equals(group)) {
//                pids = id;
            } else if ("position_id".equals(group)) {
                flowPosIds = id;
            } else if ("channel".equals(group)) {
                channels = id;
            }
            if (StringUtils.isNotBlank(flowCons)) {
                List<Integer> flowConIds = StringUtils.str2List4Int(flowCons, Constants.SIGN_COMMA);
                paramMap.put("flowConIds", flowConIds);
            }
            if (StringUtils.isNotBlank(apps)) {
                List<Integer> appIds = StringUtils.str2List4Int(flowCons, Constants.SIGN_COMMA);
                paramMap.put("apps", appIds);
            }
//            if (StringUtils.isNotBlank(pids)) {
//                List<Integer> pidList = StringUtils.str2List4Int(pids, Constants.SIGN_COMMA);
//                paramMap.put("pids", pidList);
//            }
            if (StringUtils.isNotBlank(flowPosIds)) {
                List<String> posIds = StringUtils.str2List(flowPosIds, Constants.SIGN_COMMA);
                paramMap.put("posIds", posIds);
            }
            if (StringUtils.isNotBlank(channels)) {
                List<String> channelList = StringUtils.str2List(channels, Constants.SIGN_COMMA);
                paramMap.put("channels", channelList);
            }
            resultList = settlementReportMapper.detailReport(paramMap);
            Map<String, SettlementReport> quotaDayMap = Maps.newHashMap();
            for (SettlementReport report : resultList) {
                quotaDayMap.put(report.getDate().toString(), report);
            }
            Date sDay = DateUtils.parse(sDate.toString(), DateUtils.SHORT_FORMAT);
            Date eDay = DateUtils.parse(eDate.toString(), DateUtils.SHORT_FORMAT);
            for (Date startDate = sDay; startDate.compareTo(eDay) <= 0; startDate = DateUtils.getNextDaysDate(startDate,
                    1)) {
                String day = DateUtils.format(startDate, DateUtils.SHORT_FORMAT);
                if (!quotaDayMap.containsKey(day)) {
                    SettlementReport quota = new SettlementReport();
                    quota.setDate(Integer.parseInt(day));
                    quotaDayMap.put(day, quota);
                }
            }
            resultList = Lists.newArrayList(quotaDayMap.values());
            for (SettlementReport report : resultList) {
                if ("flow_con".equals(group)) {
                    FlowConsumer platform = flowConsumerMapper.selectByPrimaryKey(Integer.parseInt(id));
                    if (platform != null) {
                        report.setItemName(platform.getConsumerName());
                    }
                } else if ("app".equals(group)) {
                    Apps app = appsMapper.selectByPrimaryKey(Integer.parseInt(id));
                    if (app != null) {
                        report.setItemName(app.getAppName());
                    }
//                } else if ("pid".equals(group)) {
//                    AdPosition adp = adPositionMapper.selectByPrimaryKey(Integer.parseInt(id));
//                    if (adp != null) {
//                        report.setItemName(adp.getName());
//                    }
                } else if ("position_id".equals(group)) {
                    AdPositionMappingExample example = new AdPositionMappingExample();
                    example.createCriteria().andConsumerPositionIdEqualTo(id);
                    List<AdPositionMapping> mappingList = adPositionMappingMapper.selectByExample(example);
                    if (CollectionUtils.isNotEmpty(mappingList)) {
                        report.setItemName(mappingList.get(Constants.INTEGER_0).getConsumerPositionName());
                    }
                } else if ("channel".equals(group)) {
                    AppChannel channel = appChannelMapper.selectByPrimaryKey(Integer.parseInt(report.getItemId()));
                    if (channel != null) {
                        report.setChannelName(channel.getCname());
                    }
                }
            }
            resultList.sort((o1, o2) -> o1.getDate() < o2.getDate() ? -1 : 1);
        }
        return resultList;
    }
}
