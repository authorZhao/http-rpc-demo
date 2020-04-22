package com.git.gateway.service;

import com.git.other.ApiResult;
import com.git.spring.anno.RpcClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author authorZhao
 * @date 2019/12/20
 */

@RpcClient(URLPre="http://localhost:8099/user")
public interface TestService {

    @RequestMapping(method = RequestMethod.POST)
    //@PostMapping(value = "/getById",produces = {"multipart/form-data; charset=UTF-8"})
    ApiResult getById(@RequestParam("id") String id);
}
