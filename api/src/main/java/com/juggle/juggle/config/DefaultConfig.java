package com.juggle.juggle.config;

import com.juggle.juggle.framework.cache.CacheAdvise;
import com.juggle.juggle.framework.cache.ICache;
import com.juggle.juggle.framework.cache.local.LocalCache;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.juggle.juggle.framework.http.HttpClientConnectionManagerFactory;
import com.juggle.juggle.framework.http.SyncHttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DefaultConfig {

    @Bean
    @Primary
    public ICache localCache() {
        LocalCache cache = new LocalCache("/ehcache/ehcache.xml") {
            @Override
            public String makeCacheId(String sector, String key, Object query) {
                if (null != UserSession.getAuthorize()) {
                    sector = UserSession.getAuthorize().getToken() + "/" + sector;
                }
                return super.makeCacheId(sector, key, query);
            }
        };
        return cache;
    }

    @Bean
    public CacheAdvise cacheAdvise(ICache cache) {
        CacheAdvise advise = new CacheAdvise();
        advise.setCache(cache);
        return advise;
    }

    @Bean
    public SyncHttpClient apiHttpClient() {
        HttpClientConnectionManager connectionManager = HttpClientConnectionManagerFactory.defaultPoolingConfig();
        SyncHttpClient httpClient = new SyncHttpClient();
        httpClient.setMapper(JsonUtils.getMapperInstance());
        httpClient.setConnectionManager(connectionManager);
        return httpClient;
    }
}
