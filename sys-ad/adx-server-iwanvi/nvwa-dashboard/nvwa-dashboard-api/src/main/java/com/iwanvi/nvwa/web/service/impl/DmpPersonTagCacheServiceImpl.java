package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.DmpPersonRuleMapper;
import com.iwanvi.nvwa.web.service.DmpPersonTagCacheService;
import com.iwanvi.nvwa.web.util.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class DmpPersonTagCacheServiceImpl implements DmpPersonTagCacheService {
    private static final Logger logger = LoggerFactory.getLogger(DmpPersonTagCacheServiceImpl.class);

    @Autowired
    private RedisDao redisDao;
    @Autowired
    private DmpPersonRuleMapper dmpPersonRuleMapper;

    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void cacheDmp() {
        List<Map<String, String>> dmpPersonList = dmpPersonRuleMapper.selectAllPersons();
        String pkey;
        String tids;
        for (Map<String, String> map : dmpPersonList) {
            if (!CollectionUtils.isEmpty(map)) {
                pkey = map.get("pkey");
                tids = map.get("tids");
                if (StringUtils.isNotBlank(pkey) && StringUtils.isNotBlank(tids)) {
                    redisDao.set(StringUtils.buildString(WebConstants.DMP_KEY,pkey),tids);
                    logger.info("cache dmp person,{}:{}",pkey,tids);
                }
            }
        }
    }
}
