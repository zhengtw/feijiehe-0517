package com.jfhealthcare.common.utils;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.ObjectUtils;

public class CacheUtils {
	
	public static final long oneMin=1000*60*1;
	
	public static final long halfHour=1000*60*30;
	
	public static final long oneHour=1000*60*60;
	
	public static final long oneDay=1000*60*60*24;
	/**
	 * 写个static方法块，里创建一个定时器
	 */
	static {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				for (Iterator<Object> it = CacheUtils.pool.keySet().iterator(); it
						.hasNext();) {
					Object key = it.next();
					CacheObject value = CacheUtils.pool.get(key);
					if ((null != value) && (value.getActiveTime() + value.getTimeOut() < System.currentTimeMillis())) {
						CacheUtils.pool.remove(key);
						value = null;
					}
				}
			}
		}, new Date(), 3600000L);
	}

	/**
	 * 存储数据的map,至于为什么用ConcurrentHashMap，可以百度
	 */
	private static Map<Object, CacheObject> pool = new ConcurrentHashMap<Object, CacheObject>();

	/**
	 * 将数据加入到Map中
	 */
	public static void add(Object key, Object value, long timeout) {
		// CacheObject对象中有当前保存时间和过期时间，值
		CacheObject co = new CacheObject(value.toString(), timeout);
		co.setActiveTime(System.currentTimeMillis());
		pool.put(key, co);
	}

	/**
	 * 将Map中数据更新
	 */
	public static void update(Object key, Object value, long timeout) {
		// CacheObject对象中有当前保存时间和过期时间，值
		CacheObject co = new CacheObject(value.toString(), timeout);
		co.setActiveTime(System.currentTimeMillis());
		pool.put(key, co);
	}

	/**
	 * 获取Map中的数据
	 */
	public static String get(String key) {

		CacheObject value = pool.get(key);
		// value不等于空情况下，判断保存时间＋超时时间 是否小于当前时间，小于说明过期了。从map中移除掉
		if ((null != value) && (value.getActiveTime()+value.getTimeOut() < System.currentTimeMillis())) {
			pool.remove(key);
			value = null;
		}
		if (null == value) {
			return null;
		}
		return value.getStringValue();
	}

	public static void main(String[] args) throws Exception {
		CacheUtils.add("A", "123123", 3000); //保存3秒
		CacheUtils.update("A", "aaaaaaa", 3000);
		for (int i = 0; i < 100; i++) {
			Thread.sleep(500);
			System.out.println(CacheUtils.get("A"));
			if(ObjectUtils.isEmpty(CacheUtils.get("A"))){
				System.out.println("kong ");
			}
		}
	}

}
