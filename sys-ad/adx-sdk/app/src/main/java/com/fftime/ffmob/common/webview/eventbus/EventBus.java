package com.fftime.ffmob.common.webview.eventbus;

public interface EventBus {
  <T extends Event> void regListener(Class<T> type, EventListener listener);

  <T extends Event> void fire(T event);
}
