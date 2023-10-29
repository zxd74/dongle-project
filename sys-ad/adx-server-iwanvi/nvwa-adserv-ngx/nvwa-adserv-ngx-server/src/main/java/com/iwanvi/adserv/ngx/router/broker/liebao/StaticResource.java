package com.iwanvi.adserv.ngx.router.broker.liebao;

import javax.xml.bind.annotation.*;

/**
 * @author: 郑晓东
 * @date: 2019-06-10 19:09
 * @version: v1.0
 * @Description:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "value"
})
public class StaticResource {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "type")
    protected String type;

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
     * 获取type属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getType() {
        return type;
    }

    /**
     * 设置type属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setType(String value) {
        this.type = value;
    }

}
