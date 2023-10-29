package com.fftime.ffmob.common.webview.eventbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBusImpl implements EventBus {

  private Map<Class<? extends Event>, List<EventListener>> map =
      new HashMap<Class<? extends Event>, List<EventListener>>();

  @Override
  public <T extends Event> void regListener(Class<T> type, EventListener listener) {
    if (!this.map.containsKey(type)) {
      this.map.put(type, new ArrayList<EventListener>());
    }
    this.map.get(type).add(listener);
  }

  @Override
  public <T extends Event> void fire(T event) {
    if (event == null) {
      return;
    }
    List<EventListener> listeners = this.map.get(event.getClass());
    for (EventListener l : listeners) {
      l.handle(event);
    }
  }

}
