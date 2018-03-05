package com.jfhealthcare.common.exception;

import org.apache.http.HttpStatus;

/**
 * 自定义异常
 * @author xujinma
 */
public class RmisException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String msg;
    private int code = HttpStatus.SC_INTERNAL_SERVER_ERROR;
    
    public RmisException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public RmisException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public RmisException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public RmisException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
