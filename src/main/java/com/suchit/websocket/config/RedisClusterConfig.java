package com.suchit.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class RedisClusterConfig {

    @Bean
    public RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
        // provide nodes from application.yml (recommended) or hardcode here:
        clusterConfig.clusterNode("127.0.0.1", 7000);
        clusterConfig.clusterNode("127.0.0.1", 7001);
        clusterConfig.clusterNode("127.0.0.1", 7002);
        clusterConfig.clusterNode("127.0.0.1", 7003);
        clusterConfig.clusterNode("127.0.0.1", 7004);
        clusterConfig.clusterNode("127.0.0.1", 7005);
        // set password if required
         clusterConfig.setPassword(RedisPassword.of("myRedisPass123"));
//        clusterConfig.setPassword(RedisPassword.of(redisPassword));
        return clusterConfig;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(RedisClusterConfiguration clusterConfiguration) {
        // Lettuce supports Redis Cluster mode
        LettuceConnectionFactory lcf = new LettuceConnectionFactory(clusterConfiguration);
        // optional customizations: client config, pooling, timeouts
        return lcf;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(LettuceConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // register listeners/subscriptions as you already do
        return container;
    }

    @Bean
    public RedisCacheManager cacheManager(LettuceConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer()
                        ));

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }
}
