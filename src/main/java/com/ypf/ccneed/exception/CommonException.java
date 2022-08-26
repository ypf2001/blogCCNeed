package com.ypf.ccneed.exception;

/**
 * @program: ccneed
 * @author: yanpengfan
 * @create: 2022-07-28 10:14
 **/
public class CommonException extends RuntimeException {
    private String code;
    private String msg;

    public CommonException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
