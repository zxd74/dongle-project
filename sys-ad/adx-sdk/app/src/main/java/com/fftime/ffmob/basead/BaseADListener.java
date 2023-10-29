package com.fftime.ffmob.basead;

import java.util.Collection;

public interface BaseADListener {
  public static final int ERROR_NETWORK = -1;
  public static final int ERROR_RESPONSE = -2;

  void onSucc(Collection<BaseADItem> ads);

  void onFail(int errCode, String msg);
}
