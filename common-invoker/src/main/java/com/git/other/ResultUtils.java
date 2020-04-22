package com.git.other;


import java.util.HashMap;
import java.util.Map;

public class ResultUtils {

	public static final String SUCESS_MSG = "处理成功";
	public static final String FAILED_MSG = "处理失败";
	public static final Integer SUCESS_CODE = 0;
	public static final Integer FAILED_CODE = 1;

	private ResultUtils() {
	}

	public static ApiResult buildError(Integer errorCode, String msg){
        return new ApiResult(errorCode,msg);
    }

	public static ApiResult buildError(String msg){
		return new ApiResult(FAILED_CODE,msg);
	}

    public static ApiResult buildSucessMap(Map<String, Object> data){
        ApiResult apiResult =  new ApiResult(data);
        apiResult.setMsg(SUCESS_MSG);
        apiResult.setErrorCode(SUCESS_CODE);
        return apiResult;
    }


	public static ApiResult success(){
		ApiResult apiResult =  new ApiResult(null);
		apiResult.setMsg(SUCESS_MSG);
		apiResult.setErrorCode(SUCESS_CODE);
		return apiResult;
	}

	public static ApiResult error(){
		ApiResult apiResult =  new ApiResult(null);
		apiResult.setMsg(FAILED_MSG);
		apiResult.setErrorCode(FAILED_CODE);
		return apiResult;
	}


    public static ApiResult buildSucessObject(Object data){

        Map<String, Object> map = new HashMap<>();
        map.put("data",data);
        ApiResult apiResult =  new ApiResult(map);
        apiResult.setMsg(SUCESS_MSG);
        apiResult.setErrorCode(SUCESS_CODE);
        return apiResult;
    }
    public static ApiResult buildSucessObject(String keys, Object data){
        Map<String, Object> map = new HashMap<>();
        map.put(keys,data);
        ApiResult apiResult =  new ApiResult();
        apiResult.setMsg(SUCESS_MSG);
        apiResult.setErrorCode(SUCESS_CODE);
        apiResult.setData(map);
        return apiResult;
    }
	public static ApiResult buildObject(Integer code, String msg, Object data){
		Map<String, Object> map = new HashMap<>();
		map.put("data",data);
		ApiResult apiResult =  new ApiResult();
		apiResult.setMsg(msg);
		apiResult.setErrorCode(code);
		apiResult.setData(map);
		return apiResult;
	}

}

