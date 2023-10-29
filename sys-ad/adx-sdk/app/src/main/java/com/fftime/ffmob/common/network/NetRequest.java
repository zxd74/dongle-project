package com.fftime.ffmob.common.network;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;

import com.fftime.ffmob.util.StringUtil;

public abstract class NetRequest {
  public static enum Method {
    GET, POST
  }

  private int connectionTimeOut;
  private int socketTimeOut;
  private String url;
  private Map<String, String> _headers = new HashMap<String, String>();
  private Map<String, String> _paras = new HashMap<String, String>();
  private Map<String, String> headers = Collections.unmodifiableMap(this._headers);
  private Map<String, String> paras = Collections.unmodifiableMap(this._paras);
  private Method method;
  /**
   * 只在method 为post时有效
   */
  private byte[] postData;

  /**
   * 
   * @param url
   * @param method
   * @param postData : 只在使用Post方法时生效，get时将被忽略.PostData一旦被提交后则不可修改。
   */
  public NetRequest(String url, Method method, byte[] postData) {
    super();
    this.url = url;
    this.method = method;
    if (postData == null) {
      this.postData = null;
    } else {
      this.postData = postData.clone();
    }
  }

  /**
   * 
   * @return 请不要修改此方法返回的数组内容。否则不保证程序能够正常执行
   */
  public byte[] getPostData() {
    return postData;
  }

  public Method getMethod() {
    return method;
  }

  public String getUrl() {
    return url;
  }

  public Map<String, String> getHeaders() {
    return this.headers;
  }

  public Map<String, String> getParas() {
    return this.paras;
  }

  /**
   * 如果key 或者 value 为空或者null 则直接跳过
   * 
   * @param key
   * @param value
   */
  public void addHeader(String key, String value) {
    if (StringUtil.isEmpty(key) || StringUtil.isEmpty(value)) {
      return;
    }
    this._headers.put(key, value);
  }

  /**
   * 如果key 或者 value 为空或者null 则直接跳过。暂时不支持同一个key多个value的情况，最后一个有效的value生效 param
   * 最终将append到URL中发送给Server端
   * 
   * @param key
   * @param value
   */
  public NetRequest addParam(String key, String value) {
    this._paras.put(key, value);
    return this;
  }

  /**
   * @return 返回将params拼接在原始URL之后的新URL。连接原始URL与param的标准是如果原始URL有？则使用&连接，否则使用?拼接。 内部函数不要做极限挑战哦
   */
  public String getUrlWithParas() {
    if (this.getParas().isEmpty()) {
      return this.getUrl();
    } else {
      StringBuilder sb = new StringBuilder();
      for (Entry<String, String> entry : this.getParas().entrySet()) {
        sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
      }
      String url = this.getUrl();
      if (url == null) {
        return sb.substring(0, sb.length() - 1);
      }
      if (url.contains("?")) {
        return url + "&" + sb.substring(0, sb.length() - 1);
      } else {
        return url + "?" + sb.substring(0, sb.length() - 1);
      }
    }
  }

  public int getConnectionTimeOut() {
    return connectionTimeOut;
  }

  public void setConnectionTimeOut(int connectionTimeOut) {
    this.connectionTimeOut = connectionTimeOut;
  }

  public int getSocketTimeOut() {
    return socketTimeOut;
  }

  public void setSocketTimeOut(int socketTimeOut) {
    this.socketTimeOut = socketTimeOut;
  }

  public abstract void onResponse(HttpResponse origResp);

  public abstract void onError(Exception e);
}
