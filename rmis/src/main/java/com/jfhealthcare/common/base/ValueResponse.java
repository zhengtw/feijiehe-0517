package com.jfhealthcare.common.base;

import org.apache.http.HttpStatus;

import lombok.Data;

@Data
public class ValueResponse extends BaseResponse{

	private String otherValue;
	
	public static ValueResponse getSuccessValue(Object data,String otherValue) {
		return getSuccessValue(data,otherValue,null);
	}
	
	public static ValueResponse getSuccessValue(Object data,String otherValue,String msg) {
		ValueResponse valueResponse=new ValueResponse();
		valueResponse.setOtherValue(otherValue);
		valueResponse.setData(data);
		valueResponse.setCode(HttpStatus.SC_OK);
		valueResponse.setMsg(msg);
		return valueResponse;
	}
	
	
	
}
