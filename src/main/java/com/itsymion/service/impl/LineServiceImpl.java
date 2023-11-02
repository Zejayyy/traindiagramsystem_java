package com.itsymion.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsymion.Algorithm.SA1;
import com.itsymion.Algorithm.SA3;
import com.itsymion.controller.DataForms.DrawData;
import com.itsymion.dao.*;
import com.itsymion.domain.Line;
import com.itsymion.domain.Route;
import com.itsymion.service.ILineService;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class LineServiceImpl extends ServiceImpl<ILineDao, Line> implements ILineService
{

    final IRouteDao routeDao;
    final IStationRulerDao stationRulerDao;
    final ISectionRulerDao sectionRulerDao;
    public LineServiceImpl(IRouteDao routeDao, IStationRulerDao stationRulerDao, ISectionRulerDao sectionRulerDao)
    {
        this.routeDao = routeDao;
        this.stationRulerDao = stationRulerDao;

        this.sectionRulerDao = sectionRulerDao;
    }

    @Override
    public DrawData draw(Integer routeId,Integer rulerId, Integer lineId) throws IOException, ClassNotFoundException
    {
        QueryWrapper<Route> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("route_id",routeId)
                .eq("line_id",lineId);
        int startStationId = routeDao.selectOne(queryWrapper).getStartStationId();
        int endStationId = routeDao.selectOne(queryWrapper).getEndStationId();
        DrawData data = new DrawData();
        System.out.println("77777777777777777777777");
        switch (lineId)
        {
            case 1->
                    {
                        SA1 sa = new SA1( rulerId, routeId,lineId);
                        data.setUpTrips(sa.getCurUpTrips());
                        data.setDownTrips(sa.getCurDownTrips());
                        data.setOptimalPath(sa.getOptimalPath());
                        data.setEmptyTrips(sa.getCurEmptyTrips());
                        data.setOnLineAndOutLine(sa.getCurOnLineAndOutLine());
                        data.setStationTime(stationRulerDao.selectDwellList(rulerId,startStationId,endStationId,lineId));
                        data.setSectionRunningTime(sectionRulerDao.GetTravelTimeList(startStationId,endStationId,rulerId,lineId));
                        data.setIfSolved(sa.bestSolved);
                    }
            case 3->
                    {
                        System.out.println("======");
                        System.out.println("到这里了");
                        System.out.println("======");

                        SA3 sa = new SA3( rulerId, routeId,lineId);
                        System.out.println("6666666666666666");
                        data.setUpTrips(sa.getCurUpTrips());
                        data.setDownTrips(sa.getCurDownTrips());
                        data.setOptimalPath(sa.getOptimalPath());
                        data.setEmptyTrips(sa.getCurEmptyTrips());
                        data.setOnLineAndOutLine(sa.getCurOnLineAndOutLine());
                        data.setStationTime(stationRulerDao.selectDwellList(rulerId,startStationId,endStationId,lineId));
                        data.setSectionRunningTime(sectionRulerDao.GetTravelTimeList(startStationId,endStationId,rulerId,lineId));
                        data.setIfSolved(sa.bestSolved);
                    }
        }


        return data;
    }
}
