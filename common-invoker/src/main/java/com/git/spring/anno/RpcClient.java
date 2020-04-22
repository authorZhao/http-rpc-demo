package com.git.spring.anno;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcClient {

    /**
     * 服务在注册中心的名称
     * @return
     */
    String value() default "";

    /**
     * url前缀，访问的url前缀
     * @return
     */
    String URLPre() default "";


    /**
     * 调用方式包括http、rpc
     * @return
     */
    String cllType() default "";

}
