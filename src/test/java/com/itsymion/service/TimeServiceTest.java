package com.itsymion.service;


import com.itsymion.domain.Time;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TimeServiceTest
{


    @Autowired
    private ITimeService timeService;


    @Test
    void testGetAll()
    {

        System.out.println(timeService.list(null));
    }


}
