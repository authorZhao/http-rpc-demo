package com.git.spring.factorys;

import com.git.clazz.ProxyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * 为Mapper生成代理对象
 * @author authorZhao
 * @param <T>
 */
public class MyFactoryBean<T> implements FactoryBean<T> {

    private Logger logger = LoggerFactory.getLogger(MyFactoryBean.class);

    private Class<T> interfaceClass;


    public MyFactoryBean(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
    public MyFactoryBean() {
    }


    /**
     * 这里指定代理的逻辑
     * @return
     * @throws Exception
     */
    @Override
    public T getObject() throws Exception {
        logger.info("正在为{}生成代理对象",interfaceClass.getName());
        T t = (T) ProxyUtil.getObject(interfaceClass);
        if(t==null){
            logger.error("{}代理对象,生成失败",interfaceClass.getName());
        }
        logger.info("{}代理对象,生成成功",interfaceClass.getName());
        return t;
    }

    @Override
    public Class<T> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * 如果采用方法注入属性，必须准备无参构造，不然报错
     * @param serviceInterface
     */
    public void setServiceInterface(Class<T> serviceInterface) {
        this.interfaceClass = serviceInterface;
    }
}
