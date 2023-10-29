package com.iwanvi.nvwa.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class AdPosition {
    private Integer id;

    private String name;

    private String flowUuid;

    private Integer appId;

    private Integer createType;

    private String templateId;

    private Integer outTemplateId;

    private Integer type;

    private Integer sellType;

    private Integer terminal;

    private String flowPositionId;

    private String uuid;

    private Integer status;

    private Integer channel;

    private Integer subChannel;

    private Integer carLevel;

    private Integer carSeries;

    private Integer areaLevel;

    private Integer area;

    private Integer priceRange;

    private Long forecastExposure;

    private Long forecastClick;

    private Integer minSellDay;

    private Integer updateUser;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Integer aikaTemplateId;

    private Integer os;

    private Integer isSupportDynamic;

    private Integer mappingSwitch;

    private String comment;

    private String flowName;

    private String templateName;

    private List<DateLimit> dateList;

    private List<AdPositionMapping> mappingList;

    private String mappingConsumer;

    private Integer title;

    private Integer picWidth;

    private Integer picHeight;

    private Integer videoWidth;

    private Integer videoHeight;

    private Integer duration;

    private Double forecastCtr;

    private String screenshot;

    private Integer locationX;

    private Integer locationY;

    private String osName;

    private String terminalName;

    private String consumerPositionId;

    private String consumerPositionName;

    private Integer flowConsumerId;

    private String appName;

    private String typeName;

    private String positionAppName;

    private Integer cp;

    private Integer ps;

    private String appIds;

    private List<Template> templateList;

    public Integer getId() {
        return id;
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

    public String getFlowUuid() {
        return flowUuid;
    }

    public void setFlowUuid(String flowUuid) {
        this.flowUuid = flowUuid;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getCreateType() {
        return createType;
    }

    public void setCreateType(Integer createType) {
        this.createType = createType;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Integer getOutTemplateId() {
        return outTemplateId;
    }

    public void setOutTemplateId(Integer outTemplateId) {
        this.outTemplateId = outTemplateId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSellType() {
        return sellType;
    }

    public void setSellType(Integer sellType) {
        this.sellType = sellType;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    public String getFlowPositionId() {
        return flowPositionId;
    }

    public void setFlowPositionId(String flowPositionId) {
        this.flowPositionId = flowPositionId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getSubChannel() {
        return subChannel;
    }

    public void setSubChannel(Integer subChannel) {
        this.subChannel = subChannel;
    }

    public Integer getCarLevel() {
        return carLevel;
    }

    public void setCarLevel(Integer carLevel) {
        this.carLevel = carLevel;
    }

    public Integer getCarSeries() {
        return carSeries;
    }

    public void setCarSeries(Integer carSeries) {
        this.carSeries = carSeries;
    }

    public Integer getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(Integer priceRange) {
        this.priceRange = priceRange;
    }

    public Long getForecastExposure() {
        return forecastExposure;
    }

    public void setForecastExposure(Long forecastExposure) {
        this.forecastExposure = forecastExposure;
    }

    public Long getForecastClick() {
        return forecastClick;
    }

    public void setForecastClick(Long forecastClick) {
        this.forecastClick = forecastClick;
    }

    public Integer getMinSellDay() {
        return minSellDay;
    }

    public void setMinSellDay(Integer minSellDay) {
        this.minSellDay = minSellDay;
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

    public Integer getAikaTemplateId() {
        return aikaTemplateId;
    }

    public void setAikaTemplateId(Integer aikaTemplateId) {
        this.aikaTemplateId = aikaTemplateId;
    }

    public Integer getOs() {
        return os;
    }

    public void setOs(Integer os) {
        this.os = os;
    }

    public Integer getIsSupportDynamic() {
        return isSupportDynamic;
    }

    public void setIsSupportDynamic(Integer isSupportDynamic) {
        this.isSupportDynamic = isSupportDynamic;
    }

    public Integer getMappingSwitch() {
        return mappingSwitch;
    }

    public void setMappingSwitch(Integer mappingSwitch) {
        this.mappingSwitch = mappingSwitch;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public Integer getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(Integer picWidth) {
        this.picWidth = picWidth;
    }

    public Integer getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(Integer picHeight) {
        this.picHeight = picHeight;
    }

    public Integer getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(Integer videoWidth) {
        this.videoWidth = videoWidth;
    }

    public Integer getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(Integer videoHeight) {
        this.videoHeight = videoHeight;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getForecastCtr() {
        return forecastCtr;
    }

    public void setForecastCtr(Double forecastCtr) {
        this.forecastCtr = forecastCtr;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public Integer getLocationX() {
        return locationX;
    }

    public void setLocationX(Integer locationX) {
        this.locationX = locationX;
    }

    public Integer getLocationY() {
        return locationY;
    }

    public void setLocationY(Integer locationY) {
        this.locationY = locationY;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public List<DateLimit> getDateList() {
        return dateList;
    }

    public void setDateList(List<DateLimit> dateList) {
        this.dateList = dateList;
    }

    public List<AdPositionMapping> getMappingList() {
        return mappingList;
    }

    public void setMappingList(List<AdPositionMapping> mappingList) {
        this.mappingList = mappingList;
    }

    public String getMappingConsumer() {
        return mappingConsumer;
    }

    public void setMappingConsumer(String mappingConsumer) {
        this.mappingConsumer = mappingConsumer;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getConsumerPositionId() {
        return consumerPositionId;
    }

    public void setConsumerPositionId(String consumerPositionId) {
        this.consumerPositionId = consumerPositionId;
    }

    public String getConsumerPositionName() {
        return consumerPositionName;
    }

    public void setConsumerPositionName(String consumerPositionName) {
        this.consumerPositionName = consumerPositionName;
    }

    public Integer getFlowConsumerId() {
        return flowConsumerId;
    }

    public void setFlowConsumerId(Integer flowConsumerId) {
        this.flowConsumerId = flowConsumerId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPositionAppName() {
        return positionAppName;
    }

    public void setPositionAppName(String positionAppName) {
        this.positionAppName = positionAppName;
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

    public String getAppIds() {
        return appIds;
    }

    public void setAppIds(String appIds) {
        this.appIds = appIds;
    }

    public List<Template> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<Template> templateList) {
        this.templateList = templateList;
    }
}