package com.git.clazz;



import com.git.inter.CgLibProxyMethod;
import com.git.inter.ProxyMethod;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理工具类，直接生成代理对象
 * @author authorZhao
 */
public class ProxyUtil {

    private static final Logger logger = LoggerFactory.getLogger(ProxyUtil.class);

    public static Object getObject(Class clazz) {
        return getObject(clazz,null);
    }

    public static Object getObject(Class clazz,ProxyMethod ProxyMethod) {
        if(clazz.isInterface()){
            logger.info("正在为{}创建jdk动态代理对象",clazz.getName());
            return jdkProxy(clazz,ProxyMethod);
        }else{
            logger.info("正在为{}创建cgLib动态代理对象",clazz.getName());
            return cgLibProxy(clazz,ProxyMethod);
        }

    }

    private static Object cgLibProxy(Class clazz, ProxyMethod proxyMethod) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        MyMethodInterceptor myMethodInterceptor = null;
        if(proxyMethod==null){
            myMethodInterceptor = new MyMethodInterceptor();
        }else{
            if(proxyMethod instanceof CgLibProxyMethod)myMethodInterceptor = new MyMethodInterceptor((CgLibProxyMethod)proxyMethod);

        }
        enhancer.setCallback(myMethodInterceptor);
        Object proxyObj = enhancer.create();
        return proxyObj;
    }

    private static Object jdkProxy(Class clazz, ProxyMethod ProxyMethod) {
        // 获取接口名
        String interfaceName = clazz.getName();

        ClassLoader classLoader = clazz.getClassLoader();
        Class[] interfaces = { clazz };

        MyInvocationHandler myInvocationHandler;
        if(ProxyMethod==null){
            myInvocationHandler = new MyInvocationHandler();
        }else{
            myInvocationHandler = new MyInvocationHandler(ProxyMethod);
        }
        //创建代理对象
        Object object = null;
        try {
            object = Proxy.newProxyInstance(classLoader, interfaces, myInvocationHandler);
        }catch (Exception e){
            logger.error("获取不到{}的代理对象",interfaceName);
            e.printStackTrace();
        }
        return object;
    }

    private static class MyInvocationHandler implements InvocationHandler {

        private ProxyMethod ProxyMethod;

        public MyInvocationHandler(ProxyMethod<Object,Method,Object[],Object> ProxyMethod){
            this.ProxyMethod = ProxyMethod;
        }

        public MyInvocationHandler(){
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this,args);
            }
            return ProxyMethod.apply(proxy,method,args);
        }

    }

    private static class MyMethodInterceptor implements MethodInterceptor {

        /**
         * Object：cglib生成的代理对象
         * Method：被代理对象的方法
         * Object[]：方法参数
         * MethodProxy：代理对象的方法
         */
        private CgLibProxyMethod cgLibProxyMethod;

        public MyMethodInterceptor( ) {
        }

        public MyMethodInterceptor(CgLibProxyMethod<Object,Method,Object[],MethodProxy,Object> cgLibProxyMethod) {
            this.cgLibProxyMethod = cgLibProxyMethod;
        }

        @Override
        public Object intercept(Object proxy, Method method, Object[] args,
                                MethodProxy methodProxy) throws Throwable {
            if(this.cgLibProxyMethod!=null)return cgLibProxyMethod.apply(proxy,method,args,methodProxy);
            return methodProxy.invoke(proxy,args);
        }
    }

}
