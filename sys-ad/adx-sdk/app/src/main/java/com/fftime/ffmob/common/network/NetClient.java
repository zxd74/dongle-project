package com.fftime.ffmob.common.network;

import android.os.Handler;
import android.os.Looper;

import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.Map.Entry;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.fftime.ffmob.common.FFTLoger;
import com.fftime.ffmob.common.status.DeviceStatus;
import com.fftime.ffmob.common.status.StatusManager;


public class NetClient {
  private static final String TAG = "NetClient";
  private static final int Max_QUEUE_SIZE = 50; // 默认最大队列长度

  public void syncExecute(PlainNetRequest netReq) {
    new Task(Priority.High,netReq).run();
  }

  public static enum Priority {
    High(1), Mid(2), Low(3);
    private int value;

    private Priority(int value) {
      this.value = value;
    }

    public int value() {
      return this.value;
    }
  }

  public byte[] getResponseBody(NetRequest request){
    HttpClient client = getHttpClient();
    HttpResponse origResp = null;
    switch (request.getMethod()) {
      case POST:
        String postUrl = request.getUrlWithParas();
        HttpPost post = new HttpPost(postUrl);
        setHeaderAndParas(post,request);
        try {
          byte[] postData = request.getPostData();
          if (postData != null && postData.length > 0) {
            post.setEntity(new ByteArrayEntity(postData));
          }
          origResp = client.execute(post);
          PlainADNetResponse resp = new PlainADNetResponse(origResp, (PlainNetRequest) request);
          return resp.getContentAsByteArray();
        } catch (Exception e) {
          //this.onError(e);
        } finally {
          post.abort();
          if (origResp != null && origResp.getEntity() != null) {
            try {
              origResp.getEntity().consumeContent();
            } catch (Exception e) {}
          }
        }
        break;
      case GET:
        HttpGet get = new HttpGet(request.getUrlWithParas());
        get.addHeader("Accept-Encoding", "gzip");
        setHeaderAndParas(get,request);
        try {
          origResp = client.execute(get);
          PlainADNetResponse resp = new PlainADNetResponse(origResp, (PlainNetRequest) request);
          return resp.getContentAsByteArray();
        } catch (Exception e) {
          //this.onError(e);
        } finally {
          get.abort();
          if (origResp != null && origResp.getEntity() != null) {
            try {
              origResp.getEntity().consumeContent();
            } catch (Exception e) {}
          }
        }
        break;
      default:
        FFTLoger.e(TAG, String.format("Unsupported HTTP method %s for url %s",
                request.getMethod(), request.getUrl()));
        break;
    }

    return null;
  }

  private void setHeaderAndParas(HttpRequestBase req,NetRequest request) {
    for (Entry<String, String> entry : request.getHeaders().entrySet()) {
      req.setHeader(entry.getKey(), entry.getValue());
    }
    req.setHeader("User-Agent", StatusManager.getInstance().getDeviceStatus().getUserAgent());
    HttpParams param = req.getParams();
    if (param == null) {
      param = new BasicHttpParams();
    }
    if (request.getConnectionTimeOut() > 0) {
      HttpConnectionParams.setConnectionTimeout(param, request.getConnectionTimeOut());
    }
    if (request.getSocketTimeOut() > 0) {
      HttpConnectionParams.setSoTimeout(param, request.getSocketTimeOut());
    }
    HttpClientParams.setRedirecting(param, true);
    req.setParams(param);
  }

  public String getResponseBodyAsString(NetRequest request){
    byte[] respBody = getResponseBody(request);
    if(respBody==null) return null;
    return new String(respBody, Charset.forName("UTF-8"));
  }

  private static class Task implements Runnable, Comparable<Task> {
    Priority priority;
    NetRequest request;
    int retryTimes = 1;

    public Task(Priority priority, NetRequest request, int retryTimes) {
      this.priority = priority;
      this.request = request;
      this.retryTimes = retryTimes;
    }

    public Task(Priority priority, NetRequest request) {
      this(priority, request, 1);
    }

    public void run() {
      try {
        doWork();
      } catch (Throwable e) {
        FFTLoger.w(TAG, "Exception while excute ADNetTask", e);
      }
    }

    private void doWork() {
      HttpClient client = getHttpClient();
      HttpResponse origResp = null;
      switch (request.getMethod()) {
        case POST:
          String postUrl = request.getUrlWithParas();
          HttpPost post = new HttpPost(postUrl);
          setHeaderAndParas(post);
          try {
            byte[] postData = this.request.getPostData();
            if (postData != null && postData.length > 0) {
              post.setEntity(new ByteArrayEntity(postData));
            }
            origResp = client.execute(post);
            this.request.onResponse(origResp);
          } catch (Exception e) {
            this.onError(e);
          } finally {
            post.abort();
            if (origResp != null && origResp.getEntity() != null) {
              try {
                origResp.getEntity().consumeContent();
              } catch (Exception e) {}
            }
          }
          break;
        case GET:
          HttpGet get = new HttpGet(request.getUrlWithParas());
          get.addHeader("Accept-Encoding", "gzip");
          setHeaderAndParas(get);
          try {
            origResp = client.execute(get);
            request.onResponse(origResp);
          } catch (Exception e) {
            this.onError(e);
          } finally {
            get.abort();
            if (origResp != null && origResp.getEntity() != null) {
              try {
                origResp.getEntity().consumeContent();
              } catch (Exception e) {}
            }
          }
          break;
        default:
          FFTLoger.e(TAG, String.format("Unsupported HTTP method %s for url %s",
              this.request.getMethod(), this.request.getUrl()));
          break;
      }
    }

