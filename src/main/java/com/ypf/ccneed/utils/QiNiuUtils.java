package com.ypf.ccneed.utils;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * @program: ccneed
 * @author: yanpengfan
 * @create: 2022-07-28 17:03
 **/
public class QiNiuUtils {
    final static String accessKey = "E6UbXTYy5O7GylbafNktW_ioNOnqMmwV-ZaBdwfI";
    final static String secretKey = "4egO9as32KpINzz-iij6yWmMfzdH-FpH6Ps2LtB3";
    final static String bucket = "can-can-need";

    public static String UploadToken() {
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        return upToken;
    }
}
