package com.pedroleon.app.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import tech.jhipster.config.JHipsterProperties;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Autowired
    private ApplicationProperties applicationProperties;

    private final JHipsterProperties jHipsterProperties;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        if (!this.applicationProperties.isCacheEnabled()) {
            return new NoOpCacheManager();
        }

        RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);

        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration
            .defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(jHipsterProperties.getCache().getRedis().getExpiration())); // Set cache expiration time

        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, cacheConfiguration);

        return cacheManager;
    }
}
