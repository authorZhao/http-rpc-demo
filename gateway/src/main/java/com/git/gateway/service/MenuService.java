package com.git.gateway.service;

import com.git.other.ApiResult;
import com.git.spring.anno.RpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author authorZhao
 * @date 2019/12/20
 */

@RpcClient(URLPre="http://localhost:8099/menu")
public interface MenuService {

    @PostMapping("/getMenuByQuery")
    ApiResult getMenuByQuery();


    @GetMapping("/getMenuByRoleId")
    ApiResult getMenuByRoleId(@PathVariable String roleId);
}
