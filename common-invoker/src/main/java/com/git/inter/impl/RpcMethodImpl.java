package com.git.inter.impl;

import com.alibaba.fastjson.JSON;
import com.git.clazz.AnnotationUtil;
import com.git.inter.CgLibProxyMethod;
import com.git.inter.ProxyMethod;
import com.git.invoker.HttpMapping;
import com.git.other.ResultUtils;
import com.git.other.StringUtils;
import com.git.spring.anno.RpcClient;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author authorZhao
 * @date 2019/12/20
 */
@Slf4j
public class RpcMethodImpl {


    public static ProxyMethod dubboRemote(){
        CgLibProxyMethod<Object, Method,Object[], MethodProxy,Object>  cgLibProxyMethod =
                (object1,methods,methodProxy,object2)->{
                    return  null;
                };
        return cgLibProxyMethod;

    }

    public static ProxyMethod<Object, Method,Object[],Object> httpRemote() {
        ProxyMethod<Object, Method,Object[],Object>  cgLibProxyMethod =
                (proxy,method,args)->{
                    //1.决定请求方式

                    String methodName = method.getName();
                    String url = "";

                    //2.得到请求路径
                    RpcClient annotationImpl = AnnotationUtil.getAnnotationImpl(proxy.getClass(), RpcClient.class);
                    if(annotationImpl!=null)url = annotationImpl.URLPre();

                    HttpMapping requestMapping = getRequestMapping(method);
                    String urlFix = requestMapping.getValue();
                    if(StringUtils.isBlank(urlFix)){
                        url = url+"/"+methodName;
                    }else{
                        url = url +urlFix;
                    }

                    return doHttp(url,method,args, requestMapping.getHttpMethod());
                };
        return cgLibProxyMethod;
    }

    private static Object doHttp(String url, Method method, Object[] args, HttpMethod httpMethod) {

        //1.restTemplate构建
        RestTemplate restTemplate = new RestTemplate();
        //2.请求头与请求类型

        HttpMapping httpMapping = getRequestMapping(method);

        //1.获得请求头 http请求头
        HttpHeaders headers = getHeader();

        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
        //2.设置consumer
        String[] consumes = httpMapping.getConsumes();
        if(consumes!=null&&consumes.length>0)headers.setContentType(MediaType.parseMediaType(consumes[0]));
        String[] produces = httpMapping.getProduces();
        if(produces!=null&&produces.length>0)mediaTypeList.add(MediaType.parseMediaType(produces[0]));
        headers.setAccept(mediaTypeList);
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        Map<String, Object> map = new HashMap<>();
        //支持三个注解，不要乱用
        //1.PathVariable,将带有这个注解的放在url上面

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if(parameter==null)continue;
            String paramname = parameter.getName();
            PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
            if(pathVariable!=null){
                //为空会报错
                url = url +"/"+args[i].toString();
            }
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            if(requestParam!=null){
                String name = requestParam.value();
                paramname = StringUtils.isNotBlank(name)?name:paramname;
            }
            if(args[i]!=null){
                form.add(paramname,(args[i]));
            }
        }

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
            if(requestBody!=null){
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                //带有requestBody时候全体当做一个参数打包成json
                String json = JSON.toJSONString(form);
                log.error("正在发送json形式的请求，请求数据为:{}",json);
                HttpEntity<String> httpEntity = new HttpEntity<>(json, headers);
                return httpEntity;
            }
        }

        //2.RequestBody


        //3.PathVariable

        //RequestParam，
        //RequestBody,
        //PathVariable


        //3.设置参数
        //普通post
        //json
        //headers.setContentType(MediaType.APPLICATION_JSON_UTF8); // 请求头设置属性
        //headers.setContentType(MediaType.parseMediaType("multipart/form-data; charset=UTF-8"));
        //
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(form, headers);






        //3.参数解析
        //4.结果返回
        Class<?> returnType = method.getReturnType();


        //后台接收的地址
        //String url = "http://localhost:8092/process/saveProcess";

        log.info("正发起http请求，url={}，请求参数={}",url,httpEntity.getBody().toString());
        //restTemplate.postForEntity()
        ResponseEntity result = null;
        try {
            result = restTemplate.exchange(url, httpMethod, httpEntity, returnType);
        }catch (Exception e){
            result = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
            log.info("http请求结果为{}", JSON.toJSONString(result.getBody()));
            return ResultUtils.buildSucessObject(result.getBody());
        }
        log.info("http请求结果为{}", JSON.toJSONString(result.getBody()));
        return result.getBody();
    }


