package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class EntityDsp {
    private Integer id;

    private String name;

    private Integer consumerId;

    private Integer advertiserId;

    private String dspAdvertiserId;

    private String dspCreativeId;

    private Integer positionId;

    private Integer industry;

    private String landpage;

    private String expireDate;

    private Integer status;

    private Integer examinesStatus;

    private Integer examinesUser;

    private Date examinesTime;

    private String examinesRemarks;

    private Integer entitytype;

    private String threadTitle;

    private String threadContent;

    private String userPortrait;

    private String entityurl;

    private String threadPic1;

    private String threadPic2;

    private String threadPic3;

    private String threadPic4;

    private String threadPic5;

    private String threadPic6;

    private String threadPic7;

    private String threadPic8;

    private String threadPic9;

    private String packageName;

    private Integer appId;

    private Date createTime;

    private Date updateTime;

    private String taskId;

    private Integer cp;

    private Integer ps;

    public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	private String ctatext;
    
    public String getAdvertiserName() {
		return advertiserName;
	}

	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}

	private String consumerName;
    private String advertiserName;
    private String dspId;

    public Integer getId() {
        return id;
    }

    public String getDspId() {
		return dspId;
	}

	public void setDspId(String dspId) {
		this.dspId = dspId;
	}

	public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Integer consumerId) {
        this.consumerId = consumerId;
    }

    public Integer getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(Integer advertiserId) {
        this.advertiserId = advertiserId;
    }

    public String getDspAdvertiserId() {
        return dspAdvertiserId;
    }

    public void setDspAdvertiserId(String dspAdvertiserId) {
        this.dspAdvertiserId = dspAdvertiserId;
    }

    public String getDspCreativeId() {
        return dspCreativeId;
    }

    public void setDspCreativeId(String dspCreativeId) {
        this.dspCreativeId = dspCreativeId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getIndustry() {
        return industry;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    public String getLandpage() {
        return landpage;
    }

    public void setLandpage(String landpage) {
        this.landpage = landpage;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getExaminesStatus() {
        return examinesStatus;
    }

    public void setExaminesStatus(Integer examinesStatus) {
        this.examinesStatus = examinesStatus;
    }

    public Integer getExaminesUser() {
        return examinesUser;
    }

    public void setExaminesUser(Integer examinesUser) {
        this.examinesUser = examinesUser;
    }

    public Date getExaminesTime() {
        return examinesTime;
    }

    public void setExaminesTime(Date examinesTime) {
        this.examinesTime = examinesTime;
    }

    public String getExaminesRemarks() {
        return examinesRemarks;
    }

    public void setExaminesRemarks(String examinesRemarks) {
        this.examinesRemarks = examinesRemarks;
    }

    public Integer getEntitytype() {
        return entitytype;
    }

    public void setEntitytype(Integer entitytype) {
        this.entitytype = entitytype;
    }

    public String getThreadTitle() {
        return threadTitle;
    }

    public void setThreadTitle(String threadTitle) {
        this.threadTitle = threadTitle;
    }

    public String getThreadContent() {
        return threadContent;
    }

    public void setThreadContent(String threadContent) {
        this.threadContent = threadContent;
    }

    public String getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait;
    }

    public String getEntityurl() {
        return entityurl;
    }

    public void setEntityurl(String entityurl) {
        this.entityurl = entityurl;
    }

    public String getThreadPic1() {
        return threadPic1;
    }

    public void setThreadPic1(String threadPic1) {
        this.threadPic1 = threadPic1;
    }

    public String getThreadPic2() {
        return threadPic2;
    }

    public void setThreadPic2(String threadPic2) {
        this.threadPic2 = threadPic2;
    }

    public String getThreadPic3() {
        return threadPic3;
    }

    public void setThreadPic3(String threadPic3) {
        this.threadPic3 = threadPic3;
    }

    public String getThreadPic4() {
        return threadPic4;
    }

    public void setThreadPic4(String threadPic4) {
        this.threadPic4 = threadPic4;
    }

    public String getThreadPic5() {
        return threadPic5;
    }

    public void setThreadPic5(String threadPic5) {
        this.threadPic5 = threadPic5;
    }

    public String getThreadPic6() {
        return threadPic6;
    }

    public void setThreadPic6(String threadPic6) {
        this.threadPic6 = threadPic6;
    }

    public String getThreadPic7() {
        return threadPic7;
    }

    public void setThreadPic7(String threadPic7) {
        this.threadPic7 = threadPic7;
    }

    public String getThreadPic8() {
        return threadPic8;
    }

    public void setThreadPic8(String threadPic8) {
        this.threadPic8 = threadPic8;
    }

    public String getThreadPic9() {
        return threadPic9;
    }

    public void setThreadPic9(String threadPic9) {
        this.threadPic9 = threadPic9;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCtatext() {
        return ctatext;
    }

    public void setCtatext(String ctatext) {
        this.ctatext = ctatext;
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public Integer getPs() {
        return ps;
    }

    public void setPs(Integer ps) {
        this.ps = ps;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entity_dsp
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static EntityDsp.Builder builder() {
        return new EntityDsp.Builder();
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table entity_dsp
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static class Builder {
        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private EntityDsp obj;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder() {
            this.obj = new EntityDsp();
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.id
         *
         * @param id the value for entity_dsp.id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder id(Integer id) {
            obj.setId(id);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.name
         *
         * @param name the value for entity_dsp.name
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder name(String name) {
            obj.setName(name);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.consumer_id
         *
         * @param consumerId the value for entity_dsp.consumer_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder consumerId(Integer consumerId) {
            obj.setConsumerId(consumerId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.advertiser_id
         *
         * @param advertiserId the value for entity_dsp.advertiser_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder advertiserId(Integer advertiserId) {
            obj.setAdvertiserId(advertiserId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.dsp_advertiser_id
         *
         * @param dspAdvertiserId the value for entity_dsp.dsp_advertiser_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dspAdvertiserId(String dspAdvertiserId) {
            obj.setDspAdvertiserId(dspAdvertiserId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.dsp_creative_id
         *
         * @param dspCreativeId the value for entity_dsp.dsp_creative_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dspCreativeId(String dspCreativeId) {
            obj.setDspCreativeId(dspCreativeId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.position_id
         *
         * @param positionId the value for entity_dsp.position_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder positionId(Integer positionId) {
            obj.setPositionId(positionId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.industry
         *
         * @param industry the value for entity_dsp.industry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder industry(Integer industry) {
            obj.setIndustry(industry);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.landpage
         *
         * @param landpage the value for entity_dsp.landpage
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder landpage(String landpage) {
            obj.setLandpage(landpage);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.expire_date
         *
         * @param expireDate the value for entity_dsp.expire_date
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder expireDate(String expireDate) {
            obj.setExpireDate(expireDate);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.status
         *
         * @param status the value for entity_dsp.status
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder status(Integer status) {
            obj.setStatus(status);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.examines_status
         *
         * @param examinesStatus the value for entity_dsp.examines_status
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder examinesStatus(Integer examinesStatus) {
            obj.setExaminesStatus(examinesStatus);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.examines_user
         *
         * @param examinesUser the value for entity_dsp.examines_user
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder examinesUser(Integer examinesUser) {
            obj.setExaminesUser(examinesUser);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.examines_time
         *
         * @param examinesTime the value for entity_dsp.examines_time
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder examinesTime(Date examinesTime) {
            obj.setExaminesTime(examinesTime);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.examines_remarks
         *
         * @param examinesRemarks the value for entity_dsp.examines_remarks
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder examinesRemarks(String examinesRemarks) {
            obj.setExaminesRemarks(examinesRemarks);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.entityType
         *
         * @param entitytype the value for entity_dsp.entityType
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder entitytype(Integer entitytype) {
            obj.setEntitytype(entitytype);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.thread_title
         *
         * @param threadTitle the value for entity_dsp.thread_title
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder threadTitle(String threadTitle) {
            obj.setThreadTitle(threadTitle);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.thread_content
         *
         * @param threadContent the value for entity_dsp.thread_content
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder threadContent(String threadContent) {
            obj.setThreadContent(threadContent);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.user_portrait
         *
         * @param userPortrait the value for entity_dsp.user_portrait
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder userPortrait(String userPortrait) {
            obj.setUserPortrait(userPortrait);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.entityUrl
         *
         * @param entityurl the value for entity_dsp.entityUrl
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder entityurl(String entityurl) {
            obj.setEntityurl(entityurl);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.thread_pic1
         *
         * @param threadPic1 the value for entity_dsp.thread_pic1
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder threadPic1(String threadPic1) {
            obj.setThreadPic1(threadPic1);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.thread_pic2
         *
         * @param threadPic2 the value for entity_dsp.thread_pic2
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder threadPic2(String threadPic2) {
            obj.setThreadPic2(threadPic2);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.thread_pic3
         *
         * @param threadPic3 the value for entity_dsp.thread_pic3
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder threadPic3(String threadPic3) {
            obj.setThreadPic3(threadPic3);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.thread_pic4
         *
         * @param threadPic4 the value for entity_dsp.thread_pic4
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder threadPic4(String threadPic4) {
            obj.setThreadPic4(threadPic4);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.thread_pic5
         *
         * @param threadPic5 the value for entity_dsp.thread_pic5
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder threadPic5(String threadPic5) {
            obj.setThreadPic5(threadPic5);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.thread_pic6
         *
         * @param threadPic6 the value for entity_dsp.thread_pic6
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder threadPic6(String threadPic6) {
            obj.setThreadPic6(threadPic6);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.thread_pic7
         *
         * @param threadPic7 the value for entity_dsp.thread_pic7
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder threadPic7(String threadPic7) {
            obj.setThreadPic7(threadPic7);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.thread_pic8
         *
         * @param threadPic8 the value for entity_dsp.thread_pic8
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder threadPic8(String threadPic8) {
            obj.setThreadPic8(threadPic8);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.thread_pic9
         *
         * @param threadPic9 the value for entity_dsp.thread_pic9
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder threadPic9(String threadPic9) {
            obj.setThreadPic9(threadPic9);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.package_name
         *
         * @param packageName the value for entity_dsp.package_name
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder packageName(String packageName) {
            obj.setPackageName(packageName);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.app_id
         *
         * @param appId the value for entity_dsp.app_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder appId(Integer appId) {
            obj.setAppId(appId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.create_time
         *
         * @param createTime the value for entity_dsp.create_time
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder createTime(Date createTime) {
            obj.setCreateTime(createTime);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.update_time
         *
         * @param updateTime the value for entity_dsp.update_time
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder updateTime(Date updateTime) {
            obj.setUpdateTime(updateTime);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.task_id
         *
         * @param taskId the value for entity_dsp.task_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder taskId(String taskId) {
            obj.setTaskId(taskId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity_dsp.ctatext
         *
         * @param ctatext the value for entity_dsp.ctatext
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder ctatext(String ctatext) {
            obj.setCtatext(ctatext);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public EntityDsp build() {
            return this.obj;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table entity_dsp
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        name("name", "name", "VARCHAR", false),
        consumerId("consumer_id", "consumerId", "INTEGER", false),
        advertiserId("advertiser_id", "advertiserId", "INTEGER", false),
        dspAdvertiserId("dsp_advertiser_id", "dspAdvertiserId", "VARCHAR", false),
        dspCreativeId("dsp_creative_id", "dspCreativeId", "VARCHAR", false),
        positionId("position_id", "positionId", "INTEGER", false),
        industry("industry", "industry", "INTEGER", false),
        landpage("landpage", "landpage", "VARCHAR", false),
        expireDate("expire_date", "expireDate", "VARCHAR", false),
        status("status", "status", "INTEGER", false),
        examinesStatus("examines_status", "examinesStatus", "INTEGER", false),
        examinesUser("examines_user", "examinesUser", "INTEGER", false),
        examinesTime("examines_time", "examinesTime", "TIMESTAMP", false),
        examinesRemarks("examines_remarks", "examinesRemarks", "VARCHAR", false),
        entitytype("entityType", "entitytype", "INTEGER", false),
        threadTitle("thread_title", "threadTitle", "VARCHAR", false),
        threadContent("thread_content", "threadContent", "VARCHAR", false),
        userPortrait("user_portrait", "userPortrait", "VARCHAR", false),
        entityurl("entityUrl", "entityurl", "VARCHAR", false),
        threadPic1("thread_pic1", "threadPic1", "VARCHAR", false),
        threadPic2("thread_pic2", "threadPic2", "VARCHAR", false),
        threadPic3("thread_pic3", "threadPic3", "VARCHAR", false),
        threadPic4("thread_pic4", "threadPic4", "VARCHAR", false),
        threadPic5("thread_pic5", "threadPic5", "VARCHAR", false),
        threadPic6("thread_pic6", "threadPic6", "VARCHAR", false),
        threadPic7("thread_pic7", "threadPic7", "VARCHAR", false),
        threadPic8("thread_pic8", "threadPic8", "VARCHAR", false),
        threadPic9("thread_pic9", "threadPic9", "VARCHAR", false),
        packageName("package_name", "packageName", "VARCHAR", false),
        appId("app_id", "appId", "INTEGER", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        taskId("task_id", "taskId", "VARCHAR", false),
        ctatext("ctatext", "ctatext", "VARCHAR", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity_dsp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }
    }
}