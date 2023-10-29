package com.iwanvi.nvwa.web.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwanvi.nvwa.common.utils.*;
import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.web.service.AdPositionService;
import com.iwanvi.nvwa.web.service.QuotaFlowService;
import com.iwanvi.nvwa.web.util.WebConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuotaFlowServiceImpl implements QuotaFlowService {
    private static final Logger logger = LoggerFactory.getLogger(QuotaFlowServiceImpl.class);

    @Autowired
    private QuotaFlowMapper quotaFlowMapper;
    @Autowired
    private FlowSourceMapper flowSourceMapper;
    @Autowired
    private AppsMapper appsMapper;
    @Autowired
    private AdPositionMapper adPositionMapper;
    @Autowired
    private AdPositionService adPositionService;
    @Autowired
    private AppChannelMapper appChannelMapper;
    @Autowired
    private AppVersionMapper appVersionMapper;

    @Override
    public List<QuotaFlow> sumWithDate(String flowId, String appids, String adpids, String channels, String subChannels,
                                       String versions, Integer sDate, Integer eDate) {
        List<QuotaFlow> resultList = Lists.newArrayList();
        if (sDate != null && eDate != null) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            String today = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
            paramMap.put("flowId", flowId);
            if (StringUtils.isNotBlank(appids)) {
                List<String> appIds = StringUtils.str2List(appids, Constants.SIGN_COMMA);
                paramMap.put("apps", appIds);
            }
            if (StringUtils.isNotBlank(adpids)) {
                List<String> pidList = StringUtils.str2List(adpids, Constants.SIGN_COMMA);
                paramMap.put("pids", pidList);
            }
            if (StringUtils.isNotBlank(channels)) {
                List<String> channelids = StringUtils.str2List(channels, Constants.SIGN_COMMA);
                paramMap.put("channels1", channelids);
            }
            if (StringUtils.isNotBlank(subChannels)) {
                List<String> subChannelsids = StringUtils.str2List(subChannels, Constants.SIGN_COMMA);
                paramMap.put("channels2", subChannelsids);
            }
            if (StringUtils.isNotBlank(versions)) {
                List<String> versionids = StringUtils.str2List(versions, Constants.SIGN_COMMA);
                paramMap.put("versions", versionids);
            }
            resultList = quotaFlowMapper.sumWithDate(paramMap);
            Map<String, QuotaFlow> quotaDayMap = Maps.newHashMap();
            for (QuotaFlow report : resultList) {
                quotaDayMap.put(report.getCreDay().toString(), report);
            }
            Date sDay = DateUtils.parse(sDate.toString(), DateUtils.SHORT_FORMAT);
            Date eDay = DateUtils.parse(eDate.toString(), DateUtils.SHORT_FORMAT);
            for (Date startDate = sDay; startDate.compareTo(eDay) <= 0; startDate = DateUtils.getNextDaysDate(startDate,
                    1)) {
                String day = DateUtils.format(startDate, DateUtils.SHORT_FORMAT);
                if (!quotaDayMap.containsKey(day)) {
                    QuotaFlow quota = new QuotaFlow();
                    quota.setCreDay(Integer.parseInt(day));
                    quotaDayMap.put(day, quota);
                }
            }
            if (eDate >= Integer.parseInt(today)) {
                QuotaFlow quota = quotaDayMap.get(today);
                String query = buildQuery(appids, adpids, channels, subChannels, versions);
                QuotaFlow todayQuota = getTodayQuotaFromLog(query);
                quota.setReq(quota.getReq() + todayQuota.getReq());
                quota.setRequv(quota.getRequv() + todayQuota.getRequv());
                quota.setExp(quota.getExp() + todayQuota.getExp());
                quota.setExpuv(quota.getExpuv() + todayQuota.getExpuv());
                quota.setClk(quota.getClk() + todayQuota.getClk());
                quota.setClkuv(quota.getClkuv() + todayQuota.getClkuv());
                quota.setInvest(quota.getInvest() + todayQuota.getInvest());
                quota.setBid(quota.getBid() + todayQuota.getBid());
            }
            resultList = Lists.newArrayList(quotaDayMap.values());
            resultList.sort((o1, o2) -> o1.getCreDay() < o2.getCreDay() ? -1 : 1);
        }
        return resultList;
    }

    @Override
    public List<QuotaFlow> sumWithGroup(String flowId, String appids, String adpids, String channels1, String channels2,
                                        String versions, String group, Integer sDate, Integer eDate) {
        List<QuotaFlow> resultList = Lists.newArrayList();
        if (sDate != null && eDate != null) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            String today = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
            if (StringUtils.isNotBlank(group)) {
                paramMap.put("groupItem", group);
            } else {
                paramMap.put("groupItem", "flow_id");
            }
            if (StringUtils.isNotBlank(flowId)) {
                paramMap.put("flowId", flowId);
            }
            if (StringUtils.isNotBlank(appids)) {
                List<String> appIds = StringUtils.str2List(appids, Constants.SIGN_COMMA);
                paramMap.put("apps", appIds);
            }
            if (StringUtils.isNotBlank(adpids)) {
                List<String> pidList = StringUtils.str2List(adpids, Constants.SIGN_COMMA);
                paramMap.put("pids", pidList);
            }
            if (StringUtils.isNotBlank(channels1)) {
                List<String> channelids = StringUtils.str2List(channels1, Constants.SIGN_COMMA);
                paramMap.put("channels1", channelids);
            }
            if (StringUtils.isNotBlank(channels2)) {
                List<String> channelids = StringUtils.str2List(channels2, Constants.SIGN_COMMA);
                paramMap.put("channels2", channelids);
            }
            if (StringUtils.isNotBlank(versions)) {
                List<String> versionids = StringUtils.str2List(versions, Constants.SIGN_COMMA);
                paramMap.put("versions", versionids);
            }
            resultList = quotaFlowMapper.sumWithGroup(paramMap);
            List<String> itemList = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(resultList)) {
                itemList = Lists.transform(resultList, QuotaFlow::getItemId);
            }
            List<String> list = Lists.newArrayList();
            String logGroup = StringUtils.EMPTY;
            if ("appid".equals(group)) {
                if (paramMap.get("apps") != null) {
                    list = (List<String>) paramMap.get("apps");
                } else {
                    AppsExample appsExample = new AppsExample();
                    appsExample.createCriteria().andMediasEqualTo(flowId);
                    List<Apps> apps = appsMapper.selectByExample(appsExample);
                    list = Lists.transform(apps, Apps::getAppId);
                }
                logGroup = "appId";
            } else if ("adpid".equals(group)) {
                if (paramMap.get("pids") != null) {
                    list = (List<String>) paramMap.get("pids");
                } else {
                    List<AdPosition> adpList = Lists.newArrayList();
                    if (StringUtils.isNotBlank(appids)) {
                        adpList = adPositionService.getPositionByApp(appids);
                    } else {
                        adpList = adPositionMapper.selectByExample(null);
                    }
                    list = Lists.transform(adpList, AdPosition::getUuid);
                }
                logGroup = "posId";
            } else if ("channel1".equals(group)) {
                if (paramMap.get("channels1") != null) {
                    list = (List<String>) paramMap.get("channels1");
                } else {
                    List<AppChannel> channelList = appChannelMapper.selectByExample(null);
                    list = Lists.transform(channelList, AppChannel::getName);
                }
                logGroup = "channel1";
            } else if ("channel2".equals(group)) {
                if (paramMap.get("channels2") != null) {
                    list = (List<String>) paramMap.get("channels2");
                } else {
                    AppChannelExample example = new AppChannelExample();
                    example.createCriteria().andParentIdIsNotNull();
                    List<AppChannel> channelList = appChannelMapper.selectByExample(example);
                    list = Lists.transform(channelList, AppChannel::getName);
                }
                logGroup = "channel2";
            } else if ("versions".equals(group)) {
                if (paramMap.get("versions") != null) {
                    list = (List<String>) paramMap.get("versions");
                } else {
                    List<AppVersion> versionList = appVersionMapper.selectByExample(null);
                    list = Lists.transform(versionList, AppVersion::getName);
                }
                logGroup = "version";
            } else {
                list = Arrays.asList(WebConstants.AIKA_UUID);
            }
            if (CollectionUtils.isNotEmpty(list)) {
                for (String item : list) {
                    if (!itemList.contains(item)) {
                        QuotaFlow quota = new QuotaFlow();
                        quota.setItemId(item);
                        quota.setFlowId(flowId);
                        resultList.add(quota);
                    }
                }
            }
            String query = buildQuery(appids, adpids, channels1, channels2, versions);
            Map<String, QuotaFlow> multiQuota = null;
            if (StringUtils.isNotBlank(group) && eDate >= Integer.parseInt(today)) {
                multiQuota = getTodayQuotaFromLogWithGroup(query, logGroup);
            }

            for (QuotaFlow report : resultList) {
                if (report != null) {
                    if (StringUtils.isNotBlank(report.getFlowId())) {
                        FlowSourceExample example = new FlowSourceExample();
                        example.createCriteria().andMediaUuidEqualTo(report.getFlowId());
                        List<FlowSource> flows = flowSourceMapper.selectByExample(example);
                        if (CollectionUtils.isNotEmpty(flows)) {
                            report.setFsName(flows.get(Constants.INTEGER_0).getMediaName());
                            report.setFsId(flows.get(Constants.INTEGER_0).getId().toString());
                        }
                    }
                    if ("appid".equals(group)) {
                        if (eDate >= Integer.parseInt(today) && multiQuota != null) {
                            QuotaFlow todayQuota = multiQuota.get(report.getItemId());
                            if (todayQuota != null) {
                                addQuota(report, todayQuota);
                            }
                        }
                        AppsExample example = new AppsExample();
                        example.createCriteria().andAppIdEqualTo(report.getItemId());
                        List<Apps> apps = appsMapper.selectByExample(example);
                        if (CollectionUtils.isNotEmpty(apps)) {
                            report.setAppName(apps.get(Constants.INTEGER_0).getAppName());
                        } else {//数据库查不到信息的不显示
                            report.setReq(0l);
                            report.setExp(0);
                            report.setClk(0);
                        }
                    } else if ("adpid".equals(group)) {
                        if (eDate >= Integer.parseInt(today) && multiQuota != null) {
                            QuotaFlow todayQuota = multiQuota.get(report.getItemId());
                            if (todayQuota != null) {
                                addQuota(report, todayQuota);
                            }
                        }
                        AdPositionExample example = new AdPositionExample();
                        example.createCriteria().andUuidEqualTo(report.getItemId());
                        List<AdPosition> adps = adPositionMapper.selectByExample(example);
                        if (CollectionUtils.isNotEmpty(adps)) {
                            AdPosition position = adps.get(Constants.INTEGER_0);
                            Apps app = appsMapper.selectByPrimaryKey(position.getAppId());
                            String name = position.getName();
                            if (app != null) {
                                name += "-" + app.getAppName();
                            }
                            report.setAdpName(name);
                        } else {
                            report.setReq(0l);
                            report.setExp(0);
                            report.setClk(0);
                        }
                    } else if ("channel1".equals(group)) {
                        if (eDate >= Integer.parseInt(today) && multiQuota != null) {
                            QuotaFlow todayQuota = multiQuota.get(report.getItemId());
                            if (todayQuota != null) {
                                addQuota(report, todayQuota);
                            }
                        }
                        AppChannelExample example = new AppChannelExample();
                        example.createCriteria().andNameEqualTo(report.getItemId());
                        List<AppChannel> channelList = appChannelMapper.selectByExample(example);
                        if (CollectionUtils.isNotEmpty(channelList)) {
                            AppChannel c = channelList.get(Constants.INTEGER_0);
                            report.setChannel1Name(c.getName() + "-" + c.getCname());
                        } else {
                            report.setReq(0l);
                            report.setExp(0);
                            report.setClk(0);
                        }
                    } else if ("channel2".equals(group)) {
                        if (eDate >= Integer.parseInt(today) && multiQuota != null) {
                            QuotaFlow todayQuota = multiQuota.get(report.getItemId());
                            if (todayQuota != null) {
                                addQuota(report, todayQuota);
                            }
                        }
                        if (StringUtils.isBlank(report.getItemId())) {
                            report.setReq(0l);
                            report.setExp(0);
                            report.setClk(0);
                            continue;
                        }
                        AppChannelExample example = new AppChannelExample();
                        example.createCriteria().andNameEqualTo(report.getItemId());
                        List<AppChannel> channelList = appChannelMapper.selectByExample(example);
                        if (CollectionUtils.isNotEmpty(channelList)) {
                            AppChannel c = channelList.get(Constants.INTEGER_0);
                            report.setChannel2Name(c.getName() + "-" + c.getCname());
                        } else {
                            report.setReq(0l);
                            report.setExp(0);
                            report.setClk(0);
                        }
                    } else if ("versions".equals(group)) {
                        if (eDate >= Integer.parseInt(today) && multiQuota != null) {
                            QuotaFlow todayQuota = multiQuota.get(report.getItemId());
                            if (todayQuota != null) {
                                addQuota(report, todayQuota);
                            }
                        }
                        report.setVersion(report.getItemId());
                    } else if (eDate >= Integer.parseInt(today)) {
                        QuotaFlow todayQuota = getTodayQuotaFromLog(query);
                        report.setReq(report.getReq() + todayQuota.getReq());
                        report.setRequv(report.getRequv() + todayQuota.getRequv());
                        report.setExp(report.getExp() + todayQuota.getExp());
                        report.setExpuv(report.getExpuv() + todayQuota.getExpuv());
                        report.setClk(report.getClk() + todayQuota.getClk());
                        report.setClkuv(report.getClkuv() + todayQuota.getClkuv());
                        report.setInvest(report.getInvest() + todayQuota.getInvest());
                        report.setBid(report.getBid() + todayQuota.getBid());
                    }
                }
            }
            resultList = resultList.stream().filter(item -> item.getReq() > 0 || item.getExp() > 0 || item.getClk() > 0)
                    .collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public List<QuotaFlow> detailReport(String id, String flowId, String appids, String adpids, String channels1,
                                        String channels2, String versions, String group, Integer sDate, Integer eDate) {
        List<QuotaFlow> resultList = Lists.newArrayList();
        if (sDate != null && eDate != null) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            String today = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
            paramMap.put("flowId", flowId);
            if ("appid".equals(group)) {
                appids = id;
            } else if ("adpid".equals(group)) {
                adpids = id;
            } else if ("channel1".equals(group)) {
                channels1 = id;
            } else if ("channel2".equals(group)) {
                channels2 = id;
            } else if ("versions".equals(group)) {
                versions = id;
            }
            if (StringUtils.isNotBlank(appids)) {
                List<String> appIds = StringUtils.str2List(appids, Constants.SIGN_COMMA);
                paramMap.put("apps", appIds);
            }
            if (StringUtils.isNotBlank(adpids)) {
                List<String> pidList = StringUtils.str2List(adpids, Constants.SIGN_COMMA);
                paramMap.put("pids", pidList);
            }
            if (StringUtils.isNotBlank(channels1)) {
                List<String> channelids = StringUtils.str2List(channels1, Constants.SIGN_COMMA);
                paramMap.put("channels1", channelids);
            }
            if (StringUtils.isNotBlank(channels2)) {
                List<String> channelids = StringUtils.str2List(channels2, Constants.SIGN_COMMA);
                paramMap.put("channels2", channelids);
            }
            if (StringUtils.isNotBlank(versions)) {
                List<String> versionids = StringUtils.str2List(versions, Constants.SIGN_COMMA);
                paramMap.put("versions", versionids);
            }
            resultList = quotaFlowMapper.detailReport(paramMap);
            Map<String, QuotaFlow> quotaDayMap = Maps.newHashMap();
            for (QuotaFlow report : resultList) {
                quotaDayMap.put(report.getCreDay().toString(), report);
            }
            Date sDay = DateUtils.parse(sDate.toString(), DateUtils.SHORT_FORMAT);
            Date eDay = DateUtils.parse(eDate.toString(), DateUtils.SHORT_FORMAT);
            for (Date startDate = sDay; startDate.compareTo(eDay) <= 0; startDate = DateUtils.getNextDaysDate(startDate,
                    1)) {
                String day = DateUtils.format(startDate, DateUtils.SHORT_FORMAT);
                if (!quotaDayMap.containsKey(day)) {
                    QuotaFlow quota = new QuotaFlow();
                    quota.setCreDay(Integer.parseInt(day));
                    quotaDayMap.put(day, quota);
                }
            }
            if (eDate >= Integer.parseInt(today)) {
                QuotaFlow quota = quotaDayMap.get(today);
                String query = "";
                if ("appid".equals(group)) {
                    query = buildQuery(id, adpids, channels1, channels2, versions);
                } else if ("adpid".equals(group)) {
                    query = buildQuery(appids, id, channels1, channels2, versions);
                } else if ("channel1".equals(group)) {
                    query = buildQuery(appids, adpids, id, channels2, versions);
                } else if ("channel2".equals(group)) {
                    query = buildQuery(appids, adpids, channels1, id, versions);
                } else if ("versions".equals(group)) {
                    query = buildQuery(appids, adpids, channels1, channels2, id);
                }
                QuotaFlow todayQuota = getTodayQuotaFromLog(query);
                quota.setReq(quota.getReq() + todayQuota.getReq());
                quota.setRequv(quota.getRequv() + todayQuota.getRequv());
                quota.setExp(quota.getExp() + todayQuota.getExp());
                quota.setExpuv(quota.getExpuv() + todayQuota.getExpuv());
                quota.setClk(quota.getClk() + todayQuota.getClk());
                quota.setClkuv(quota.getClkuv() + todayQuota.getClkuv());
                quota.setInvest(quota.getInvest() + todayQuota.getInvest());
                quota.setBid(quota.getBid() + todayQuota.getBid());
            }
            resultList = Lists.newArrayList(quotaDayMap.values());
            for (QuotaFlow report : resultList) {
                if ("appid".equals(group)) {
                    AppsExample example = new AppsExample();
                    example.createCriteria().andAppIdEqualTo(id);
                    List<Apps> apps = appsMapper.selectByExample(example);
                    if (CollectionUtils.isNotEmpty(apps)) {
                        report.setItemName(apps.get(Constants.INTEGER_0).getAppName());
                    }
                } else if ("adpid".equals(group)) {
                    AdPositionExample example = new AdPositionExample();
                    example.createCriteria().andUuidEqualTo(id);
                    List<AdPosition> adps = adPositionMapper.selectByExample(example);
                    if (CollectionUtils.isNotEmpty(adps)) {
                        report.setItemName(adps.get(Constants.INTEGER_0).getName());
                    }
                } else if ("channel1".equals(group)) {
                    AppChannelExample example = new AppChannelExample();
                    example.createCriteria().andNameEqualTo(id);
                    List<AppChannel> channelList = appChannelMapper.selectByExample(example);
                    if (CollectionUtils.isNotEmpty(channelList)) {
                        AppChannel c = channelList.get(Constants.INTEGER_0);
                        report.setItemName(c.getName() + "-" + c.getCname());
                    }
                } else if ("channel2".equals(group)) {
                    AppChannelExample example = new AppChannelExample();
                    example.createCriteria().andNameEqualTo(id);
                    List<AppChannel> channelList = appChannelMapper.selectByExample(example);
                    if (CollectionUtils.isNotEmpty(channelList)) {
                        AppChannel c = channelList.get(Constants.INTEGER_0);
                        report.setItemName(c.getName() + "-" + c.getCname());
                    }
                } else if ("versions".equals(group)) {
                    report.setItemName(id);
                }
            }
            resultList.sort((o1, o2) -> o1.getCreDay() < o2.getCreDay() ? -1 : 1);
        }
        return resultList;
    }

    private void addQuota(QuotaFlow report, QuotaFlow todayQuota) {
        report.setReq(report.getReq() + todayQuota.getReq());
        report.setRequv(report.getRequv() + todayQuota.getRequv());
        report.setExp(report.getExp() + todayQuota.getExp());
        report.setExpuv(report.getExpuv() + todayQuota.getExpuv());
        report.setClk(report.getClk() + todayQuota.getClk());
        report.setClkuv(report.getClkuv() + todayQuota.getClkuv());
        report.setInvest(report.getInvest() + todayQuota.getInvest());
        report.setBid(report.getBid() + todayQuota.getBid());
    }
    private QuotaFlow getTodayQuotaFromLog(String query) {
        QuotaFlow quota = new QuotaFlow();
        int from = (int) (DateUtils.getFirstSecondOfHour(new Date()).getTime() / 1000);
        int to = (int) (new Date().getTime() / 1000);
        String queryUv = StringUtils.concat( " *|SELECT COUNT(DISTINCT (did))", query);
        String queryCost = StringUtils.concat(" *|SELECT sum(cost)", query);
        String queryCommon = StringUtils.concat(" *|SELECT count(*)", query);
        Long req = AliyunLogUtil.getTodayNumByTypeAndParamsWithSql(Constants.ALIYUN_LOG_STORE_REQ, queryCommon, from, to);
        Long requv = AliyunLogUtil.getTodayNumByTypeAndParamsWithSql(Constants.ALIYUN_LOG_STORE_REQ, queryUv, from, to);
        Long clk = AliyunLogUtil.getTodayNumByTypeAndParamsWithSql(Constants.ALIYUN_LOG_STORE_CLK, queryCommon, from, to);
        Long clkuv = AliyunLogUtil.getTodayNumByTypeAndParamsWithSql(Constants.ALIYUN_LOG_STORE_CLK, queryUv, from, to);
        Long exp = AliyunLogUtil.getTodayNumByTypeAndParamsWithSql(Constants.ALIYUN_LOG_STORE_EXP, queryCommon, from, to);
        Long expuv = AliyunLogUtil.getTodayNumByTypeAndParamsWithSql(Constants.ALIYUN_LOG_STORE_EXP, queryUv, from, to);
        Long cost = AliyunLogUtil.getTodayNumByTypeAndParamsWithSql(Constants.ALIYUN_LOG_STORE_EXP, queryCost, from, to);
        quota.setReq(req.longValue());
        quota.setRequv(requv.intValue());
        quota.setExp(exp.intValue());
        quota.setExpuv(expuv.intValue());
        quota.setClk(clk.intValue());
        quota.setClkuv(clkuv.intValue());
        quota.setInvest(cost);
        return quota;
    }
    private Map<String,QuotaFlow> getTodayQuotaFromLogWithGroup(String query, String group) {
        Map<String,QuotaFlow> groupMap = Maps.newHashMap();
        int from = (int) (DateUtils.getFirstSecondOfHour(new Date()).getTime() / 1000);
        int to = (int) (new Date().getTime() / 1000);

        String queryCommon = StringUtils.concat(" count(*) num, count(distinct(did)) uv ", query);
        if (StringUtils.isNotBlank(group)) {
            queryCommon = group + Constants.SIGN_COMMA + queryCommon;
            queryCommon += " group by ";
            queryCommon += group;
        }
        queryCommon = "*| select " +queryCommon;

        String queryExp = StringUtils.concat(" count(*) num, sum(cost) cost, count(distinct(did)) uv ", query);
        if (StringUtils.isNotBlank(group)) {
            queryExp = group + Constants.SIGN_COMMA + queryExp;
            queryExp += " group by ";
            queryExp += group;
        }
        queryExp = "*| select " + queryExp;

        List<Map<String, Object>> reqList = AliyunLogUtil.getQuotaWithSql(Constants.ALIYUN_LOG_STORE_REQ, queryCommon, from, to);
        List<Map<String, Object>> expList = AliyunLogUtil.getQuotaWithSql(Constants.ALIYUN_LOG_STORE_EXP, queryExp, from, to);
        List<Map<String, Object>> clkList = AliyunLogUtil.getQuotaWithSql(Constants.ALIYUN_LOG_STORE_CLK, queryCommon, from, to);
        QuotaFlow quota;
        if (CollectionUtils.isNotEmpty(reqList)) {
            for (Map<String, Object> itemMap : reqList) {
                String index = itemMap.get(group).toString();
                if (StringUtils.isNotBlank(index)) {
                    if ( groupMap.get(index) != null ) {
                        quota = groupMap.get(index);
                    } else {
                        quota = new QuotaFlow();
                    }
                    quota.setReq(NvwaUtils.obj2long(itemMap.get("num"),0l));
                    quota.setRequv(NvwaUtils.obj2int(itemMap.get("uv")));
                    groupMap.put(index, quota);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(expList)) {
            for (Map<String, Object> itemMap : expList) {
                String index = itemMap.get(group).toString();
                if (StringUtils.isNotBlank(index)) {
                    if ( groupMap.get(index) != null ) {
                        quota = groupMap.get(index);
                    } else {
                        quota = new QuotaFlow();
                    }
                    quota.setInvest(NvwaUtils.obj2long(itemMap.get("cost"),0l));
                    quota.setExp(NvwaUtils.obj2int(itemMap.get("num")));
                    quota.setExpuv(NvwaUtils.obj2int(itemMap.get("uv")));
                    groupMap.put(index, quota);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(clkList)) {
            for (Map<String, Object> itemMap : clkList) {
                String index = itemMap.get(group).toString();
                if (StringUtils.isNotBlank(index)) {
                    if ( groupMap.get(index) != null ) {
                        quota = groupMap.get(index);
                    } else {
                        quota = new QuotaFlow();
                    }
                    quota.setClk(NvwaUtils.obj2int(itemMap.get("num")));
                    quota.setClkuv(NvwaUtils.obj2int(itemMap.get("uv")));
                    groupMap.put(index, quota);
                }
            }
        }

        return groupMap;
    }

    private String buildQuery(String appids, String adpids, String channels, String subChannels, String versions) {
        StringBuilder query = new StringBuilder();
        String subQuery = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(appids)) {
            subQuery = appids.replaceAll(Constants.SIGN_COMMA, "','");
            if (StringUtils.isNotBlank(subQuery)) {
                query.append(" and appId in ('");
                query.append(subQuery);
                query.append("')");
            }
        }
        if (StringUtils.isNotBlank(adpids)) {
            subQuery = adpids.replaceAll(Constants.SIGN_COMMA, "','");
            if (StringUtils.isNotBlank(subQuery)) {
                query.append(" and posId in ('");
                query.append(subQuery);
                query.append("')");
            }
        }
        if (StringUtils.isNotBlank(channels)) {
            subQuery = channels.replaceAll(Constants.SIGN_COMMA, "','");
            if (StringUtils.isNotBlank(subQuery)) {
                query.append(" and channel1 in ('");
                query.append(subQuery);
                query.append("')");
            }
        }
        if (StringUtils.isNotBlank(subChannels)) {
            subQuery = subChannels.replaceAll(Constants.SIGN_COMMA, "','");
            if (StringUtils.isNotBlank(subQuery)) {
                query.append(" and channel2 in ('");
                query.append(subQuery);
                query.append("')");
            }
        }
        if (StringUtils.isNotBlank(versions)) {
            subQuery = versions.replaceAll(Constants.SIGN_COMMA, "','");
            if (StringUtils.isNotBlank(subQuery)) {
                query.append(" and version in ('");
                query.append(subQuery);
                query.append("')");
            }
        }
        String queryString = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(query)) {
            queryString = " where " + query.substring(5);
        }
        return queryString;
    }


}