/*    private static Object doGet(String url, Method method, Object[] args, HttpMethod post) {

        //1.restTemplate构建
        RestTemplate restTemplate = new RestTemplate();
        //2.请求头与请求类型
        HttpEntity<MultiValueMap<String, Object>> httpEntity = getHttpEntity(url,method,args);

        //3.参数解析

        //4.结果返回
        Class<?> returnType = method.getReturnType();


        //后台接收的地址
        //String url = "http://localhost:8092/process/saveProcess";

        log.info("正发起http请求，url={}，请求参数={}",url,httpEntity.getBody().toString());
        //restTemplate.postForEntity()
        ResponseEntity result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, returnType);
        log.info("http请求结果为{}", JSON.toJSONString(result.getBody()));
        return result.getBody();

    }*/

    /*private static HttpEntity getHttpEntity(String url, Method method, Object[] args) {

    }*/



    /**
     * 请求头
     * @return
     */
    private static HttpHeaders getHeader() {
        //默认简体中文
        HttpHeaders headers = new HttpHeaders(CollectionUtils.toMultiValueMap(new LinkedCaseInsensitiveMap(8, Locale.SIMPLIFIED_CHINESE)));

        //默认utf-8编码
        Charset charset = Charset.forName("UTF-8");
        List<Charset> characters = new ArrayList<>();
        characters.add(charset);
        headers.setAcceptCharset(characters);
        //
        return headers;
    }

    /**
     * RequestMapping简单解析
     * @param method
     * @return
     */
    private static HttpMapping getRequestMapping(Method method) {
        HttpMapping httpMapping = new HttpMapping();
        RequestMapping requestMapping = AnnotationUtils.getAnnotation(method, RequestMapping.class);
        PostMapping postMapping = AnnotationUtils.getAnnotation(method, PostMapping.class);
        GetMapping getMapping = AnnotationUtils.getAnnotation(method, GetMapping.class);
        if(postMapping!=null&&getMapping!=null)throw new RuntimeException("不能同时使用postMapping和getMapping注解");
        if(requestMapping==null)return null;

        RequestMethod[] requestMethods = requestMapping.method();
        if(requestMethods!=null&&requestMethods.length>0){
            if(httpMapping.contaionsMethod(requestMethods,RequestMethod.GET)){
                httpMapping.setRequestMethod(RequestMethod.GET);
                httpMapping.setHttpMethod(HttpMethod.GET);
            }
        }
        String[] value = requestMapping.value();
        if(value==null||value.length<=0)value=requestMapping.path();
        httpMapping.setConsumes(requestMapping.consumes());
        httpMapping.setProduces(requestMapping.produces());
        httpMapping.setHeaders(requestMapping.headers());

        if(postMapping!=null){
            if(value==null||value.length<=0)value=postMapping.value();
            if(value==null||value.length<=0)value=postMapping.path();
            httpMapping.setConsumes(postMapping.consumes());
            httpMapping.setProduces(postMapping.produces());
            httpMapping.setHeaders(postMapping.headers());
        }
        if(getMapping!=null){
            if(value==null||value.length<=0)value=getMapping.value();
            if(value==null||value.length<=0)value=getMapping.path();
            httpMapping.setConsumes(getMapping.consumes());
            httpMapping.setProduces(getMapping.produces());
            httpMapping.setHeaders(getMapping.headers());
        }

        if(value!=null&&value.length>0)httpMapping.setValue(value[0]);
        return httpMapping;
    }


}
