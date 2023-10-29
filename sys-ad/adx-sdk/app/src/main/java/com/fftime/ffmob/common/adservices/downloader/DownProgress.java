package com.fftime.ffmob.common.adservices.downloader;

public class DownProgress {
  private final long total;
  private long current;
  private int percent;

  public DownProgress(long total) {
    this.total = total;
  }

  boolean updateProgress(long current) {
    this.current = current;
    int p = (int) (current / (total / 100.0));
    if (this.percent != p) {
      this.percent = p;
      return true;
    } else {
      return false;
    }
  }

  public long getCurrent() {
    return current;
  }

  public int getPercent() {
    return percent;
  }

  public String getPercentStr() {
    return percent + "%";
  }

  public long getTotal() {
    return total;
  }
}
