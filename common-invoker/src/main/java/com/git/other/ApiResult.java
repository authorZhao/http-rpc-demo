package com.git.other;

import java.util.Map;

public class ApiResult {

	public Integer errorCode;

	public String msg;

	public Map<String,Object> data;

	public ApiResult() {
		super();
	}

	public ApiResult(Integer errorCode, String msg) {
		this.errorCode = errorCode;
		this.msg = msg;
	}

	public ApiResult(Map<String, Object> data) {
		this.data = data;
	}

	public ApiResult(Integer errorCode, String msg, Map<String, Object> data) {
		this.errorCode = errorCode;
		this.msg = msg;
		this.data = data;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
