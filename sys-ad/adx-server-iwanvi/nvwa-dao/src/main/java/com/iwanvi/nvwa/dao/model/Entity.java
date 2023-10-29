package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Entity {
    private Integer id;

    private String entityName;

    private Integer putAllId;

    private Integer pid;

    private Integer adPosition;

    private Integer templateId;

    private String entityTitle;

    private Integer adId;

    private String entityDesc;

    private String entityUrl;

    private Integer runState;

    private Integer entityState;

    private Integer auditUser;

    private String industry;

    private String auditComments;

    private Date auditTime;

    private Integer createUser;

    private Date createTime;

    private Date updateTime;

    private String userName;

    private String threadTitle;

    private String threadPic1;

    private String threadPic2;

    private String threadPic3;

    private String dynamicCode;

    private String threadContent;

    private String threadUrlText;

    private String recommendName;

    private String recommendLink;

    private String customCss;

    private String userPortrait;

    private String popWindowText;

    private String buttonText;

    private String labelTitle;

    private String labelVisible;

    private String goodsStyle;

    private String extId;

    private String extIds;

    private Integer entityType;

    private Integer datarate;

    private String threadPic4;

    private String threadPic5;

    private String threadPic6;

    private String threadPic7;

    private String threadPic8;

    private String threadPic9;

    private String land1;

    private String land2;

    private String land3;

    private String land4;

    private String land5;

    private String land6;

    private String land7;

    private String land8;

    private String land9;

    private String taskId;
    
    private String timeInterval;

	private Date beginTime;

	private Date endTime;

	private String putName;

	private String orderName;

	private String posName;

	private String adverName;
	
	private String appName;

	private AdxRelation adxRelation;

	private List<TemplateModuleRelation> moduleRelations;

	private Integer orderId;

	private Integer auditState;

	private String industryName;
	
	private String landUrl;
	
	private Integer putType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Integer getPutAllId() {
        return putAllId;
    }

    public void setPutAllId(Integer putAllId) {
        this.putAllId = putAllId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getAdPosition() {
        return adPosition;
    }

    public void setAdPosition(Integer adPosition) {
        this.adPosition = adPosition;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getEntityTitle() {
        return entityTitle;
    }

    public void setEntityTitle(String entityTitle) {
        this.entityTitle = entityTitle;
    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public String getEntityDesc() {
        return entityDesc;
    }

    public void setEntityDesc(String entityDesc) {
        this.entityDesc = entityDesc;
    }

    public String getEntityUrl() {
        return entityUrl;
    }

    public void setEntityUrl(String entityUrl) {
        this.entityUrl = entityUrl;
    }

    public Integer getRunState() {
        return runState;
    }

    public void setRunState(Integer runState) {
        this.runState = runState;
    }

    public Integer getEntityState() {
        return entityState;
    }

    public void setEntityState(Integer entityState) {
        this.entityState = entityState;
    }

    public Integer getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(Integer auditUser) {
        this.auditUser = auditUser;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAuditComments() {
        return auditComments;
    }

    public void setAuditComments(String auditComments) {
        this.auditComments = auditComments;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getThreadTitle() {
        return threadTitle;
    }

    public void setThreadTitle(String threadTitle) {
        this.threadTitle = threadTitle;
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

    public String getDynamicCode() {
        return dynamicCode;
    }

    public void setDynamicCode(String dynamicCode) {
        this.dynamicCode = dynamicCode;
    }

    public String getThreadContent() {
        return threadContent;
    }

    public void setThreadContent(String threadContent) {
        this.threadContent = threadContent;
    }

    public String getThreadUrlText() {
        return threadUrlText;
    }

    public void setThreadUrlText(String threadUrlText) {
        this.threadUrlText = threadUrlText;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public String getRecommendLink() {
        return recommendLink;
    }

    public void setRecommendLink(String recommendLink) {
        this.recommendLink = recommendLink;
    }

    public String getCustomCss() {
        return customCss;
    }

    public void setCustomCss(String customCss) {
        this.customCss = customCss;
    }

    public String getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait;
    }

    public String getPopWindowText() {
        return popWindowText;
    }

    public void setPopWindowText(String popWindowText) {
        this.popWindowText = popWindowText;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    public String getLabelVisible() {
        return labelVisible;
    }

    public void setLabelVisible(String labelVisible) {
        this.labelVisible = labelVisible;
    }

    public String getGoodsStyle() {
        return goodsStyle;
    }

    public void setGoodsStyle(String goodsStyle) {
        this.goodsStyle = goodsStyle;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getExtIds() {
        return extIds;
    }

    public void setExtIds(String extIds) {
        this.extIds = extIds;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public Integer getDatarate() {
        return datarate;
    }

    public void setDatarate(Integer datarate) {
        this.datarate = datarate;
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

    public String getLand1() {
        return land1;
    }

    public void setLand1(String land1) {
        this.land1 = land1;
    }

    public String getLand2() {
        return land2;
    }

    public void setLand2(String land2) {
        this.land2 = land2;
    }

    public String getLand3() {
        return land3;
    }

    public void setLand3(String land3) {
        this.land3 = land3;
    }

    public String getLand4() {
        return land4;
    }

    public void setLand4(String land4) {
        this.land4 = land4;
    }

    public String getLand5() {
        return land5;
    }

    public void setLand5(String land5) {
        this.land5 = land5;
    }

    public String getLand6() {
        return land6;
    }

    public void setLand6(String land6) {
        this.land6 = land6;
    }

    public String getLand7() {
        return land7;
    }

    public void setLand7(String land7) {
        this.land7 = land7;
    }

    public String getLand8() {
        return land8;
    }

    public void setLand8(String land8) {
        this.land8 = land8;
    }

    public String getLand9() {
        return land9;
    }

    public void setLand9(String land9) {
        this.land9 = land9;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPutName() {
		return putName;
	}

	public void setPutName(String putName) {
		this.putName = putName;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getAdverName() {
		return adverName;
	}

	public void setAdverName(String adverName) {
		this.adverName = adverName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public AdxRelation getAdxRelation() {
		return adxRelation;
	}

	public void setAdxRelation(AdxRelation adxRelation) {
		this.adxRelation = adxRelation;
	}

	public List<TemplateModuleRelation> getModuleRelations() {
		return moduleRelations;
	}

	public void setModuleRelations(List<TemplateModuleRelation> moduleRelations) {
		this.moduleRelations = moduleRelations;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getLandUrl() {
		return landUrl;
	}

	public void setLandUrl(String landUrl) {
		this.landUrl = landUrl;
	}

	public Integer getPutType() {
		return putType;
	}

	public void setPutType(Integer putType) {
		this.putType = putType;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entity
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static Entity.Builder builder() {
        return new Entity.Builder();
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table entity
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static class Builder {
        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table entity
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private Entity obj;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder() {
            this.obj = new Entity();
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.id
         *
         * @param id the value for entity.id
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
         * This method sets the value of the database column entity.entity_name
         *
         * @param entityName the value for entity.entity_name
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder entityName(String entityName) {
            obj.setEntityName(entityName);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.put_all_id
         *
         * @param putAllId the value for entity.put_all_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder putAllId(Integer putAllId) {
            obj.setPutAllId(putAllId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.pid
         *
         * @param pid the value for entity.pid
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder pid(Integer pid) {
            obj.setPid(pid);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.ad_position
         *
         * @param adPosition the value for entity.ad_position
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder adPosition(Integer adPosition) {
            obj.setAdPosition(adPosition);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.template_id
         *
         * @param templateId the value for entity.template_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder templateId(Integer templateId) {
            obj.setTemplateId(templateId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.entity_title
         *
         * @param entityTitle the value for entity.entity_title
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder entityTitle(String entityTitle) {
            obj.setEntityTitle(entityTitle);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.ad_id
         *
         * @param adId the value for entity.ad_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder adId(Integer adId) {
            obj.setAdId(adId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.entity_desc
         *
         * @param entityDesc the value for entity.entity_desc
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder entityDesc(String entityDesc) {
            obj.setEntityDesc(entityDesc);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.entity_url
         *
         * @param entityUrl the value for entity.entity_url
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder entityUrl(String entityUrl) {
            obj.setEntityUrl(entityUrl);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.run_state
         *
         * @param runState the value for entity.run_state
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder runState(Integer runState) {
            obj.setRunState(runState);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.entity_state
         *
         * @param entityState the value for entity.entity_state
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder entityState(Integer entityState) {
            obj.setEntityState(entityState);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.audit_user
         *
         * @param auditUser the value for entity.audit_user
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder auditUser(Integer auditUser) {
            obj.setAuditUser(auditUser);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.industry
         *
         * @param industry the value for entity.industry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder industry(String industry) {
            obj.setIndustry(industry);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.audit_comments
         *
         * @param auditComments the value for entity.audit_comments
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder auditComments(String auditComments) {
            obj.setAuditComments(auditComments);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.audit_time
         *
         * @param auditTime the value for entity.audit_time
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder auditTime(Date auditTime) {
            obj.setAuditTime(auditTime);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.create_user
         *
         * @param createUser the value for entity.create_user
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder createUser(Integer createUser) {
            obj.setCreateUser(createUser);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.create_time
         *
         * @param createTime the value for entity.create_time
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
         * This method sets the value of the database column entity.update_time
         *
         * @param updateTime the value for entity.update_time
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
         * This method sets the value of the database column entity.user_name
         *
         * @param userName the value for entity.user_name
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder userName(String userName) {
            obj.setUserName(userName);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.thread_title
         *
         * @param threadTitle the value for entity.thread_title
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
         * This method sets the value of the database column entity.thread_pic1
         *
         * @param threadPic1 the value for entity.thread_pic1
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
         * This method sets the value of the database column entity.thread_pic2
         *
         * @param threadPic2 the value for entity.thread_pic2
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
         * This method sets the value of the database column entity.thread_pic3
         *
         * @param threadPic3 the value for entity.thread_pic3
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
         * This method sets the value of the database column entity.dynamic_code
         *
         * @param dynamicCode the value for entity.dynamic_code
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dynamicCode(String dynamicCode) {
            obj.setDynamicCode(dynamicCode);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.thread_content
         *
         * @param threadContent the value for entity.thread_content
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
         * This method sets the value of the database column entity.thread_url_text
         *
         * @param threadUrlText the value for entity.thread_url_text
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder threadUrlText(String threadUrlText) {
            obj.setThreadUrlText(threadUrlText);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.recommend_name
         *
         * @param recommendName the value for entity.recommend_name
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder recommendName(String recommendName) {
            obj.setRecommendName(recommendName);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.recommend_link
         *
         * @param recommendLink the value for entity.recommend_link
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder recommendLink(String recommendLink) {
            obj.setRecommendLink(recommendLink);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.custom_css
         *
         * @param customCss the value for entity.custom_css
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder customCss(String customCss) {
            obj.setCustomCss(customCss);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.user_portrait
         *
         * @param userPortrait the value for entity.user_portrait
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
         * This method sets the value of the database column entity.pop_window_text
         *
         * @param popWindowText the value for entity.pop_window_text
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder popWindowText(String popWindowText) {
            obj.setPopWindowText(popWindowText);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.button_text
         *
         * @param buttonText the value for entity.button_text
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder buttonText(String buttonText) {
            obj.setButtonText(buttonText);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.label_title
         *
         * @param labelTitle the value for entity.label_title
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder labelTitle(String labelTitle) {
            obj.setLabelTitle(labelTitle);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.label_visible
         *
         * @param labelVisible the value for entity.label_visible
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder labelVisible(String labelVisible) {
            obj.setLabelVisible(labelVisible);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.goods_style
         *
         * @param goodsStyle the value for entity.goods_style
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder goodsStyle(String goodsStyle) {
            obj.setGoodsStyle(goodsStyle);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.ext_id
         *
         * @param extId the value for entity.ext_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder extId(String extId) {
            obj.setExtId(extId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.ext_ids
         *
         * @param extIds the value for entity.ext_ids
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder extIds(String extIds) {
            obj.setExtIds(extIds);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.entity_type
         *
         * @param entityType the value for entity.entity_type
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder entityType(Integer entityType) {
            obj.setEntityType(entityType);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.dataRate
         *
         * @param datarate the value for entity.dataRate
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder datarate(Integer datarate) {
            obj.setDatarate(datarate);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.thread_pic4
         *
         * @param threadPic4 the value for entity.thread_pic4
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
         * This method sets the value of the database column entity.thread_pic5
         *
         * @param threadPic5 the value for entity.thread_pic5
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
         * This method sets the value of the database column entity.thread_pic6
         *
         * @param threadPic6 the value for entity.thread_pic6
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
         * This method sets the value of the database column entity.thread_pic7
         *
         * @param threadPic7 the value for entity.thread_pic7
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
         * This method sets the value of the database column entity.thread_pic8
         *
         * @param threadPic8 the value for entity.thread_pic8
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
         * This method sets the value of the database column entity.thread_pic9
         *
         * @param threadPic9 the value for entity.thread_pic9
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
         * This method sets the value of the database column entity.land1
         *
         * @param land1 the value for entity.land1
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder land1(String land1) {
            obj.setLand1(land1);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.land2
         *
         * @param land2 the value for entity.land2
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder land2(String land2) {
            obj.setLand2(land2);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.land3
         *
         * @param land3 the value for entity.land3
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder land3(String land3) {
            obj.setLand3(land3);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.land4
         *
         * @param land4 the value for entity.land4
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder land4(String land4) {
            obj.setLand4(land4);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.land5
         *
         * @param land5 the value for entity.land5
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder land5(String land5) {
            obj.setLand5(land5);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.land6
         *
         * @param land6 the value for entity.land6
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder land6(String land6) {
            obj.setLand6(land6);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.land7
         *
         * @param land7 the value for entity.land7
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder land7(String land7) {
            obj.setLand7(land7);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.land8
         *
         * @param land8 the value for entity.land8
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder land8(String land8) {
            obj.setLand8(land8);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.land9
         *
         * @param land9 the value for entity.land9
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder land9(String land9) {
            obj.setLand9(land9);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column entity.task_id
         *
         * @param taskId the value for entity.task_id
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
         * This method corresponds to the database table entity
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Entity build() {
            return this.obj;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table entity
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        entityName("entity_name", "entityName", "VARCHAR", false),
        putAllId("put_all_id", "putAllId", "INTEGER", false),
        pid("pid", "pid", "INTEGER", false),
        adPosition("ad_position", "adPosition", "INTEGER", false),
        templateId("template_id", "templateId", "INTEGER", false),
        entityTitle("entity_title", "entityTitle", "VARCHAR", false),
        adId("ad_id", "adId", "INTEGER", false),
        entityDesc("entity_desc", "entityDesc", "VARCHAR", false),
        entityUrl("entity_url", "entityUrl", "VARCHAR", false),
        runState("run_state", "runState", "INTEGER", false),
        entityState("entity_state", "entityState", "INTEGER", false),
        auditUser("audit_user", "auditUser", "INTEGER", false),
        industry("industry", "industry", "VARCHAR", false),
        auditComments("audit_comments", "auditComments", "VARCHAR", false),
        auditTime("audit_time", "auditTime", "TIMESTAMP", false),
        createUser("create_user", "createUser", "INTEGER", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        userName("user_name", "userName", "VARCHAR", false),
        threadTitle("thread_title", "threadTitle", "VARCHAR", false),
        threadPic1("thread_pic1", "threadPic1", "VARCHAR", false),
        threadPic2("thread_pic2", "threadPic2", "VARCHAR", false),
        threadPic3("thread_pic3", "threadPic3", "VARCHAR", false),
        dynamicCode("dynamic_code", "dynamicCode", "VARCHAR", false),
        threadContent("thread_content", "threadContent", "VARCHAR", false),
        threadUrlText("thread_url_text", "threadUrlText", "VARCHAR", false),
        recommendName("recommend_name", "recommendName", "VARCHAR", false),
        recommendLink("recommend_link", "recommendLink", "VARCHAR", false),
        customCss("custom_css", "customCss", "VARCHAR", false),
        userPortrait("user_portrait", "userPortrait", "VARCHAR", false),
        popWindowText("pop_window_text", "popWindowText", "VARCHAR", false),
        buttonText("button_text", "buttonText", "VARCHAR", false),
        labelTitle("label_title", "labelTitle", "VARCHAR", false),
        labelVisible("label_visible", "labelVisible", "VARCHAR", false),
        goodsStyle("goods_style", "goodsStyle", "VARCHAR", false),
        extId("ext_id", "extId", "VARCHAR", false),
        extIds("ext_ids", "extIds", "VARCHAR", false),
        entityType("entity_type", "entityType", "INTEGER", false),
        datarate("dataRate", "datarate", "INTEGER", false),
        threadPic4("thread_pic4", "threadPic4", "VARCHAR", false),
        threadPic5("thread_pic5", "threadPic5", "VARCHAR", false),
        threadPic6("thread_pic6", "threadPic6", "VARCHAR", false),
        threadPic7("thread_pic7", "threadPic7", "VARCHAR", false),
        threadPic8("thread_pic8", "threadPic8", "VARCHAR", false),
        threadPic9("thread_pic9", "threadPic9", "VARCHAR", false),
        land1("land1", "land1", "VARCHAR", false),
        land2("land2", "land2", "VARCHAR", false),
        land3("land3", "land3", "VARCHAR", false),
        land4("land4", "land4", "VARCHAR", false),
        land5("land5", "land5", "VARCHAR", false),
        land6("land6", "land6", "VARCHAR", false),
        land7("land7", "land7", "VARCHAR", false),
        land8("land8", "land8", "VARCHAR", false),
        land9("land9", "land9", "VARCHAR", false),
        taskId("task_id", "taskId", "VARCHAR", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table entity
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table entity
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table entity
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table entity
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table entity
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table entity
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity
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
         * This method corresponds to the database table entity
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table entity
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
         * This method corresponds to the database table entity
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