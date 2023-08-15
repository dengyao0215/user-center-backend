package com.yupi.usercenter.common;

/***
 * @title ResultUtils
 * @description TODO 返回工具类
 * @author Skadhi
 * @version 1.0.0
 * @create 2023-02-23 20:50
 **/
public class ResultUtils {

    /**
     * 成功
     * @param data 数据
     * @param <T> 数据类型
     * @return 封装对象
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0,data, "ok");
    }

    /**
     * 失败
     * @param errorCode 异常对象
     * @return 封装对象
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     * @param code
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse(code, null, message, description);
    }

    /**
     * 失败
     * @param errorCode
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse(errorCode.getCode(), null, message, description);
    }

    /**
     * 失败
     * @param errorCode
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse(errorCode.getCode(), null, errorCode.getMessage(), description);
    }

}
