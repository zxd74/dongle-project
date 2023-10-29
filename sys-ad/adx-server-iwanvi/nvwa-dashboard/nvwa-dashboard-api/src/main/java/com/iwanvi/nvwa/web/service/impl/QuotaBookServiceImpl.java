package com.iwanvi.nvwa.web.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.BookCategoryMapper;
import com.iwanvi.nvwa.dao.QuotaBookMapper;
import com.iwanvi.nvwa.dao.model.BookCategory;
import com.iwanvi.nvwa.dao.model.BookCategoryExample;
import com.iwanvi.nvwa.dao.model.QuotaBook;
import com.iwanvi.nvwa.web.service.BookCategoryService;
import com.iwanvi.nvwa.web.service.QuotaBookService;
import com.iwanvi.nvwa.web.util.WebConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class QuotaBookServiceImpl implements QuotaBookService {

    @Autowired
    private QuotaBookMapper quotaBookMapper;
    @Autowired
    private BookCategoryMapper bookCategoryMapper;
    @Autowired
    private BookCategoryService bookCategoryService;
    @Autowired
    private RedisDao redisDao;

    @Override
    public List<QuotaBook> sumWithDate(Integer sDate, Integer eDate, String level1s, String level2s, String level3s) {
        List<QuotaBook> resultList = Lists.newArrayList();
        if (sDate != null && eDate != null) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            if (StringUtils.isNotBlank(level1s)) {
                List<Integer> level1List = StringUtils.str2List4Int(level1s, Constants.SIGN_COMMA);
                paramMap.put("level1s", level1List);
            }
            if (StringUtils.isNotBlank(level2s)) {
                List<Integer> level2List = StringUtils.str2List4Int(level2s, Constants.SIGN_COMMA);
                paramMap.put("level2s", level2List);
            }
            if (StringUtils.isNotBlank(level3s)) {
                List<String> level3List = StringUtils.str2List(level3s, Constants.SIGN_COMMA);
                paramMap.put("level3s", level3List);
            }
            resultList = quotaBookMapper.sumWithDate(paramMap);
            Map<String, QuotaBook> quotaDayMap = Maps.newHashMap();
            for (QuotaBook report : resultList) {
                quotaDayMap.put(report.getCreDay().toString(), report);
            }
            Date sDay = DateUtils.parse(sDate.toString(), DateUtils.SHORT_FORMAT);
            Date eDay = DateUtils.parse(eDate.toString(), DateUtils.SHORT_FORMAT);
            for (Date startDate = sDay; startDate.compareTo(eDay) <= 0; startDate = DateUtils.getNextDaysDate(startDate,
                    1)) {
                String day = DateUtils.format(startDate, DateUtils.SHORT_FORMAT);
                if (!quotaDayMap.containsKey(day)) {
                    QuotaBook quota = new QuotaBook();
                    quota.setCreDay(Integer.parseInt(day));
                    quotaDayMap.put(day, quota);
                }
            }
            //当天数据从redis里取
            String today = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
            if (eDate >= Integer.parseInt(today)) {
                List<Map<String, Integer>> level3Map = getLevel3List(level1s, level2s, level3s);
                if (CollectionUtils.isNotEmpty(level3Map)) {
                    Long req = 0l;
                    Long requv = 0l;
                    Long exp = 0l;
                    Long expuv = 0l;
                    Long clk = 0l;
                    Long clkuv = 0l;
                    Long cost = 0l;
                    for (Map<String, Integer> level3 : level3Map) {
                        String reqKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_REQ,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String requvKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_REQUV,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String expKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_DISPLAY,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String expuvKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_DISPLAYUV,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String clkKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_CLICK,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String clkuvKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_CLICKUV,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String costKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_COST,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String reqValue = redisDao.get(reqKey);
                        if (StringUtils.isNotBlank(reqValue)) {
                            req += Long.parseLong(reqValue);
                        }
                        String expValue = redisDao.get(expKey);
                        if (StringUtils.isNotBlank(expValue)) {
                            exp += Long.parseLong(expValue);
                        }
                        String clkValue = redisDao.get(clkKey);
                        if (StringUtils.isNotBlank(clkValue)) {
                            clk += Long.parseLong(clkValue);
                        }
                        String costValue = redisDao.get(costKey);
                        if (StringUtils.isNotBlank(costValue)) {
                            cost += Long.parseLong(costValue);
                        }

                        long requvValue = redisDao.pfCont(requvKey);
                        requv += requvValue;
                        long expuvValue = redisDao.pfCont(expuvKey);
                        expuv += expuvValue;
                        long clkuvValue = redisDao.pfCont(clkuvKey);
                        clkuv += clkuvValue;
                    }
                    QuotaBook quota = quotaDayMap.get(today);
                    quota.setReq(quota.getReq() + req.intValue());
                    quota.setRequv(quota.getRequv() + requv.intValue());
                    quota.setExp(quota.getExp() + exp.intValue());
                    quota.setExpuv(quota.getExpuv() + expuv.intValue());
                    quota.setClk(quota.getClk() + clk.intValue());
                    quota.setClkuv(quota.getClkuv() + clkuv.intValue());
                    quota.setInvest(quota.getInvest() + cost);
                }
            }
            resultList = Lists.newArrayList(quotaDayMap.values());
            resultList.sort((o1, o2) -> o1.getCreDay() < o2.getCreDay() ? -1 : 1);
        }
        return resultList;
    }

    @Override
    public List<QuotaBook> sumWithGroup(Integer sDate, Integer eDate, String level1s, String level2s,
                                        String level3s, String group) {
        List<QuotaBook> resultList = Lists.newArrayList();
        if (sDate != null && eDate != null && StringUtils.isNotBlank(group)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            paramMap.put("groupItem", group);
            if (StringUtils.isNotBlank(level1s)) {
                List<Integer> level1List = StringUtils.str2List4Int(level1s, Constants.SIGN_COMMA);
                paramMap.put("level1s", level1List);
            }
            if (StringUtils.isNotBlank(level2s)) {
                List<Integer> level2List = StringUtils.str2List4Int(level2s, Constants.SIGN_COMMA);
                paramMap.put("level2s", level2List);
            }
            if (StringUtils.isNotBlank(level3s)) {
                List<String> level3List = StringUtils.str2List(level3s, Constants.SIGN_COMMA);
                paramMap.put("level3s", level3List);
            }
            resultList = quotaBookMapper.sumWithGroup(paramMap);
            List<Integer> itemList = resultList.stream().map(QuotaBook::getItemId).map(Integer::parseInt)
                    .collect(Collectors.toList());
            if ("level1".equals(group)) {
                List<Integer> list = Lists.newArrayList();
                if (paramMap.get("level1s") != null) {
                    list = (List<Integer>) paramMap.get("level1s");
                } else {
                    List<BookCategory> levelList = bookCategoryService.selectOneLevel();
                    list = Lists.transform(levelList, BookCategory::getId);
                }
                for (Integer item : list) {
                    if (!itemList.contains(item)) {
                        QuotaBook quota = new QuotaBook();
                        quota.setItemId(item.toString());
                        resultList.add(quota);
                    }
                }
            } else if ("level2".equals(group)) {
                List<Integer> list = Lists.newArrayList();
                if (paramMap.get("level2s") != null) {
                    list = (List<Integer>) paramMap.get("level2s");
                } else {
                    List<BookCategory> levelList = bookCategoryService.selectTwoLevel();
                    list = Lists.transform(levelList, BookCategory::getId);
                }
                for (Integer item : list) {
                    if (!itemList.contains(item)) {
                        QuotaBook quota = new QuotaBook();
                        quota.setItemId(item.toString());
                        resultList.add(quota);
                    }
                }
            } else if ("level3".equals(group)) {
                List<Integer> list = Lists.newArrayList();
                if (paramMap.get("level3s") != null) {
                    list = (List<Integer>) paramMap.get("level3s");
                } else {
                    List<BookCategory> levelList = bookCategoryService.selectThreeLevel();
                    list = Lists.transform(levelList, BookCategory::getId);
                }
                for (Integer item : list) {
                    if (!itemList.contains(item)) {
                        QuotaBook quota = new QuotaBook();
                        quota.setItemId(item.toString());
                        resultList.add(quota);
                    }
                }
            }
            for (QuotaBook report : resultList) {
                if (report.getLevel1() != null) {
                    BookCategory book = bookCategoryMapper.selectByPrimaryKey(report.getLevel1());
                    if (book != null) {
                        report.setLevel1Name(book.getName());
                    }
                }
                if (report.getItemId() != null) {
                    BookCategory bookCategory = bookCategoryMapper.selectByPrimaryKey(Integer.parseInt(report.getItemId()));
                    if (bookCategory != null) {
                        if ("level1".equals(group)) {
                            report.setLevel1Name(bookCategory.getName());
                        } else if ("level2".equals(group)) {
                            report.setLevel2Name(bookCategory.getName());
                        } else if ("level3".equals(group)) {
                            report.setLevel3Name(bookCategory.getName());
                        }
                    }
                }
            }
            //当天数据从redis里取
            String today = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
            if (eDate >= Integer.parseInt(today)) {
                List<Map<String, Integer>> level3Map = getLevel3List(level1s, level2s, level3s);
                if (CollectionUtils.isNotEmpty(level3Map)) {
                    Map<String,Map<String, Long>> valueMap = Maps.newHashMap();
                    for (Map<String, Integer> level3 : level3Map) {
                        Long req = 0l;
                        Long requv = 0l;
                        Long exp = 0l;
                        Long expuv = 0l;
                        Long clk = 0l;
                        Long clkuv = 0l;
                        Long cost = 0l;
                        String reqKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_REQ,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String requvKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_REQUV,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String expKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_DISPLAY,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String expuvKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_DISPLAYUV,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String clkKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_CLICK,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String clkuvKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_CLICKUV,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String costKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_COST,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String reqValue = redisDao.get(reqKey);
                        if (StringUtils.isNotBlank(reqValue)) {
                            req = Long.parseLong(reqValue);
                        }
                        String expValue = redisDao.get(expKey);
                        if (StringUtils.isNotBlank(expValue)) {
                            exp = Long.parseLong(expValue);
                        }
                        String clkValue = redisDao.get(clkKey);
                        if (StringUtils.isNotBlank(clkValue)) {
                            clk = Long.parseLong(clkValue);
                        }
                        String costValue = redisDao.get(costKey);
                        if (StringUtils.isNotBlank(costValue)) {
                            cost = Long.parseLong(costValue);
                        }

                        long requvValue = redisDao.pfCont(requvKey);
                        requv = requvValue;
                        long expuvValue = redisDao.pfCont(expuvKey);
                        expuv = expuvValue;
                        long clkuvValue = redisDao.pfCont(clkuvKey);
                        clkuv = clkuvValue;

                        Map<String, Long> dayMap = Maps.newHashMap();
                        dayMap.put("exp",exp);
                        dayMap.put("expuv",expuv);
                        dayMap.put("clk",clk);
                        dayMap.put("clkuv",clkuv);
                        dayMap.put("req",req);
                        dayMap.put("requv",requv);
                        dayMap.put("cost",cost);

                        String mapKey = StringUtils.concat("_1_",level3.get("l1"),"_2_", level3.get("l2"),
                                "_3_",level3.get("l3"));
                        valueMap.put(mapKey, dayMap);
                    }
                    if ("level1".equals(group)) {
                        for (QuotaBook quota : resultList) {
                            Map<String, Long> appMap = getMapValue(valueMap, "_1_" + quota.getLevel1());
                            addQuota(quota, appMap);
                        }
                    } else if ("level2".equals(group)) {
                        for (QuotaBook quota : resultList) {
                            Map<String, Long> appMap = getMapValue(valueMap, "_2_" + quota.getLevel2());
                            addQuota(quota, appMap);
                        }
                    } else if ("level3".equals(group)) {
                        for (QuotaBook quota : resultList) {
                            Map<String, Long> appMap = getMapValue(valueMap, "_3_" + quota.getLevel3());
                            addQuota(quota, appMap);
                        }
                    }
                }
            }
        }
        return resultList;
    }

    private void addQuota(QuotaBook quota, Map<String, Long> cacheMap) {
        if (cacheMap.get("req") != null) {
            quota.setReq(quota.getReq() + cacheMap.get("req").intValue());
        }
        if (cacheMap.get("requv") != null) {
            quota.setRequv(quota.getRequv() + cacheMap.get("requv").intValue());
        }
        if (cacheMap.get("exp") != null) {
            quota.setExp(quota.getExp() + cacheMap.get("exp").intValue());
        }
        if (cacheMap.get("expuv") != null) {
            quota.setExpuv(quota.getExpuv() + cacheMap.get("expuv").intValue());
        }
        if (cacheMap.get("clk") != null) {
            quota.setClk(quota.getClk() + cacheMap.get("clk").intValue());
        }
        if (cacheMap.get("clkuv") != null) {
            quota.setClkuv(quota.getClkuv() + cacheMap.get("clkuv").intValue());
        }
        if (cacheMap.get("cost") != null) {
            quota.setInvest(quota.getInvest() + cacheMap.get("cost").intValue());
        }
    }

    private Map<String, Long> getMapValue(Map<String, Map<String, Long>> map, String keyReg) {
        Map<String, Long> resultmap = Maps.newHashMap();
        if (StringUtils.isNotBlank(keyReg) && MapUtils.isNotEmpty(map)) {
            Long req = 0l;
            Long requv = 0l;
            Long exp = 0l;
            Long expuv = 0l;
            Long clk = 0l;
            Long clkuv = 0l;
            Long cost = 0l;
            for (String key : map.keySet()) {
                if (key.contains(keyReg)) {
                    req += map.get(key).get("req");
                    requv += map.get(key).get("requv");
                    exp += map.get(key).get("exp");
                    expuv += map.get(key).get("expuv");
                    clk += map.get(key).get("clk");
                    clkuv += map.get(key).get("clkuv");
                    cost += map.get(key).get("cost");
                }
            }
            resultmap.put("exp",exp);
            resultmap.put("expuv",expuv);
            resultmap.put("clk",clk);
            resultmap.put("clkuv",clkuv);
            resultmap.put("req",req);
            resultmap.put("requv",requv);
            resultmap.put("cost",cost);
        }
        return resultmap;
    }

    private List<Map<String, Integer>> getLevel3List(String l1s, String l2s, String l3s) {
        List<Integer> level1IdList = Lists.newArrayList();
        List<Integer> level2IdList = Lists.newArrayList();
        List<BookCategory> level3List = Lists.newArrayList();
        List<Map<String, Integer>> levelMap = Lists.newArrayList();
        if (StringUtils.isNotBlank(l1s)) {
            level1IdList = StringUtils.str2List4Int(l1s, Constants.SIGN_COMMA);
        } else {
            BookCategoryExample example = new BookCategoryExample();
            example.createCriteria().andParentIdIsNull();
            List<BookCategory> level1List = bookCategoryMapper.selectByExample(example);
            level1IdList = Lists.transform(level1List, BookCategory::getId);
        }
        if (CollectionUtils.isEmpty(level1IdList)) {
            throw new ServiceException("没有图书分类数据");
        }
        if (StringUtils.isNotBlank(l2s)) {
            List<Integer> l2Ids = StringUtils.str2List4Int(l2s, Constants.SIGN_COMMA);
            BookCategoryExample example = new BookCategoryExample();
            example.createCriteria().andParentIdIn(level1IdList).andIdIn(l2Ids);
            List<BookCategory> level2List = bookCategoryMapper.selectByExample(example);
            level2IdList = Lists.transform(level2List, BookCategory::getId);
        } else {
            BookCategoryExample example = new BookCategoryExample();
            example.createCriteria().andParentIdIn(level1IdList);
            List<BookCategory> level2List = bookCategoryMapper.selectByExample(example);
            level2IdList = Lists.transform(level2List, BookCategory::getId);
        }
        if (CollectionUtils.isEmpty(level2IdList)) {
            return null;
        }
        if (StringUtils.isNotBlank(l3s)) {
            List<Integer> l3Ids = StringUtils.str2List4Int(l3s, Constants.SIGN_COMMA);
            BookCategoryExample example = new BookCategoryExample();
            example.createCriteria().andParentIdIn(level2IdList).andIdIn(l3Ids);
            level3List = bookCategoryMapper.selectByExample(example);
        } else {
            BookCategoryExample example = new BookCategoryExample();
            example.createCriteria().andParentIdIn(level2IdList);
            level3List = bookCategoryMapper.selectByExample(example);
        }
        if (CollectionUtils.isNotEmpty(level3List)) {
            for (BookCategory level : level3List) {
                BookCategory bookCategory = bookCategoryMapper.selectByPrimaryKey(level.getParentId());
                Map<String, Integer> level3Map = Maps.newHashMap();
                level3Map.put("l1", bookCategory.getId());
                level3Map.put("l2", level.getParentId());
                level3Map.put("l3", level.getId());
                levelMap.add(level3Map);
            }
        }
        return levelMap;
    }

    @Override
    public List<QuotaBook> detailReport(Integer id, Integer sDate, Integer eDate, String level1s, String level2s,
                                        String level3s, String group) {
        List<QuotaBook> resultList = Lists.newArrayList();
        if (sDate != null && eDate != null) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            paramMap.put("groupItem", group);
            if ("level1".equals(group)) {
                level1s = id.toString();
            } else if ("level2".equals(group)) {
                level2s = id.toString();
            } else if ("level3".equals(group)) {
                level3s = id.toString();
            }
            if (StringUtils.isNotBlank(level1s)) {
                List<Integer> level1List = StringUtils.str2List4Int(level1s, Constants.SIGN_COMMA);
                paramMap.put("level1s", level1List);
            }
            if (StringUtils.isNotBlank(level2s)) {
                List<Integer> level2List = StringUtils.str2List4Int(level2s, Constants.SIGN_COMMA);
                paramMap.put("level2s", level2List);
            }
            if (StringUtils.isNotBlank(level3s)) {
                List<String> level3List = StringUtils.str2List(level3s, Constants.SIGN_COMMA);
                paramMap.put("level3s", level3List);
            }
            resultList = quotaBookMapper.detailReport(paramMap);
            Map<String, QuotaBook> quotaDayMap = Maps.newHashMap();
            for (QuotaBook report : resultList) {
                quotaDayMap.put(report.getCreDay().toString(), report);
            }
            Date sDay = DateUtils.parse(sDate.toString(), DateUtils.SHORT_FORMAT);
            Date eDay = DateUtils.parse(eDate.toString(), DateUtils.SHORT_FORMAT);
            for (Date startDate = sDay; startDate.compareTo(eDay) <= 0; startDate = DateUtils.getNextDaysDate(startDate,
                    1)) {
                String day = DateUtils.format(startDate, DateUtils.SHORT_FORMAT);
                if (!quotaDayMap.containsKey(day)) {
                    QuotaBook quota = new QuotaBook();
                    quota.setCreDay(Integer.parseInt(day));
                    quotaDayMap.put(day, quota);
                }
            }
            //当天数据从redis里取
            String today = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
            if (eDate >= Integer.parseInt(today)) {
                List<Map<String, Integer>> level3Map = getLevel3List(level1s, level2s, level3s);
                if (CollectionUtils.isNotEmpty(level3Map)) {
                    Long req = 0l;
                    Long requv = 0l;
                    Long exp = 0l;
                    Long expuv = 0l;
                    Long clk = 0l;
                    Long clkuv = 0l;
                    Long cost = 0l;
                    Map<String,Map<String, Long>> valueMap = Maps.newHashMap();
                    for (Map<String, Integer> level3 : level3Map) {
                        String reqKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_REQ,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String requvKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_REQUV,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String expKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_DISPLAY,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String expuvKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_DISPLAYUV,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String clkKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_CLICK,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String clkuvKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_CLICKUV,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String costKey = StringUtils.buildString(WebConstants.QUOTA_CATALOG_DAY_KEY, Constants.QUOTA_COST,
                                level3.get("l1").toString(), level3.get("l2").toString(), level3.get("l3").toString(),
                                today);
                        String reqValue = redisDao.get(reqKey);
                        if (StringUtils.isNotBlank(reqValue)) {
                            req = Long.parseLong(reqValue);
                        }
                        String expValue = redisDao.get(expKey);
                        if (StringUtils.isNotBlank(expValue)) {
                            exp = Long.parseLong(expValue);
                        }
                        String clkValue = redisDao.get(clkKey);
                        if (StringUtils.isNotBlank(clkValue)) {
                            clk = Long.parseLong(clkValue);
                        }
                        String costValue = redisDao.get(costKey);
                        if (StringUtils.isNotBlank(costValue)) {
                            cost = Long.parseLong(costValue);
                        }

                        long requvValue = redisDao.pfCont(requvKey);
                        requv = requvValue;
                        long expuvValue = redisDao.pfCont(expuvKey);
                        expuv = expuvValue;
                        long clkuvValue = redisDao.pfCont(clkuvKey);
                        clkuv = clkuvValue;

                        Map<String, Long> dayMap = Maps.newHashMap();
                        dayMap.put("exp",exp);
                        dayMap.put("expuv",expuv);
                        dayMap.put("clk",clk);
                        dayMap.put("clkuv",clkuv);
                        dayMap.put("req",req);
                        dayMap.put("requv",requv);
                        dayMap.put("cost",cost);

                        String mapKey = StringUtils.concat("_1_",level3.get("l1"),"_2_", level3.get("l2"),
                                "_3_",level3.get("l3"));
                        valueMap.put(mapKey, dayMap);
                    }
                    QuotaBook quota = quotaDayMap.get(today);
                    String preffix = null;
                    if ("level1".equals(group)) {
                        preffix = "_1_" + id;
                    }
                    if ("level2".equals(group)) {
                        preffix = "_2_" + id;
                    }
                    if ("level3".equals(group)) {
                        preffix = "_3_" + id;
                    }
                    Map<String, Long> appMap = getMapValue(valueMap, preffix);
                    addQuota(quota, appMap);
                }
            }
            resultList = Lists.newArrayList(quotaDayMap.values());
            for (QuotaBook report : resultList) {
                BookCategory book = bookCategoryMapper.selectByPrimaryKey(id);
                if (book != null) {
                    report.setItemName(book.getName());
                }
            }
            resultList.sort((o1, o2) -> o1.getCreDay() < o2.getCreDay() ? -1 : 1);
        }
        return resultList;
    }
}
