package com.itsymion.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsymion.domain.Route;
import com.itsymion.domain.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RouteServiceTest
{
    @Autowired
    private IRouteService routeService;

    @Autowired
    private IStationService stationService;
//    @Test
//    void getCurrentRoute()
//    {
//        QueryWrapper<Station> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByAsc("stationid").between("stationid",1,3);
//        System.out.println(stationService.list(queryWrapper));
//    }



}
