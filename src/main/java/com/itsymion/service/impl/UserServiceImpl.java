package com.itsymion.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsymion.controller.utils.R;
import com.itsymion.dao.IUserDao;
import com.itsymion.domain.User;
import com.itsymion.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<IUserDao, User> implements IUserService
{
    @Autowired
    private IUserDao userDao;

    @Override
    public String login(String username, String password)
    {
        return userDao.login(username,password);
    }
}
