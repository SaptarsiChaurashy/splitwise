package com.ril.gamification.service.utils;

import org.slf4j.MDC;

import java.util.Map;

public class MdcSnapshot {
  private final Map<String, String> mdc;

  private MdcSnapshot() {
    this.mdc = MDC.getCopyOfContextMap();
  }

  public static MdcSnapshot getCurrentMdc() {
    return new MdcSnapshot();
  }

  public void populateMdc() {
    MDC.clear();
    if (mdc != null) {
      MDC.setContextMap(mdc);
    }
  }
}
