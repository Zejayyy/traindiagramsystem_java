package com.itsymion.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itsymion.dao.ISectionDao;
import com.itsymion.domain.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StationServiceTestCase
{

    @Autowired
    private IStationService stationService;



    @Test
    void getAll()
    {
//        System.out.println(stationService.list());


    }



}
