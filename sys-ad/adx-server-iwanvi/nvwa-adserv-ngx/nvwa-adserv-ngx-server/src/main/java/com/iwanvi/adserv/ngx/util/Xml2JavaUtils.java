package com.iwanvi.adserv.ngx.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/**
 * @author: 郑晓东
 * @date: 2019-07-19 11:33
 * @version: v1.0
 * @Description:
 */
public class Xml2JavaUtils {
    /**
     * xml转换成JavaBean
     * @param xml  内容
     * @param c
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T converyToBean(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
