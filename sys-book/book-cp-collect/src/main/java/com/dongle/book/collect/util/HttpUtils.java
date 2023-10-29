package com.dongle.book.collect.util;

import net.sf.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Dongle
 * @desc  http请求工具类
 * @since 2021/8/1 8:46
 */
public class HttpUtils {

    private static final String METHOD_GET = "GET";
    private static final int TIMEOUT = 1000 * 60 * 5;
    private static final int HTTP_STATUS_302 = 302;
    private static final int HTTP_STATUS_301 = 301;
    private static final String HEAD_FIELD_LOCATION = "Location";
    private static final int READ_BYTE_SIZE = 5120;


    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings({"DM_DEFAULT_ENCODING", "OS_OPEN_STREAM"})
    public static JSONObject readUrlToGet(String url) {
        StringBuffer sb = new StringBuffer();
        InputStream istr = null;
        InputStreamReader isr = null;
        try {
            URL realUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod(METHOD_GET);
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.connect();
            int code = connection.getResponseCode();
            if (code == HTTP_STATUS_301 || code == HTTP_STATUS_302){
                String location = connection.getHeaderField(HEAD_FIELD_LOCATION);
                connection.disconnect();
                url = location;
                realUrl = new URL(url);
                connection = (HttpURLConnection) realUrl.openConnection();
                connection.setRequestMethod(METHOD_GET);
                connection.setConnectTimeout(TIMEOUT);
                connection.setReadTimeout(TIMEOUT);
                connection.connect();
            }
            istr = connection.getInputStream();
            isr = new InputStreamReader(istr);
            char[] cc = new char[READ_BYTE_SIZE];
            int getN = 0;
            while ((getN = isr.read(cc)) > 0) {
                sb.append(cc, 0, getN);
            }
        } catch (Exception ex) {
//            LOGGER.error("读取readUrlToGet失败,url为"+url+"异常为--" + ex.getMessage());
        }
        finally {
            try {
                if (istr!=null){
                    istr.close();
                }
                if (isr != null){
                    isr.close();
                }
            }catch (Exception e){
//                LOGGER.error("关闭流失败,异常为--" + e.getMessage());
            }
        }
        if ("".equals(sb.toString())) {
            JSONObject json = new JSONObject();
            json.put(CpConstant.JSON_FIELD_ERR_CODE, CpConstant.DEFAULT_ERROR_CODE);
            return json;
        }
        return JSONObject.fromObject(sb.toString());
    }
}
