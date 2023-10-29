package com.fftime.ffmob.common.webview.bridge;

/**
 * 
 * @author MR.Z
 *
 */
public class EventMsg extends Message {
  public static final String EVT_CBID = "event";

  public EventMsg(Event obj) {
    super(EVT_CBID, obj.toJSON().toString(), RespStatus.OK);
  }
}
