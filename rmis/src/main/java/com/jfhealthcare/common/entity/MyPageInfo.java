package com.jfhealthcare.common.entity;

import java.util.List;

import com.github.pagehelper.PageInfo;

public class MyPageInfo<T> extends PageInfo<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private T value;
	
	private List<?> otherValue;
	
	public MyPageInfo(List<T> list) {
		super(list);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	public List<?> getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(List<?> otherValue) {
		this.otherValue = otherValue;
	}

}
