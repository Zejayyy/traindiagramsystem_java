package com.itsymion.controller.utils;


import com.itsymion.controller.DataForms.LoginData;
import lombok.Data;

@Data
public class R1
{
    private Integer code;
    private String message;
    private Boolean success;
    LoginData data;

//    private Map<String, Object> data;


//    public R1(Integer code, String message, Boolean success, Map<String, Object> data)
//    {
//        this.data = data;
//        this.code = code;
//        this.message = message;
//        this.success = success;
//
//    }

    public R1(Integer code, String message, Boolean success, LoginData data)
    {
        this.data = data;

        this.code = code;
        this.message = message;
        this.success = success;

    }
}
