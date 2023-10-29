
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
 *         &lt;element ref="{}InLine"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "inLine"
})
@XmlRootElement(name = "Ad")
public class Ad {

    @XmlElement(name = "InLine", required = true)
    protected InLine inLine;
    @XmlAttribute(name = "id")
    protected String id;

    /**
     * 获取inLine属性的值。
     * 
     * @return
     *     possible object is
     *     {@link InLine }
     *     
     */
    public InLine getInLine() {
        return inLine;
    }

    /**
     * 设置inLine属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link InLine }
     *     
     */
    public void setInLine(InLine value) {
        this.inLine = value;
    }

    /**
     * 获取id属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
