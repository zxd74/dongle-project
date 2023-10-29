
package com.iwanvi.adserv.ngx.router.broker.xiaomi.video;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


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
 *         &lt;element ref="{}Creative" maxOccurs="unbounded" minOccurs="0"/>
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
    "creative"
})
@XmlRootElement(name = "Creatives")
public class Creatives {

    @XmlElement(name = "Creative")
    protected List<Creative> creative;

    /**
     * Gets the value of the creative property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the creative property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCreative().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Creative }
     * 
     * 
     */
    public List<Creative> getCreative() {
        if (creative == null) {
            creative = new ArrayList<Creative>();
        }
        return this.creative;
    }

}
