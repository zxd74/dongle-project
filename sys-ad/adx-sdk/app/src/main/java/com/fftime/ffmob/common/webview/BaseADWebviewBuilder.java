package com.fftime.ffmob.common.webview;

import com.fftime.ffmob.common.webview.eventbus.ADLifeEvent;
import com.fftime.ffmob.common.webview.eventbus.EventListener;
import com.fftime.ffmob.common.webview.handlers.impl.ADLifeEventHandler;
import com.fftime.ffmob.common.webview.handlers.impl.ClickHandler;
import com.fftime.ffmob.common.webview.handlers.impl.DispHandler;
import com.fftime.ffmob.common.webview.handlers.impl.LoadADHandler;
import com.fftime.ffmob.common.webview.handlers.impl.RollableHandler;

import android.app.Activity;

public class BaseADWebviewBuilder {
  public static FFTWebview build(Activity activity, FFTWebviewDelegate delegate,
      EventListener lifeEventListener) {
    FFTWebview wv = null;
    //try {
      wv = new FFTWebview(activity, delegate, true);
    //}catch(Throwable ex){
      //wv = new FFTWebview(activity,delegate,true);
    //}
    wv.addEventListener(ADLifeEvent.class, lifeEventListener);
    wv.getBridge().bindHandlers(LoadADHandler.getInstance(),//
        ClickHandler.getInstance(),//
        DispHandler.getInstance(),//
        ADLifeEventHandler.getInstance(),//
        RollableHandler.getInstance());
    wv.setVerticalScrollBarEnabled(false);
    wv.setHorizontalScrollBarEnabled(false);
    wv.setClickable(true);
    return wv;
  }
}
