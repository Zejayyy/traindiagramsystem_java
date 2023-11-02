package com.itsymion.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_user")
public class User
{
    private Integer id;
    private String password;
    private String username;
    private String type;

}
