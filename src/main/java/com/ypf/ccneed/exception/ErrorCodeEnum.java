package com.ypf.ccneed.exception;

public enum ErrorCodeEnum {
    DELETE_USER_WRONG("10001","用户参数错误"),
    DELETE_USER_RUNTIME_WRONG("10002","用户删除过程错误"),
    UPDATE_IMG_ERROR("20001","图片保存错误"),
    SUBSCRIBE_TEXT_ERROR("30001","文章上传失败"),
    SUBSCRIBE_TEXT_SHOUT_ERROR("30002","文章为空或者过短,上传失败"),
    SUBSCRIBE_TEXT_IO_ERROR("30003","io流异常,上传失败"),
    FETCH_TEXT_IO_ERROR("30004","文章获取失败"),
    FETCH_COMMENT_IO_ERROR("40001","评论获取失败"),
    SEND_COMMENT_IO_ERROR("40002","评论发布失败"),
    ;

    private  String code ;
    private  String msg;
    ErrorCodeEnum(String code, String msg) {
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
