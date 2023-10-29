package com.fftime.ffmob.feeds;

import android.app.Activity;

import com.fftime.ffmob.basead.BaseADItem;

public class FeedsADItem {

  private final BaseADItem adItem;

  public FeedsADItem(BaseADItem adItem) {
    super();
    this.adItem = adItem;
  }

  public String getImg() {
    return adItem.getImg();
  }

  public void disp() {
    this.adItem.disp();
  }

  public void click(Activity context) {
    this.adItem.click(context);
  }

  public BaseADItem getAdItem() {
    return adItem;
  }
}
