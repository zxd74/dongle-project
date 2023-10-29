package com.iwanvi.nvwa.web.task;

import com.iwanvi.nvwa.web.service.AdPositionService;
import com.iwanvi.nvwa.web.service.AuditService;
import com.iwanvi.nvwa.web.service.EntityDspService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class ScheduleTask {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    @Autowired
    private EntityDspService entityDspService;
    @Autowired
    private AdPositionService adPositionService;
    @Autowired
    private AuditService auditService;
//    @Autowired
//    private SynchroAiKaService synchroAiKaService;

    @Scheduled(cron = "0 1 0 * * ?")
    public void checkEntityDspExpires() {
        entityDspService.checkEntityOutOfDate();
    }
//    @Scheduled(cron = "0 * * * * ?")
//    public void syncTaskAiKa() {
//        synchroAiKaService.syncTask();
//    }

    @Scheduled(cron = "0 1 0 * * ?")
    public void computeAdpositionForecastExposure() {
        logger.info("compute adPosition forecast exposure start...");
        adPositionService.forecastExposureTask();
        logger.info("compute adPosition forecast exposure complete...");
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void checkVideoStatus() {
        try {
            auditService.checkVideoStatus();
        } catch (UnsupportedEncodingException e) {
            logger.info("check video creative error!",e);
        }
    }
}
