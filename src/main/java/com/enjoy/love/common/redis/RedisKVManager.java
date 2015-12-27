package com.enjoy.love.common.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.enjoy.love.common.redis.support.ICache;
import com.enjoy.love.common.util.Primitives;

@Component("redisKVManager")
public class RedisKVManager implements ICache<String> {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public boolean isExist(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void set(String key, Object value, long timeout, TimeUnit unit) {
		redisTemplate.opsForValue().set(key, value, timeout, unit);
	}

	@Override
	public Boolean setIfAbsent(String key, Object value) {
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	@Override
	public void multiSet(Map<? extends String, Object> m) {
		redisTemplate.opsForValue().multiSet(m);
	}

	@Override
	public Boolean multiSetIfAbsent(Map<? extends String, Object> m) {
		return redisTemplate.opsForValue().multiSetIfAbsent(m);
	}

	@Override
	public <T> T get(Object key, Class<T> classOfT) {
		return Primitives.wrap(classOfT).cast(
				redisTemplate.opsForValue().get(key));
	}

	@Override
	public <T> T getAndSet(String key, Object value, Class<T> classOfT) {
		return Primitives.wrap(classOfT).cast(
				redisTemplate.opsForValue().getAndSet(key, value));
	}

	@Override
	public List<Object> multiGet(Collection<String> keys) {
		return redisTemplate.opsForValue().multiGet(keys);
	}

	@Override
	public Integer append(String key, String value) {
		return redisTemplate.opsForValue().append(key, value);
	}

	@Override
	public String get(String key, long start, long end) {
		return redisTemplate.opsForValue().get(key, start, end);
	}

	@Override
	public void set(String key, Object value, long offset) {
		redisTemplate.opsForValue().set(key, value, offset);
	}

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public void deleteAll(Collection<String> keys) {
		redisTemplate.delete(keys);
	}

	/**
	 * ============域访问器==============
	 */

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
