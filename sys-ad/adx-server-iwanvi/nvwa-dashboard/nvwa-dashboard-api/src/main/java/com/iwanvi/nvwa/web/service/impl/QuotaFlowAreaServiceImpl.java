package com.iwanvi.nvwa.web.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwanvi.nvwa.common.utils.*;
import com.iwanvi.nvwa.dao.AreaMapper;
import com.iwanvi.nvwa.dao.QuotaFlowAreaMapper;
import com.iwanvi.nvwa.dao.model.Area;
import com.iwanvi.nvwa.dao.model.AreaExample;
import com.iwanvi.nvwa.dao.model.QuotaFlowArea;
import com.iwanvi.nvwa.web.service.QuotaFlowAreaService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class QuotaFlowAreaServiceImpl implements QuotaFlowAreaService {

    @Autowired
    private QuotaFlowAreaMapper quotaFlowAreaMapper;
    @Autowired
    private AreaMapper areaMapper;

    @Override
    public List<QuotaFlowArea> sumByDay(Integer sDay, Integer eDay, String areas) {
        List<QuotaFlowArea> resultList = Lists.newArrayList();
        if (sDay != null && eDay != null) {
            Map<String, Object> params = Maps.newHashMap();
            params.put("sDate", sDay);
            params.put("eDate", eDay);
            if (StringUtils.isNotBlank(areas)) {
                List<String> areaList = StringUtils.str2List(areas, Constants.SIGN_COMMA);
                if (CollectionUtils.isNotEmpty(areaList)) {
                    params.put("areas", areaList);
                }
            }
            resultList = quotaFlowAreaMapper.sumByDay(params);
            Map<String, QuotaFlowArea> multiMap = getQuotaMapFromLog(areas);
            if (CollectionUtils.isNotEmpty(resultList)) {
                for (QuotaFlowArea quota : resultList) {
                    QuotaFlowArea nowQuota = multiMap.get(quota.getArea());
                    if (nowQuota != null) {
                        addQuota(quota, nowQuota);
                    }
                }
            } else {
                resultList.addAll(multiMap.values());
            }
            if (CollectionUtils.isNotEmpty(resultList)) {
                for (QuotaFlowArea quota : resultList) {
                    String areaCode = quota.getArea();
                    if (StringUtils.isNotBlank(areaCode)) {
                        AreaExample example = new AreaExample();
                        example.createCriteria().andAreaCodeEqualTo(Integer.parseInt(areaCode));
                        List<Area> areaList = areaMapper.selectByExample(example);
                        if (CollectionUtils.isNotEmpty(areaList)) {
                            quota.setAreaName(areaList.get(Constants.INTEGER_0).getAreaName());
                        }
                    }
                }
            }
        }
        return resultList;
    }

    private void addQuota(QuotaFlowArea report, QuotaFlowArea todayQuota) {
        report.setReq(report.getReq() + todayQuota.getReq());
        report.setRequv(report.getRequv() + todayQuota.getRequv());
        report.setExp(report.getExp() + todayQuota.getExp());
        report.setExpuv(report.getExpuv() + todayQuota.getExpuv());
        report.setClk(report.getClk() + todayQuota.getClk());
        report.setClkuv(report.getClkuv() + todayQuota.getClkuv());
        report.setInvest(report.getInvest() + todayQuota.getInvest());
    }

    @Override
    public List<QuotaFlowArea> detailReport(Integer sDay, Integer eDay, String area) {
        List<QuotaFlowArea> resultList = Lists.newArrayList();
        if (sDay != null && eDay != null && StringUtils.isNotBlank(area)) {
            Map<String, Object> params = Maps.newHashMap();
            params.put("sDate", sDay);
            params.put("eDate", eDay);
            List<String> areas = Lists.newArrayList(area);
            params.put("areas", areas);
            resultList = quotaFlowAreaMapper.detail(params);
            Map<String, QuotaFlowArea> quotaDayMap = Maps.newHashMap();
            for (QuotaFlowArea report : resultList) {
                quotaDayMap.put(report.getCreDay().toString(), report);
            }
            Date sDate = DateUtils.parse(sDay.toString(), DateUtils.SHORT_FORMAT);
            Date eDate = DateUtils.parse(eDay.toString(), DateUtils.SHORT_FORMAT);
            for (Date startDate = sDate; startDate.compareTo(eDate) <= 0; startDate = DateUtils.getNextDaysDate(
                    startDate, 1)) {
                String day = DateUtils.format(startDate, DateUtils.SHORT_FORMAT);
                if (!quotaDayMap.containsKey(day)) {
                    QuotaFlowArea quota = new QuotaFlowArea();
                    quota.setCreDay(Integer.parseInt(day));
                    quota.setArea(area);
                    quotaDayMap.put(day, quota);
                }
            }
            String today = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
            if (eDay >= Integer.parseInt(today)) {
                QuotaFlowArea quota = quotaDayMap.get(today);
                Map<String, QuotaFlowArea> multiQuotaMap = getQuotaMapFromLog(area);
                if (MapUtils.isNotEmpty(multiQuotaMap)) {
                    QuotaFlowArea nowQuota = multiQuotaMap.get(area);
                    if (nowQuota != null) {
                        addQuota(quota, nowQuota);
                    }
                }
            }
            resultList = Lists.newArrayList(quotaDayMap.values());
            if (CollectionUtils.isNotEmpty(resultList)) {
                for (QuotaFlowArea quota : resultList) {
                    String areaCode = quota.getArea();
                    if (StringUtils.isNotBlank(areaCode)) {
                        AreaExample example = new AreaExample();
                        example.createCriteria().andAreaCodeEqualTo(Integer.parseInt(areaCode));
                        List<Area> areaList = areaMapper.selectByExample(example);
                        if (CollectionUtils.isNotEmpty(areaList)) {
                            quota.setAreaName(areaList.get(Constants.INTEGER_0).getAreaName());
                        }
                    }
                }
            }
        }
        resultList.sort((o1, o2) -> o1.getCreDay() >= o2.getCreDay() ? 1 : -1);
        return resultList;
    }

    private Map<String, QuotaFlowArea> getQuotaMapFromLog(String areas) {
        Map<String, QuotaFlowArea> multiQuotaMap = Maps.newHashMap();
        int from = (int) (DateUtils.getFirstSecondOfHour(new Date()).getTime() / 1000);
        int to = (int) (new Date().getTime() / 1000);
        StringBuilder query = new StringBuilder("*| select areacode, count(*) num, count(distinct(did)) uv ");
        StringBuilder queryExp = new StringBuilder("*| select areacode, count(*) num, count(distinct(did)) uv, sum(cost) cost ");
        if (StringUtils.isNotBlank(areas)) {
            query.append(" where areacode in (");
            query.append(areas);
            query.append(")");
            queryExp.append(" where areacode in (");
            queryExp.append(areas);
            queryExp.append(")");
        }
        query.append(" group by areacode  order by num desc limit 400 ");
        queryExp.append(" group by areacode  order by num desc limit 400 ");
        List<Map<String, Object>> reqList = AliyunLogUtil.getQuotaWithSql("ads-req", query.toString(), from, to);
        List<Map<String, Object>> expList = AliyunLogUtil.getQuotaWithSql("ads-exp", query.toString(), from, to);
        List<Map<String, Object>> clkList = AliyunLogUtil.getQuotaWithSql("ads-clk", query.toString(), from, to);
        QuotaFlowArea quota;
        if (CollectionUtils.isNotEmpty(reqList)) {
            for (Map<String, Object> itemMap : reqList) {
                String index = itemMap.get("areacode").toString();
                if (StringUtils.isNotBlank(index)) {
                    if ( multiQuotaMap.get(index) != null ) {
                        quota = multiQuotaMap.get(index);
                    } else {
                        quota = new QuotaFlowArea();
                        quota.setArea(index);
                    }
                    quota.setCreDay(Integer.parseInt(DateUtils.format(new Date(), DateUtils.SHORT_FORMAT)));
                    quota.setReq(NvwaUtils.obj2long(itemMap.get("num"),0l));
                    quota.setRequv(NvwaUtils.obj2int(itemMap.get("uv")));
                    multiQuotaMap.put(index, quota);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(expList)) {
            for (Map<String, Object> itemMap : expList) {
                String index = itemMap.get("areacode").toString();
                if (StringUtils.isNotBlank(index)) {
                    if ( multiQuotaMap.get(index) != null ) {
                        quota = multiQuotaMap.get(index);
                    } else {
                        quota = new QuotaFlowArea();
                        quota.setArea(index);
                    }
                    quota.setCreDay(Integer.parseInt(DateUtils.format(new Date(), DateUtils.SHORT_FORMAT)));
                    quota.setInvest(NvwaUtils.obj2long(itemMap.get("cost"),0l));
                    quota.setExp(NvwaUtils.obj2int(itemMap.get("num")));
                    quota.setExpuv(NvwaUtils.obj2int(itemMap.get("uv")));
                    multiQuotaMap.put(index, quota);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(clkList)) {
            for (Map<String, Object> itemMap : clkList) {
                String index = itemMap.get("areacode").toString();
                if (StringUtils.isNotBlank(index)) {
                    if ( multiQuotaMap.get(index) != null ) {
                        quota = multiQuotaMap.get(index);
                    } else {
                        quota = new QuotaFlowArea();
                        quota.setArea(index);
                    }
                    quota.setCreDay(Integer.parseInt(DateUtils.format(new Date(), DateUtils.SHORT_FORMAT)));
                    quota.setClk(NvwaUtils.obj2int(itemMap.get("num")));
                    quota.setClkuv(NvwaUtils.obj2int(itemMap.get("uv")));
                    multiQuotaMap.put(index, quota);
                }
            }
        }
        return multiQuotaMap;
    }
}
