package com.itsymion.controller.utils;

import com.sun.net.httpserver.Authenticator;
import lombok.Data;


@Data
public class R
{

    private Integer code;
//    private Boolean flag;
    private Object data;
    private Boolean ok;
    private String message;

    public R(Integer code)
    {
        this.code = code;

    }
//    public R(Boolean flag)
//    {
//        this.flag = flag;
//    }
    public R(Integer code,Object data)
    {
        this.code = code;
        this.data = data;
    }
    public R(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }
    public R(Object data)
    {
        this.data = data;
    }
    public R(Integer code,Boolean ok,Object data,String message)
    {
        this.code = code;
        this.data = data;
        this.ok = ok;
        this.message = message;
    }
//    public R(Boolean flag,Object data)
//    {
//        this.flag = flag;
//        this.data = data;
//    }

//    public R(String msg)
//    {
////        this.flag = false;
//        this.msg = msg;
//    }

}
