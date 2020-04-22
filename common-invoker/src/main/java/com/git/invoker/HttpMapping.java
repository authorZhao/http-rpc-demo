package com.git.invoker;

import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

@Data
public class HttpMapping {
    private String value;

    private RequestMethod requestMethod = RequestMethod.POST;

    private HttpMethod httpMethod = HttpMethod.POST;
    /**
     * 暂时不解析
     */
    private String[] params;

    private String[] headers;

    private String[] consumes;

    private String[] produces;

    public boolean contaionsMethod(RequestMethod[] method,RequestMethod requestMethod){
        if(method==null||method.length<=0)return false;
        return Arrays.stream(method).anyMatch(s->s.equals(requestMethod));
    }

}
