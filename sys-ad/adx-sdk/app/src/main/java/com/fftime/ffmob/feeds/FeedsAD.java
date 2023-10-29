package com.fftime.ffmob.feeds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;

import com.fftime.ffmob.basead.BaseAD;
import com.fftime.ffmob.basead.BaseADItem;
import com.fftime.ffmob.basead.BaseADListener;

public class FeedsAD {

  private final BaseAD core;

  public FeedsAD(Context context, String appID, String posID) {
    super();
    this.core = new BaseAD(context, appID, posID);
  }
  
  public void loadAD(int count, final FeedsADListener listener) {
    this.core.loadAD(count, new BaseADListener() {
      
      @Override
      public void onSucc(Collection<BaseADItem> ads) {
        List<FeedsADItem> rt = new ArrayList<FeedsADItem>();
        for(BaseADItem item:ads){
          rt.add(new FeedsADItem(item));
        }
        listener.onSucc(rt);
      }
      
      @Override
      public void onFail(int errCode, String msg) {
        listener.onFail(errCode, msg);
      }
    });
  }
  

}
