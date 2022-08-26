package com.ypf.ccneed.vo;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: ccneed
 * @author: yanpengfan
 * @create: 2022-07-28 10:09
 **/
public class ResponseVo {

    public static  String failed( String code,String Msg){
        Map map = new HashMap<>();
        map.put("msg",Msg);
        map.put("code",code);
        return JSON.toJSONString(map);
    }
    public static  String success( String code,String Msg){
        Map map = new HashMap<>();
        map.put("msg",Msg);
        map.put("code",code);
        return JSON.toJSONString(map);
    }


    public static  String dataReturn( String code,Object Msg){
        Map map = new HashMap<>();
        map.put("msg",Msg);
        map.put("code",code);
        return JSON.toJSONString(map);
    }
}
