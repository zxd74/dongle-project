package com.iwanvi.adserv.ngx.router.broker.liebao;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 郑晓东
 * @date: 2019-06-10 19:08
 * @version: v1.0
 * @Description:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"tracking"})
@XmlRootElement(name = "TrackingEvents")
public class TrackingEvents {
    @XmlElement(name = "Tracking", nillable = true)
    protected List<Tracking> tracking;

    public List<TrackingEvents.Tracking> getTracking() {
        if (tracking == null) {
            tracking = new ArrayList<Tracking>();
        }
        return this.tracking;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class Tracking {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "event")
        protected String event;
        @XmlAttribute(name = "offset")
        protected String offset;

        /**
         * 获取value属性的值。
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getValue() {
            return value;
        }

        /**
         * 设置value属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * 获取event属性的值。
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getEvent() {
            return event;
        }

        /**
         * 设置event属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setEvent(String value) {
            this.event = value;
        }

        /**
         * 获取offset属性的值。
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getOffset() {
            return offset;
        }

        /**
         * 设置offset属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setOffset(String value) {
            this.offset = value;
        }

    }
}
