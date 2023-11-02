package com.itsymion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itsymion.controller.utils.R;
import com.itsymion.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

public interface IUserService extends IService<User>
{

    String login(String username,String password);


}
