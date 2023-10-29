package com.fftime.ffmob.basead;

import java.util.concurrent.atomic.AtomicBoolean;

import android.app.Activity;

public class BaseADItem {
  private final String img;
  private final BaseAD loader;
  private AtomicBoolean displayed = new AtomicBoolean(false);

  BaseADItem(String img, BaseAD loader) {
    this.loader = loader;
    this.img = img;
  }

  public String getImg() {
    return img;
  }

  public void disp() {
    if (displayed.compareAndSet(false, true)) {
      loader.disp(this);
    }
  }

  public void click(Activity context) {
    loader.click(this, context);
  }
}
