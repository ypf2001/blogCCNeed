package com.ypf.ccneed.config;

import com.alibaba.fastjson.JSON;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @program: ccneed
 * @author: yanpengfan
 * @create: 2022-07-29 19:07
 **/
@Component("selfKeyGenerate")
public class RedisSelfKeyGenerate implements KeyGenerator {
    @Override
    public Object generate(Object o, Method method, Object... objects) {
        return o.getClass().getSimpleName()+"#"+ "(" + JSON.toJSONString(objects) + ")";
    }
}
