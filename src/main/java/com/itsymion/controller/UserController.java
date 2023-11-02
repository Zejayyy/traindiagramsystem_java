package com.itsymion.controller;

import com.alibaba.druid.util.StringUtils;
import com.itsymion.controller.utils.R1;
import com.itsymion.controller.DataForms.LoginData;
import com.itsymion.domain.User;
import com.itsymion.service.IUserService;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping(value = "/users", method = RequestMethod.GET)
public class UserController
{

    private final IUserService iUserService;

    public UserController(IUserService iUserService)
    {
        this.iUserService = iUserService;
    }





    @PostMapping(value = "/login")
    public R1 login(@RequestBody User user)
    {
//        Map<String,Object> data = new HashMap<>();
//        data.put("token","sakdfnkasdkladlklasdjaslk165as4d132as56d4as65drqw85reqw43qfsadf1rweg453hfggsftn547nm65ey645b634");
//        System.out.println(data);
        LoginData data = new LoginData();
                data.setToken("doaisjdasdaskfdsfjldsjfsdsssdsaafsdklsdsfsf2321");
//        System.out.println("你输入的用户名为：" + user.getUsername());
//        System.out.println("你输入的密码为：" + user.getPassword());
        String type = iUserService.login(user.getUsername(), user.getPassword());
        if (StringUtils.isEmpty(user.getUsername()))
        {
            return new R1(30000,"用户名不许为空",false,data);

        }
        if (StringUtils.isEmpty(user.getPassword()))
        {
            return new R1(30000,"密码不许为空",false,data);
        }
        if (type == null)
        {
            return new R1(30000,"密码错误",false,data);
        }
        else
        {
            return new R1(200,"登录成功",true,data);
        }

    }




}
