package com.jfhealthcare.common.utils;

import lombok.Data;

@Data
public class CacheObject {

	private long activeTime;
	
	private long timeOut;

	private Object value;

	public CacheObject(Object value,long timeOut) {
		super();
		this.activeTime = System.currentTimeMillis();
		this.timeOut = timeOut;
		this.value = value;
	}
	
	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getStringValue() {
		return String.valueOf(value);
	}
}