    private void setHeaderAndParas(HttpRequestBase req) {
      for (Entry<String, String> entry : this.request.getHeaders().entrySet()) {
        req.setHeader(entry.getKey(), entry.getValue());
      }
      req.setHeader("User-Agent", StatusManager.getInstance().getDeviceStatus().getUserAgent());
      HttpParams param = req.getParams();
      if (param == null) {
        param = new BasicHttpParams();
      }
      if (this.request.getConnectionTimeOut() > 0) {
        HttpConnectionParams.setConnectionTimeout(param, this.request.getConnectionTimeOut());
      }
      if (this.request.getSocketTimeOut() > 0) {
        HttpConnectionParams.setSoTimeout(param, this.request.getSocketTimeOut());
      }
      HttpClientParams.setRedirecting(param, true);
      req.setParams(param);
    }

    @Override
    public int compareTo(Task another) {
      if (another == null) {
        return -1;
      } else {
        return this.priority.value - another.priority.value;
      }
    }

    private void onError(Exception e) {
      int times = retryTimes - 1;
      if (times > 0) {
        NetClient.getInstance().excute(request, priority, times);
      } else {
        this.request.onError(e);
      }
    }
  }

  private static final NetClient instance = new NetClient();
  private ThreadPoolExecutor excutor;

  public static final NetClient getInstance() {
    return instance;
  }

  private NetClient() {
    PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<Runnable>(15);
    this.excutor = new ThreadPoolExecutor(5, 10, 180l, TimeUnit.SECONDS, queue);
  }

  private static final int MAXRETRYTIMES = 5;

  /**
   * 提交自动重试的任务
   *
   * @param req
   * @param priority
   * @param retryTimes 当且仅当网络Exception时自动重试，response code != 202的情况不再重试。
   */
  public void excute(NetRequest req, Priority priority, int retryTimes) {
    if (retryTimes < 1) {
      retryTimes = 1;
    }
    if (retryTimes > MAXRETRYTIMES) {
      retryTimes = MAXRETRYTIMES;
    }
    if (this.isQueueFull()) {
      if (req != null) {
        req.onError(new Exception("FFT AD Network Queue is full,check network state"));
      }
    } else {
      this.excutor.execute(new Task(priority, req, retryTimes));
    }
  }

  public void excute(NetRequest req, Priority priority) {
    if (this.isQueueFull()) {
      if (req != null) {
        req.onError(new Exception("FFT AD Network Queue is full,check network state"));
      }
    } else {
      this.excutor.execute(new Task(priority, req));
    }
  }

  protected int getQueueSize() {
    return excutor.getQueue().size();
  }

  private boolean isQueueFull() {
    return this.excutor.getQueue().size() > Max_QUEUE_SIZE;
  }

  private static final HttpClient DefaultHttpClient;

  private static final int DEFAULT_SOCKET_TIMEOUT = 10000;

  private static final int DEFAULT_HOST_CONNECTIONS = 3;

  private static final int DEFAULT_MAX_CONNECTIONS = 10;

  static {
       final HttpParams httpParams = new BasicHttpParams();
    // timeout: get connections from connection pool
    ConnManagerParams.setTimeout(httpParams, DEFAULT_SOCKET_TIMEOUT);
    // timeout: connect to the server
    HttpConnectionParams.setConnectionTimeout(httpParams, DEFAULT_SOCKET_TIMEOUT);
    // timeout: transfer data from server
    HttpConnectionParams.setSoTimeout(httpParams, DEFAULT_SOCKET_TIMEOUT);

    // set max connections per host
    ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(
        DEFAULT_HOST_CONNECTIONS));
    // set max total connections
    ConnManagerParams.setMaxTotalConnections(httpParams, DEFAULT_MAX_CONNECTIONS);

    // // use expect-continue handshake
    // HttpProtocolParams.setUseExpectContinue(httpParams, true);
    // // disable stale check
    // HttpConnectionParams.setStaleCheckingEnabled(httpParams, false);

    HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
    HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
    //HttpClientParams.setRedirecting(httpParams, true);

    // set user agent
    DeviceStatus deviceStatus = StatusManager.getInstance().getDeviceStatus();
    String userAgent = null;
    if(deviceStatus != null){
      userAgent = StatusManager.getInstance().getDeviceStatus().getUserAgent();
    }
    HttpProtocolParams.setUserAgent(httpParams, userAgent);

    // // disable Nagle algorithm
    // HttpConnectionParams.setTcpNoDelay(httpParams, true);
    //
    // HttpConnectionParams.setSocketBufferSize(httpParams,
    // DEFAULT_SOCKET_BUFFER_SIZE);

    // scheme: http and https
    SchemeRegistry schemeRegistry = new SchemeRegistry();
    schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

    try {
      KeyStore trustStore = KeyStore.getInstance(KeyStore
              .getDefaultType());
      trustStore.load(null, null);

      SSLSocketFactory sslSocketFactory=new EasySSLSocketFactory(trustStore);
      sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

      schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
    }catch(Exception ex){

    }

    ClientConnectionManager manager = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
    DefaultHttpClient = new DefaultHttpClient(manager, httpParams);
  }

  private static HttpClient getHttpClient() {
    return DefaultHttpClient;
  }
}
