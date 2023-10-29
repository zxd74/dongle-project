package com.fftime.ffmob.common.adservices.downloader;

import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;

@SuppressLint("NewApi")
public abstract class FFTNotificationBuilder {
  public static FFTNotificationBuilder builderBySupportV4(Context context) {
    return new FFTNotificationBuilderBySupportV4(context);
  }

  public abstract FFTNotificationBuilder setContentTitle(String title);

  public abstract FFTNotificationBuilder setContentText(String text);

  public abstract FFTNotificationBuilder setLargeIcon(Bitmap largeIcon);

  public abstract FFTNotificationBuilder setAutoCancel(boolean auto);

  public abstract FFTNotificationBuilder setProgress(int max, int progress, boolean indeterminate);

  public abstract FFTNotificationBuilder setContentIntent(PendingIntent intent);

  public abstract Notification build();


  private static class FFTNotificationBuilderBySupportV4 extends FFTNotificationBuilder {

    private NotificationCompat.Builder core;
    private Method buildMethod;
    private boolean hasSetProgressMethod = true;

    public FFTNotificationBuilderBySupportV4(Context context) {
      this.core = new NotificationCompat.Builder(context);
      try {
        if (core.getClass().getMethod("build") != null) {
          buildMethod = core.getClass().getMethod("build");
        }
      } catch (Throwable e) {
        e.printStackTrace();
      }
      if (buildMethod == null) {
        try {
          if (core.getClass().getMethod("getNotification") != null) {
            buildMethod = core.getClass().getMethod("getNotification");
          }
        } catch (Throwable e) {
          e.printStackTrace();
        }
      }
      this.core.setSmallIcon(android.R.drawable.stat_sys_download_done);
    }

    @Override
    public FFTNotificationBuilder setContentTitle(String title) {
      core.setContentTitle(title);
      return this;
    }

    @Override
    public FFTNotificationBuilder setContentText(String text) {
      core.setContentText(text);
      return this;
    }

    @Override
    public FFTNotificationBuilder setLargeIcon(Bitmap largeIcon) {
      core.setLargeIcon(largeIcon);
      return this;
    }

    @Override
    public FFTNotificationBuilder setAutoCancel(boolean auto) {
      core.setAutoCancel(auto);
      return this;
    }

    @Override
    public FFTNotificationBuilder setProgress(int max, int progress, boolean indeterminate) {
      if (hasSetProgressMethod) {
        try {
          core.setProgress(max, progress, indeterminate);
        } catch (Throwable e) {
          hasSetProgressMethod = false;
        }
      }
      return this;
    }

    @Override
    public FFTNotificationBuilder setContentIntent(PendingIntent intent) {
      this.core.setContentIntent(intent);
      return this;
    }


    @Override
    public Notification build() {
      if (buildMethod != null) {
        try {
          return (Notification) buildMethod.invoke(core);
        } catch (Throwable e) {
          return null;
        }
      } else {
        return null;
      }
    }

  }


}
