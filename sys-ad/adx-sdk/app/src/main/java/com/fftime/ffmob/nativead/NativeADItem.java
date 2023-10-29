package com.fftime.ffmob.nativead;

import android.app.Activity;

import com.fftime.ffmob.basead.BaseADItem;

public class NativeADItem {

  private final BaseADItem adItem;

  public NativeADItem(BaseADItem adItem) {
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
}
