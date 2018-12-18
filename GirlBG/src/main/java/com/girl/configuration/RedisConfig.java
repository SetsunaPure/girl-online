package com.girl.configuration;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.girl.Common.utils.SpringContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.Charset;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


/**
 * Redis缓存配置类
 *
 * @author szekinwin
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    static Map<String, RedisTemplate<String, ?>> map = new HashMap<>();

//    @Value("${zezs.redis.mqchans}")
//    private String mqchans="config";

//    @Bean //相当于xml中的bean
//    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
//                                            MessageListenerAdapter listenerAdapter) {
//
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        //这个container 可以添加多个 messageListener
//        if(StringUtils.isNoneBlank(mqchans)) {
//        	String[] split = mqchans.split(",");
//        	for (String string : split) {
//    			container.addMessageListener(listenerAdapter, new ChannelTopic(RedisKeysUtil.getKey(Constants.ZEZSMQ_CHART,string.trim())));
//        	}
//        }
//        return container;
//    }

//    /**
//     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
//     * @param receiver
//     * @return
//     */
//    @Bean
//    MessageListenerAdapter listenerAdapter(RedisMessageReceiver receiver) {
//        //这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
//        //也有好几个重载方法，这边默认调用处理器的方法 叫handleMessage 可以自己到源码里面看
//        return new MessageListenerAdapter(receiver, "handleMessage");
//    }

    //缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
//        RedisCacheManager cacheManager = new RedisCacheManager();
        //设置缓存过期时间
//        cacheManager.setDefaultExpiration(10000);
//        RedisCacheConfiguration redisCacheConfiguration=RedisCacheConfiguration.defaultCacheConfig().
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisCacheConfiguration cacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofDays(1))
                        .disableCachingNullValues()
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer(Charset.forName("utf-8"))))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        return RedisCacheManager.builder(factory).cacheDefaults(cacheConfiguration).build();
//        return cacheManager;
    }

    @Bean
    public StringRedisTemplate redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(factory);
        setSerializer(template);//设置序列化工具
        template.afterPropertiesSet();
        return template;
    }

    private void setSerializer(StringRedisTemplate template) {
        @SuppressWarnings({"rawtypes", "unchecked"})
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
    }

    public static <T> RedisTemplate<String, T> getTemplate(Class<T> class1) {
        @SuppressWarnings("unchecked")
        RedisTemplate<String, T> redisTemplate = (RedisTemplate<String, T>) map.get(class1.getName());
        if (redisTemplate == null) {
            RedisConnectionFactory redisConnectionFactory = SpringContext.getBean(RedisConnectionFactory.class);
            Jackson2JsonRedisSerializer<T> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<T>(class1);
            ObjectMapper om = new ObjectMapper();
            om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            jackson2JsonRedisSerializer.setObjectMapper(om);
            RedisTemplate<String, T> template = new RedisTemplate<String, T>();
            template.setConnectionFactory(redisConnectionFactory);
            template.setKeySerializer(new StringRedisSerializer(Charset.forName("utf-8")));
            template.setValueSerializer(jackson2JsonRedisSerializer);
            template.setHashKeySerializer(new StringRedisSerializer(Charset.forName("utf-8")));
            template.setHashValueSerializer(jackson2JsonRedisSerializer);
            template.afterPropertiesSet();
            redisTemplate = template;
        }
        return redisTemplate;

    }
}