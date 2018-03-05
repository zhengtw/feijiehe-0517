package com.jfhealthcare.common.utils;

import org.springframework.data.redis.core.StringRedisTemplate;

import com.jfhealthcare.modules.system.service.DistributedLock;
import com.jfhealthcare.modules.system.service.impl.JedisLock;

public class DistributedLockUtil{
    /**
     * 获取分布式锁
     * 默认获取锁10s超时，锁过期时间60s
     * @author yangwenkui
     * @time 2016年5月6日 下午1:30:46
     * @return
     */
    public static DistributedLock getDistributedLock(StringRedisTemplate redisTemplate,String lockKey){
        lockKey = assembleKey(lockKey);
        JedisLock lock = new JedisLock(redisTemplate,lockKey);
        return lock;
    }

    /**
     * 正式环境、测试环境共用一个redis时，避免key相同造成影响
     * @author yangwenkui
     * @param lockKey
     * @return
     */
    private static String assembleKey(String lockKey) {
        return String.format("lock_%s",lockKey);
    }

    /**
     * 获取分布式锁
     * 默认获取锁10s超时，锁过期时间60s
     * @author yangwenkui
     * @time 2016年5月6日 下午1:38:32
     * @param lockKey
     * @param timeoutMsecs 指定获取锁超时时间
     * @return
     */
    public static DistributedLock getDistributedLock(StringRedisTemplate redisTemplate,String lockKey,int timeoutMsecs){
        lockKey = assembleKey(lockKey);
        JedisLock lock = new JedisLock(redisTemplate,lockKey,timeoutMsecs);
        return lock;
    }

    /**
     * 获取分布式锁
     * 默认获取锁10s超时，锁过期时间60s
     * @author yangwenkui
     * @time 2016年5月6日 下午1:40:04
     * @param lockKey 锁的key
     * @param timeoutMsecs 指定获取锁超时时间
     * @param expireMsecs 指定锁过期时间
     * @return
     */
    public static DistributedLock getDistributedLock(StringRedisTemplate redisTemplate,String lockKey,int timeoutMsecs,int expireMsecs){
        lockKey = assembleKey(lockKey);
        JedisLock lock = new JedisLock( redisTemplate,lockKey,expireMsecs,timeoutMsecs);
        return lock;
    }

}