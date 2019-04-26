package cn.web.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfig
{
  @Bean
  public CacheManager cacheManagerConfig(RedisTemplate<String, Object> redisTemplate)
  {
    RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
    
    cacheManager.setDefaultExpiration(259200L);
    return cacheManager;
  }
}
