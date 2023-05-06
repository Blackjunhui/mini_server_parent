package com.black.utils.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 * @date 2023/4/19 15:06
 */
@Data
public class R<T> {

    private Boolean success;

    private Integer code;

    private String message;

    private T data;

    //成功静态方法
    public static <T> R<T> ok(){
        R<T> r = new R<T>();
        r.setSuccess(true);
        r.setCode(20000);
        r.setMessage("成功");
        return r;
    }

    public static <T> R<T> ok(T data){
        R<T> r = new R<T>();
        r.setSuccess(true);
        r.setCode(20000);
        r.setMessage("成功");
        r.setData(data);
        return r;
    }

    //失败静态方法
    public static <T> R<T> fail(){
        R<T> r = new R<T>();
        r.setSuccess(false);
        r.setCode(20001);
        r.setMessage("失败");
        return r;
    }

    public static <T> R<T> fail(String message){
        R<T> r = new R<T>();
        r.setSuccess(false);
        r.setCode(20001);
        r.setMessage(message);
        return r;
    }
}
