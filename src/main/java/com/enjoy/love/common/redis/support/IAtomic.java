package com.enjoy.love.common.redis.support;

import java.util.concurrent.TimeUnit;

/**
 * 原子增减操作 
 * @author keke
 *
 * @param <K>
 */
public interface IAtomic<K> {
	/**
	 * set一个值,如果原值存在覆盖之
	 * 
	 * @param key
	 * @param value
	 */
	void set(K key, long value);

	/**
	 * set一个值,如果原值存在覆盖之
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 *            [超时]
	 * @param unit
	 *            [时间单位]
	 */
	void set(K key, long value, long timeout, TimeUnit unit);

	/**
	 * 原子递增
	 * 
	 * @param key
	 * @param delta
	 * @return
	 */
	Long incrementAndGet(K key, long delta);

	/**
	 * 原子递减
	 * 
	 * @param key
	 * @param delta
	 * @return
	 */
	Long decrementAndGet(K key, long delta);

	/**
	 * 根据Key取值
	 * 
	 * @param key
	 * @return
	 */
	Long getLong(K key);

	/**
	 * 删除KEY&值
	 * 
	 * @param key
	 */
	void delete(K key);

	/**
	 * 判断是否存在
	 * 
	 * @param key
	 * @return
	 */
	boolean isExist(K key);
}
