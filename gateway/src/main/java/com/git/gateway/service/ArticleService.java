package com.git.gateway.service;

import com.git.other.ApiResult;
import com.git.spring.anno.RpcClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author authorZhao
 * @date 2019/12/20
 */

@RpcClient(URLPre="http://localhost:8099/article")
public interface ArticleService {

    @GetMapping(value = "/read",consumes = "text/html;charset=UTF-8")
    ApiResult getById(@PathVariable("id") String id);
}
