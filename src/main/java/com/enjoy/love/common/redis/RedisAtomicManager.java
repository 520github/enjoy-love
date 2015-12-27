package com.enjoy.love.common.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import com.enjoy.love.common.redis.support.IAtomic;

@Component("redisAtomicManager")
public class RedisAtomicManager implements IAtomic<String> {
	@Autowired
    private RedisTemplate<String, Long> redisTemplate;

    @Override
    public void set(String key, long value) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        entityIdCounter.set(value);
    }

    @Override
    public void set(String key, long value, long timeout, TimeUnit unit) {
        RedisTemplate<String, Long> redisTemplate = new RedisTemplate<String, Long>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<Long>(Long.class));
        redisTemplate.setExposeConnection(true);
        redisTemplate.setConnectionFactory(this.redisTemplate.getConnectionFactory());
        redisTemplate.afterPropertiesSet();
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public Long incrementAndGet(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Long decrementAndGet(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    @Override
    public Long getLong(String key) {
        if (isExist(key))
            return redisTemplate.opsForValue().increment(key, 0);
        else
            return null;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean isExist(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * ============域访问器==============
     */

    public RedisTemplate<String, Long> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
