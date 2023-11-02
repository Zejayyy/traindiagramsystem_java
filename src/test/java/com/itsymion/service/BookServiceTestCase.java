package com.itsymion.service;


import com.itsymion.domain.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookServiceTestCase
{



    @Autowired
    private IStationService istationService;

    @Test
    void testGetById()
    {
        System.out.println(istationService.getById(2));
    }
    @Test
    void testSave()
    {
//        Station station = new Station();
//        station.setIstransfer(1);
//        station.setStationname("包公园站");
//        station.setRemark("无");
//        station.setSenglishname("BGP");
//        station.setStationtype("普通站");
//        station.setIssingleordouble(0);
//        istationService.save(station);

    }

    @Test
    void testDelete()
    {
        istationService.removeById(3);
    }

    @Test
    void  testAddBeforeStation()
    {
//        Station station = new Station();
//        station.setIssingleordouble(0);
//        station.setIstransfer(0);
//        station.setStationname("测试站2前加站");
//        istationService.insertBeforeStation(2,station);
    }


    @Test
    void testAddAfterStation()
    {

//        Station station = new Station();
//        station.setIssingleordouble(0);
//        station.setIstransfer(0);
//        station.setStationname("测试站3后加站1");
//        istationService.insertAfterStation(10,station);


    }
}
