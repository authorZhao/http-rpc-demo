package com.git.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author authorZhao
 * @date 2019/12/31
 */
public class ApplicationUtil implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationUtil.class);

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 根据beanId获取bean
     * @param beanId
     * @return
     */
    public static Object getBean(String beanId) {
        if (beanId == null || beanId.length() == 0) {
            return null;
        }
        try {
            Object bean = context.getBean(beanId);
            if (bean == null) {
                logger.warn("获取不到bean:{}", beanId);
            }
            return bean;
        } catch (Exception e) {
            logger.warn("获取bean异常:{}", beanId);
            return null;
        }
    }

    /**
     * 根据类型获取bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            return context.getBean(clazz);
        } catch (Exception e) {
            logger.warn("获取不到bean:{}", clazz);
            return null;
        }
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            return context.getBean(name, clazz);
        } catch (Exception e) {
            logger.warn("获取不到bean:{}，{}", (Object) name, clazz);
            return null;
        }
    }

}
