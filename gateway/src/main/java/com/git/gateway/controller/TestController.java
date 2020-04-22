package com.git.gateway.controller;

import com.git.gateway.service.ArticleService;
import com.git.gateway.service.MenuService;
import com.git.gateway.service.TestService;
import com.git.other.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author authorZhao
 * @date 2019/12/20
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MenuService menuService;

    @RequestMapping("/getById/{id}")
    public ApiResult getById(@PathVariable String id){
        return testService.getById(id);

    }

    @RequestMapping("/read/{id}")
    public ApiResult read(@PathVariable String id){
        return articleService.getById(id);

    }

    @RequestMapping("/getMenuByQuery")
    public ApiResult getMenuByQuery(){
        return menuService.getMenuByQuery();

    }

    @RequestMapping("/getMenuByRoleId/{id}")
    public ApiResult getMenuByRoleId(@PathVariable String id){
        return menuService.getMenuByRoleId(id);

    }

}
