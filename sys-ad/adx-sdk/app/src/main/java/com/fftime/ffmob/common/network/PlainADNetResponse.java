package com.fftime.ffmob.common.network;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.fftime.ffmob.common.FFTLoger;

public class PlainADNetResponse extends NetResponse {
  private static final String TAG = "PlainNetResponse";
  private PlainNetRequest req;

  public PlainADNetResponse(HttpResponse origResp, PlainNetRequest req) {
    super(origResp);
    this.req = req;
  }

  public byte[] getContentAsByteArray() throws IOException {
    byte[] rawContent = this.getRawContentAsByteArray();
    if (rawContent == null) {
      FFTLoger.e(TAG, "HTTPStatus Error for PlainRequst to URL" + req.getUrlWithParas()
              + " ;statusCode=" + this.getStatusCode());
      return null;
    }
    Header header = this.getOrigResp().getEntity().getContentEncoding();
    if (header != null && header.getValue().contains("gzip")) {
      return ZipCompress.decompressByGzip(rawContent);
    }
    return rawContent;
  }
}
