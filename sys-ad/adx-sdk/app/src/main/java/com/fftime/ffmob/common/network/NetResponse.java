package com.fftime.ffmob.common.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

public abstract class NetResponse {
  private HttpResponse origResp;

  public NetResponse(HttpResponse origResp) {
    this.origResp = origResp;
  }

  protected HttpResponse getOrigResp() {
    return this.origResp;
  }

  /**
   * @see HttpEntity#getContent()
   * @return
   * @throws IOException
   * @throws IllegalStateException
   */
  public InputStream getRawContent() throws IllegalStateException, IOException {
    return this.origResp.getEntity().getContent();
  }

  /**
   * 关闭原始 http response 的输入流
   * 
   * @see InputStream#close()
   * @throws IllegalStateException
   * @throws IOException
   */
  public void close() throws IllegalStateException, IOException {
    this.origResp.getEntity().getContent().close();
  }

  protected byte[] getRawContentAsByteArray() throws IOException {
    if (HttpStatus.SC_OK != this.getStatusCode()) {
      return null;
    }
    long contentLen = origResp.getEntity().getContentLength();
    InputStream in = origResp.getEntity().getContent();
    ByteArrayOutputStream bo = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len = 0;
    while ((len = (in.read(buffer))) > 0) {
      bo.write(buffer, 0, len);
    }
    if (contentLen > 0 && bo.size() != contentLen) {
      String errMsg =
          String.format(Locale.US, "ContentLength not match (%d,%d)", bo.size(), contentLen);
      throw new IOException(errMsg);
    }
    return bo.toByteArray();
  }

  /**
   * 
   * @return 原始response内容的Byte数组形式（进行必要的解包之后的数据）。如果response 的status !=200则返回null。调用者应首先检查response
   *         status。
   * @throws IOException
   */
  public abstract byte[] getContentAsByteArray() throws IOException;

  /**
   * 将返回内容按照string返回。
   * 
   * @param defaultEncoding 默认字符编码，当原始response中不包含encoding头时使用
   * @return 如果http response status 有问题返回null ,如果返回内容为0字节则返回空串。
   * @throws IOException
   */
  public String getContentAsString(String defaultEncoding) throws IOException {
    byte[] bytes = getContentAsByteArray();
    if (bytes == null) {
      return null;
    } else if (bytes.length == 0) {
      return "";
    }

    String charset = null;
    try {
      charset = EntityUtils.getContentCharSet(this.origResp.getEntity());
    } catch (Throwable th) {}
    if (charset == null) {
      charset = defaultEncoding;
    }

    return new String(bytes, charset);
  }

  /**
   * 按照UTF-8编码将返回值解析为字符串
   * 
   * @see #getContentAsString(String)
   * @return
   * @throws IOException
   */
  public String getContentAsString() throws IOException {
    return this.getContentAsString("UTF-8");
  }

  /**
   * @see HttpStatus
   * @see HttpResponse#getStatusLine(){@link #getStatusCode()}
   * @return 原始HTTPStatus code
   */
  public int getStatusCode() {
    return this.origResp.getStatusLine().getStatusCode();
  }
}
