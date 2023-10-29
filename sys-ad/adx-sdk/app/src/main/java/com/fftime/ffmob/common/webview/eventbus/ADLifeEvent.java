package com.fftime.ffmob.common.webview.eventbus;

import org.json.JSONObject;

public class ADLifeEvent extends Event {
  public static enum ADLifeEventType {
    ADLoadSucc, //
    ADLoadFail, //
    ADReady, //
    Exposured, Clicked, //
    PopupDisplay, //
    PopupClosed, //
    ADClosed; //

    public static ADLifeEventType fromString(String str) {
      for (ADLifeEventType t : ADLifeEventType.values()) {
        if (t.name().equals(str)) {
          return t;
        }
      }
      return null;
    }
  }

  private final ADLifeEventType type;
  private final JSONObject data;

  public ADLifeEvent(ADLifeEventType type, JSONObject data) {
    super();
    this.type = type;
    this.data = data;
  }

  public ADLifeEventType getType() {
    return type;
  }

  public JSONObject getData() {
    return data;
  }

}
