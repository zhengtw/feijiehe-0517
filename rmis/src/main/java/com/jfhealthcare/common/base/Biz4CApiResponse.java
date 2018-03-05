package com.jfhealthcare.common.base;


import lombok.Data;

import java.util.Date;

/**
 * 返回数据
 * 
 * @author xujinma
 */
@Data
public class Biz4CApiResponse {
	/**
	 * 返回状态
	 */
    private String status;
	
    /**
     * 返回时间
     */
	private Date cacheDate = new Date();
	
	/**
	 * 返回结果码
	 */
	private String resultCode;
	
	/**
	 * 返回信息
	 */
	private String msg;
	
	/**
	 * 返回数据
	 */
	private Object data;
	
	public static Biz4CApiResponse getSuccessResponse() {
		return getSuccessResponse(new Date(),null);
	}
	
	public static Biz4CApiResponse getSuccessResponse(Object data) {
		return getSuccessResponse(new Date(),data);
	}
	
	public static Biz4CApiResponse getSuccessResponse(Date cacheDate, Object data){
		Biz4CApiResponse response = new Biz4CApiResponse();
		response.setStatus("200");
		response.setResultCode("success");
		response.setMsg("成功");
		response.setData(data);
		return response;
	}
	public static Biz4CApiResponse getSuccessResponse(Date cacheDate, Object data,String msg){
		Biz4CApiResponse response = new Biz4CApiResponse();
		response.setStatus("200");
		response.setResultCode("success");
		response.setMsg(msg);
		response.setData(data);
		return response;
	}
	
	public static Biz4CApiResponse getFailResponse(String status, String msg) {
		Biz4CApiResponse response = new Biz4CApiResponse();
		response.setStatus(status);
		response.setResultCode("fail");
		response.setMsg(msg);
		return response;
	}
}
