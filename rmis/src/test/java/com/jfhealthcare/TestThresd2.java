package com.jfhealthcare;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestThresd2 {  
  
    // //通过JDK5中的锁来保证线程的访问的互斥  
    private static Lock lock = new ReentrantLock();  
  
    private static long state = 0;// 用state来判断轮到谁执行  
    private static long stateNum = 1;// 用state来判断轮到谁执行  
      
    private static final int RUN_NUMBER=100;//表示循环的次数  
    
    private static Map<String, Integer> caches = new HashMap<String, Integer>();
  
    //A线程  
    static class ThreadA extends Thread {  
    	long aa=0;
        @Override  
        public void run() {  
            try {
            	lock.lock();//获取锁定  
            	aa=state;
				        state++;  
				        stateNum++;
				        if(stateNum==4){
				        	stateNum=1;
				        }
				    
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();//释放锁定,不释放锁定，会被该线程一直保持  
			}
            System.out.println("===end"+aa);
        }  
    }  
  
    public static void main (String[] args){ 
    	System.out.println(6%3);
    	System.out.println(6%6);
//    	for (int i = 0; i < 20; i++) {
//    		new ThreadA().start();
//		}
    }  
  
}