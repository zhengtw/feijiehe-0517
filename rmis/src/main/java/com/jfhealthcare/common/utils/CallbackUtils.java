package com.jfhealthcare.common.utils;

import org.springframework.stereotype.Component;

@Component
public class CallbackUtils {

	/**
	 * 回调接口
	 * @author jinma.xu
	 *
	 */
	public interface Callback {
		
		public void buildRun();
	}
	
	
	/**
	 * @author jinma.xu
	 * @param callback
	 */
	public static void buildCallback(final Callback callback)
	{
		Runnable run = new Runnable() {		
			public void run() {
				callback.buildRun();
			}
		};
		new Thread(run).start();
	}
	
	
}
