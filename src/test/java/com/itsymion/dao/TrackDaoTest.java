package com.itsymion.dao;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TrackDaoTest
{
    @Autowired
    private ITrackDao trackDao;

    @Test
    void testGetById()
    {

//        System.out.println(trackDao.selectBySectionId(1));
    }
}
