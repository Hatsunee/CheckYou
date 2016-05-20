package utils;

import play.cache.Cache;

public class AppCache {
  private final static Integer defaultSec = 3600;

  public static Object get(String s) {
    Object result = Cache.get(s);
    return result;
  }

  public static Object get(String ssid, String s) {
    Object result = Cache.get(String.format("{{%s}}{{%s}}", ssid, s));
    return result;
  }

  public static Object set(String ssid, String s, Object o) {
    Cache.set(String.format("{{%s}}{{%s}}", ssid, s), o, defaultSec);
    return o;
  }

  public static Object set(String ssid, String s, Object o, Integer i) {
    Cache.set(String.format("{{%s}}{{%s}}", ssid, s), o, i);
    return o;
  }
}