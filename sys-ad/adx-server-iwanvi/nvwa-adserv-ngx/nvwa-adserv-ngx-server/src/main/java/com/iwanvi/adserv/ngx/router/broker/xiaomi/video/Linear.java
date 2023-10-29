
package com.iwanvi.adserv.ngx.router.broker.xiaomi.video;

import javax.xml.bind.annotation.*;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Duration"/>
 *         &lt;element ref="{}Thumbnail"/>
 *         &lt;element ref="{}MediaFiles"/>
 *         &lt;element ref="{}VideoClicks"/>
 *         &lt;element ref="{}TrackingEvents"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "duration",
    "thumbnail",
    "mediaFiles",
    "videoClicks",
    "trackingEvents"
})
@XmlRootElement(name = "Linear")
public class Linear {

    @XmlElement(name = "Duration", required = true)
    protected String duration;
    @XmlElement(name = "Thumbnail", required = true)
    protected String thumbnail;
    @XmlElement(name = "MediaFiles", required = true)
    protected MediaFiles mediaFiles;
    @XmlElement(name = "VideoClicks", required = true)
    protected VideoClicks videoClicks;
    @XmlElement(name = "TrackingEvents", required = true)
    protected TrackingEvents trackingEvents;

    /**
     * 获取duration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuration() {
        return duration;
    }

    /**
     * 设置duration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuration(String value) {
        this.duration = value;
    }

    /**
     * 获取thumbnail属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * 设置thumbnail属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThumbnail(String value) {
        this.thumbnail = value;
    }

    /**
     * 获取mediaFiles属性的值。
     * 
     * @return
     *     possible object is
     *     {@link MediaFiles }
     *     
     */
    public MediaFiles getMediaFiles() {
        return mediaFiles;
    }

    /**
     * 设置mediaFiles属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link MediaFiles }
     *     
     */
    public void setMediaFiles(MediaFiles value) {
        this.mediaFiles = value;
    }

    /**
     * 获取videoClicks属性的值。
     * 
     * @return
     *     possible object is
     *     {@link VideoClicks }
     *     
     */
    public VideoClicks getVideoClicks() {
        return videoClicks;
    }

    /**
     * 设置videoClicks属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link VideoClicks }
     *     
     */
    public void setVideoClicks(VideoClicks value) {
        this.videoClicks = value;
    }

    /**
     * 获取trackingEvents属性的值。
     * 
     * @return
     *     possible object is
     *     {@link TrackingEvents }
     *     
     */
    public TrackingEvents getTrackingEvents() {
        return trackingEvents;
    }

    /**
     * 设置trackingEvents属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link TrackingEvents }
     *     
     */
    public void setTrackingEvents(TrackingEvents value) {
        this.trackingEvents = value;
    }

}
