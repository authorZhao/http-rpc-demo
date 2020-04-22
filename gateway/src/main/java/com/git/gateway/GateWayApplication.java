package com.git.gateway;

import com.git.gateway.service.ArticleService;
import com.git.gateway.service.MenuService;
import com.git.gateway.service.TestService;
import com.git.spring.anno.EnableRemoteClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan({//"com.git.file",
        "com.git.gateway.controller","com.git.gateway.service"})
@EnableRemoteClient
@Import({TestService.class,ArticleService.class, MenuService.class})
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }

}
