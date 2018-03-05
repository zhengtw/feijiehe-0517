package com.jfhealthcare.modules.system.service;
public interface DistributedLock {

    /**
     * 获取锁
     * @author 
     * @time 
     * @return
     * @throws InterruptedException
     */
    public boolean acquire();

    /**
     * 释放锁
     * @author 
     * @time 
     */
    public void release();

}