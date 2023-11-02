package com.itsymion.dao;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LineDaoTest
{

    @Autowired
    private ILineDao lineDao;

    @Test
    void test()
    {
        System.out.println(lineDao.selectList(null));

    }
}
