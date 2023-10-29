package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Apps {
	private Integer id;

	private String appName;

	private String appId;

	private String dowUrl;

	private String siteUrl;

	private String apkUrl;

	private String medias;

	private Integer platform;

	private Integer price;

	private Integer flowNum;

	private Integer type;

	private Integer createUser;

	private Date createTime;

	private Integer updateUser;

	private Date updateTime;

	private Integer auditStatus;

	private String auditOpinion;

	private Integer auditUser;

	private Date auditTime;

	private Double payDiscount;

	private Integer status;

	private String adPosIds;

	private Integer appStatus;

	private String appDownurl;

	private String consumerMapped;

	private String typeName;

	private String platformName;

	private String mediaName;

	private Integer adPosNum;

	private Integer joinType;

	private List<Map<Integer, String>> appMapped;
	
	private List<Map<String, String>> echoMapped;
	
	private List<AdPosition> adpList;
	
	private List<Integer> baseid;	
	
	public List<Integer> getBaseid() {
		return baseid;
	}

	public void setBaseid(List<Integer> baseid) {
		this.baseid = baseid;
	}

	public List<Map<String, String>> getEchoMapped() {
		return echoMapped;
	}

	public void setEchoMapped(List<Map<String, String>> echoMapped) {
		this.echoMapped = echoMapped;
	}

	public List<Map<Integer, String>> getAppMapped() {
		return appMapped;
	}

	public void setAppMapped(List<Map<Integer, String>> appMapped) {
		this.appMapped = appMapped;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public Integer getAdPosNum() {
		return adPosNum;
	}

	public void setAdPosNum(Integer adPosNum) {
		this.adPosNum = adPosNum;
	}

	public Integer getJoinType() {
		return joinType;
	}

	public void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}

	public List<AdPosition> getAdpList() {
		return adpList;
	}

	public void setAdpList(List<AdPosition> adpList) {
		this.adpList = adpList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDowUrl() {
		return dowUrl;
	}

	public void setDowUrl(String dowUrl) {
		this.dowUrl = dowUrl;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public String getMedias() {
		return medias;
	}

	public void setMedias(String medias) {
		this.medias = medias;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getFlowNum() {
		return flowNum;
	}

	public void setFlowNum(Integer flowNum) {
		this.flowNum = flowNum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public Integer getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(Integer auditUser) {
		this.auditUser = auditUser;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Double getPayDiscount() {
		return payDiscount;
	}

	public void setPayDiscount(Double payDiscount) {
		this.payDiscount = payDiscount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAdPosIds() {
		return adPosIds;
	}

	public void setAdPosIds(String adPosIds) {
		this.adPosIds = adPosIds;
	}

	public Integer getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(Integer appStatus) {
		this.appStatus = appStatus;
	}

	public String getAppDownurl() {
		return appDownurl;
	}

	public void setAppDownurl(String appDownurl) {
		this.appDownurl = appDownurl;
	}

	public String getConsumerMapped() {
		return consumerMapped;
	}

	public void setConsumerMapped(String consumerMapped) {
		this.consumerMapped = consumerMapped;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table apps
	 *
	 * @mbg.generated
	 * @project https://github.com/itfsw/mybatis-generator-plugin
	 */
	public static Apps.Builder builder() {
		return new Apps.Builder();
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the
	 * database table apps
	 *
	 * @mbg.generated
	 * @project https://github.com/itfsw/mybatis-generator-plugin
	 */
	public static class Builder {
		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the
		 * database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		private Apps obj;

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder() {
			this.obj = new Apps();
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.id
		 *
		 * @param id the value for apps.id
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder id(Integer id) {
			obj.setId(id);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.app_name
		 *
		 * @param appName the value for apps.app_name
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder appName(String appName) {
			obj.setAppName(appName);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.app_id
		 *
		 * @param appId the value for apps.app_id
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder appId(String appId) {
			obj.setAppId(appId);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.dow_url
		 *
		 * @param dowUrl the value for apps.dow_url
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder dowUrl(String dowUrl) {
			obj.setDowUrl(dowUrl);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.site_url
		 *
		 * @param siteUrl the value for apps.site_url
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder siteUrl(String siteUrl) {
			obj.setSiteUrl(siteUrl);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.apk_url
		 *
		 * @param apkUrl the value for apps.apk_url
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder apkUrl(String apkUrl) {
			obj.setApkUrl(apkUrl);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.medias
		 *
		 * @param medias the value for apps.medias
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder medias(String medias) {
			obj.setMedias(medias);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.platform
		 *
		 * @param platform the value for apps.platform
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder platform(Integer platform) {
			obj.setPlatform(platform);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.price
		 *
		 * @param price the value for apps.price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder price(Integer price) {
			obj.setPrice(price);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.flow_num
		 *
		 * @param flowNum the value for apps.flow_num
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder flowNum(Integer flowNum) {
			obj.setFlowNum(flowNum);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.type
		 *
		 * @param type the value for apps.type
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder type(Integer type) {
			obj.setType(type);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.create_user
		 *
		 * @param createUser the value for apps.create_user
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder createUser(Integer createUser) {
			obj.setCreateUser(createUser);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.create_time
		 *
		 * @param createTime the value for apps.create_time
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder createTime(Date createTime) {
			obj.setCreateTime(createTime);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.update_user
		 *
		 * @param updateUser the value for apps.update_user
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder updateUser(Integer updateUser) {
			obj.setUpdateUser(updateUser);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.update_time
		 *
		 * @param updateTime the value for apps.update_time
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder updateTime(Date updateTime) {
			obj.setUpdateTime(updateTime);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.audit_status
		 *
		 * @param auditStatus the value for apps.audit_status
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder auditStatus(Integer auditStatus) {
			obj.setAuditStatus(auditStatus);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.audit_opinion
		 *
		 * @param auditOpinion the value for apps.audit_opinion
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder auditOpinion(String auditOpinion) {
			obj.setAuditOpinion(auditOpinion);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.audit_user
		 *
		 * @param auditUser the value for apps.audit_user
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder auditUser(Integer auditUser) {
			obj.setAuditUser(auditUser);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.audit_time
		 *
		 * @param auditTime the value for apps.audit_time
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder auditTime(Date auditTime) {
			obj.setAuditTime(auditTime);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.pay_discount
		 *
		 * @param payDiscount the value for apps.pay_discount
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder payDiscount(Double payDiscount) {
			obj.setPayDiscount(payDiscount);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.status
		 *
		 * @param status the value for apps.status
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder status(Integer status) {
			obj.setStatus(status);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.ad_pos_ids
		 *
		 * @param adPosIds the value for apps.ad_pos_ids
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder adPosIds(String adPosIds) {
			obj.setAdPosIds(adPosIds);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.app_status
		 *
		 * @param appStatus the value for apps.app_status
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder appStatus(Integer appStatus) {
			obj.setAppStatus(appStatus);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.app_downurl
		 *
		 * @param appDownurl the value for apps.app_downurl
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder appDownurl(String appDownurl) {
			obj.setAppDownurl(appDownurl);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column apps.consumer_mapped
		 *
		 * @param consumerMapped the value for apps.consumer_mapped
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder consumerMapped(String consumerMapped) {
			obj.setConsumerMapped(consumerMapped);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Apps build() {
			return this.obj;
		}
	}

	/**
	 * This enum was generated by MyBatis Generator. This enum corresponds to the
	 * database table apps
	 *
	 * @mbg.generated
	 * @project https://github.com/itfsw/mybatis-generator-plugin
	 */
	public enum Column {
		id("id", "id", "INTEGER", false), appName("app_name", "appName", "VARCHAR", false),
		appId("app_id", "appId", "VARCHAR", false), dowUrl("dow_url", "dowUrl", "VARCHAR", false),
		siteUrl("site_url", "siteUrl", "VARCHAR", false), apkUrl("apk_url", "apkUrl", "VARCHAR", false),
		medias("medias", "medias", "VARCHAR", false), platform("platform", "platform", "INTEGER", false),
		price("price", "price", "INTEGER", false), flowNum("flow_num", "flowNum", "INTEGER", false),
		type("type", "type", "INTEGER", false), createUser("create_user", "createUser", "INTEGER", false),
		createTime("create_time", "createTime", "TIMESTAMP", false),
		updateUser("update_user", "updateUser", "INTEGER", false),
		updateTime("update_time", "updateTime", "TIMESTAMP", false),
		auditStatus("audit_status", "auditStatus", "INTEGER", false),
		auditOpinion("audit_opinion", "auditOpinion", "VARCHAR", false),
		auditUser("audit_user", "auditUser", "INTEGER", false),
		auditTime("audit_time", "auditTime", "TIMESTAMP", false),
		payDiscount("pay_discount", "payDiscount", "DOUBLE", false), status("status", "status", "INTEGER", false),
		adPosIds("ad_pos_ids", "adPosIds", "VARCHAR", false), appStatus("app_status", "appStatus", "INTEGER", false),
		appDownurl("app_downurl", "appDownurl", "VARCHAR", false),
		consumerMapped("consumer_mapped", "consumerMapped", "VARCHAR", false);

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the
		 * database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		private static final String BEGINNING_DELIMITER = "\"";

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the
		 * database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		private static final String ENDING_DELIMITER = "\"";

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the
		 * database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		private final String column;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the
		 * database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		private final boolean isColumnNameDelimited;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the
		 * database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		private final String javaProperty;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the
		 * database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		private final String jdbcType;

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public String value() {
			return this.column;
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public String getValue() {
			return this.column;
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public String getJavaProperty() {
			return this.javaProperty;
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public String getJdbcType() {
			return this.jdbcType;
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table apps
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
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public String desc() {
			return this.getEscapedColumnName() + " DESC";
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public String asc() {
			return this.getEscapedColumnName() + " ASC";
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public static Column[] excludes(Column... excludes) {
			ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
			if (excludes != null && excludes.length > 0) {
				columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
			}
			return columns.toArray(new Column[] {});
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table apps
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public String getEscapedColumnName() {
			if (this.isColumnNameDelimited) {
				return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER)
						.toString();
			} else {
				return this.column;
			}
		}
	}
}