package com.juggle.juggle.framework.cache;

public interface ICache {
    <T> CacheData<T> get(String sector, String key, Object query, Class<T> clazz);
    boolean set(String sector, String key, Object query, Object value, int secondsToLive);
    void expiredAll();
}
