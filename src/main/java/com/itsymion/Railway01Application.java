package com.itsymion;

import com.itsymion.controller.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(SpringUtil.class)//让自定义的工具类自动导入
@SpringBootApplication
public class Railway01Application
{

    public static void main(String[] args)
    {
        SpringApplication.run(Railway01Application.class, args);

    }

}
