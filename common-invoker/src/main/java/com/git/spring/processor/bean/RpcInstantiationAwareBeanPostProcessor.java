package com.git.spring.processor.bean;

import com.git.clazz.AnnotationUtil;
import com.git.clazz.ProxyUtil;
import com.git.inter.ProxyMethod;
import com.git.inter.impl.RpcMethodImpl;
import com.git.spring.anno.RpcClient;
import com.git.spring.factorys.MyFactoryBean;
import com.google.common.base.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * @author authorZhao
 * @date 2019/12/20
 */
@Slf4j
public class RpcInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor , ApplicationContextAware {


    private ApplicationContext applicationContext;
    /**
     * 实例化前
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {

        RpcClient rpcClient = AnnotationUtil.getAnnotation(beanClass, RpcClient.class);
        if (rpcClient == null) {
            return null;
        }
        log.info("正在为：{}生成代理对象,被代理的类为：{}",beanName,beanClass.getName());
        Supplier<ProxyMethod<Object, Method, Object[], Object>> supplier = RpcMethodImpl::httpRemote;
        Object object = ProxyUtil.getObject(beanClass, supplier.get());
        return object;

    }

    /**
     * 实例化后
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }


    /**
     * 初始化钱
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 初始化后
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext=applicationContext;
    }
}
