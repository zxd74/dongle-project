package com.fftime.ffmob.common.adservices.downloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.content.Context;

import com.fftime.ffmob.common.FFTLoger;
import com.fftime.ffmob.util.Ping;
import com.fftime.ffmob.util.URLUtil;

public class APKDownloader {

  private static final String TAG = "APKDownloader";
  private static final Executor executor = Executors.newScheduledThreadPool(10);
  private static final ConcurrentHashMap<String, Integer> concurrentLock =
      new ConcurrentHashMap<String, Integer>();
  private final APKStatusManager statusManager;
  private final InnerDownProgressListener progressListener;

  public APKDownloader(APKStatusManager statusManager, InnerDownProgressListener progressListener) {
    this.statusManager = statusManager;
    this.progressListener = progressListener;
  }


  public void addTask(APKDownTask task, Context context) {
    if (concurrentLock.contains(task.pkgName)) {
      FFTLoger.e(TAG, "task for 《" + task.pkgName + "》 is already running");
    } else {
      concurrentLock.put(task.pkgName, 1);
      executor.execute(new DownCall(task, context));
    }
  }



  public enum DownResult {
    Succ, FileErr, NetWorkErr, IOErr;
  }

  public final class DownCall implements Runnable {
    private final APKDownTask task;
    private final Context context;
    private DownResult result;
    private DownProgress progress;

    public DownCall(APKDownTask task, Context context) {
      super();
      this.task = task;
      this.context = context;
    }


    @Override
    public void run() {
      FFTLoger.d(TAG, "开始下载应用：" + task.pkgName);
      statusManager.startDownloading(task.pkgName);
      progressListener.onProgressUpdate(task, null);
      result = null;
      Ping.ping(URLUtil.appendParam(task.getUrl(), APKParas.ACTKEY, APKParas.ACT_DOWNSTART));
      APKFileUtil.getDownDir(this.context).mkdirs();
      if (!APKFileUtil.getDownDir(context).exists()) {
        // 文件系统不可访问
        result = DownResult.FileErr;
        return;
      }
      HttpResponse resp = null;
      InputStream inStream = null;
      FileOutputStream fo = null;
      File tmpFile = APKFileUtil.getTmpFile(context, task.pkgName);
      HttpClient client = getDownloadHttpClient();
      HttpGet get = new HttpGet(task.url);
      try {
        resp = client.execute(get);
        long len = resp.getEntity().getContentLength();
        this.progress = new DownProgress(len);
        inStream = resp.getEntity().getContent();
        byte[] buffer = new byte[4096];
        int rlen = 0;
        long downSize = 0;
        fo = new FileOutputStream(tmpFile);
        while ((rlen = inStream.read(buffer)) > 0) {
          fo.write(buffer, 0, rlen);
          downSize += rlen;
          if (progress.updateProgress(downSize) && progress.getPercent() < 100) {
            FFTLoger.d(TAG,
                "downloaded progress " + downSize + "/" + len + "," + progress.getPercentStr());
            progressListener.onProgressUpdate(task, progress);
          }
        }
      } catch (ClientProtocolException e1) {
        e1.printStackTrace();
        result = DownResult.NetWorkErr;
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        result = DownResult.FileErr;
      } catch (IOException e) {
        e.printStackTrace();
        result = DownResult.IOErr;
      } finally {
        if (fo != null) {
          try {
            fo.close();
          } catch (IOException ie) {
            ie.printStackTrace();
          }
        }
        try {
          get.abort();
        } catch (Throwable te) {

        }
      }

      if (progress.getPercent() == 100
          && tmpFile.renameTo(APKFileUtil.getFinalFile(context, task.pkgName))) {
        result = DownResult.Succ;
        APKManager.getInstance(context).installAPK(task);
        progressListener.onProgressUpdate(task, progress);
      } else {
        result = DownResult.FileErr;
      }

      if (result == DownResult.Succ) {
        statusManager.succToDown(task.pkgName);
        Ping.ping(URLUtil.appendParam(task.url, APKParas.ACTKEY, APKParas.ACT_DOWNSUCC));
      } else {
        statusManager.failToDown(task.pkgName);
        Ping.ping(URLUtil.appendParam(task.url, APKParas.ACTKEY, APKParas.ACT_DOWNFAIL));
      }
      concurrentLock.remove(task.pkgName);
      FFTLoger.d(TAG, "完成下载应用：(" + result + ")" + task.pkgName);

    }

    private HttpClient getDownloadHttpClient() {
      HttpParams clientParam = new BasicHttpParams();
      HttpClientParams.setRedirecting(clientParam, true);
      HttpClient downLoadClient = new DefaultHttpClient(clientParam);
      return downLoadClient;
    }
  }
}
