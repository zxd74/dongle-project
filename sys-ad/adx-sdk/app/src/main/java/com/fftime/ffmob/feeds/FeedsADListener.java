package com.fftime.ffmob.feeds;

import java.util.Collection;


public interface FeedsADListener {
  public static final int ERROR_NETWORK = -1;
  public static final int ERROR_RESPONSE = -2;

  void onSucc(Collection<FeedsADItem> ads);

  void onFail(int errCode, String msg);
}
