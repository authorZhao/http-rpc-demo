package com.git.spring.anno;

import com.git.spring.processor.bean.RpcInstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启http远程调用
 * @author authorZhao
 * @date 2019/12/20
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RpcInstantiationAwareBeanPostProcessor.class)
public @interface EnableRemoteClient {
}
