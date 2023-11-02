package com.itsymion.dao;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDaoTest
{

    @Autowired
    private IUserDao userDao;
    @Test
    void testLogin()
    {

        System.out.println(userDao.login("yuyutian","123456"));
    }

}
