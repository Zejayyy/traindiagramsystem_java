package com.itsymion.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsymion.controller.DataForms.StationRulerData;
import com.itsymion.dao.IRouteDao;
import com.itsymion.dao.IStationRulerDao;
import com.itsymion.domain.Route;
import com.itsymion.domain.StationRuler;
import com.itsymion.service.IStationRulerService;
import org.springframework.stereotype.Service;

@Service
public class StationRulerServiceImpl extends ServiceImpl<IStationRulerDao, StationRuler> implements IStationRulerService
{

    StationRulerData data;
    private final IStationRulerDao stationRulerDao;
    private final IRouteDao routeDao;
    public StationRulerServiceImpl(IStationRulerDao stationRulerDao, IRouteDao routeDao)
    {
        this.stationRulerDao = stationRulerDao;
        this.routeDao = routeDao;
    }

    @Override
    public StationRulerData GetDwellTimeList(Integer rulerId, Integer routeId, Integer lineId)
    {
         data = new StationRulerData();

        QueryWrapper<Route> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("route_id",routeId)
                .eq("line_id",lineId);
        int startStationId = routeDao.selectOne(queryWrapper).getStartStationId();
        int endStationId = routeDao.selectOne(queryWrapper).getEndStationId();
        data.setRecords(stationRulerDao.selectDwellList(rulerId,startStationId,endStationId,lineId));

        return data;
    }

    @Override
    public StationRulerData GetTurnTimeList(Integer rulerId,Integer routeId,Integer lineId)
    {
        data = new StationRulerData();

        QueryWrapper<Route> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("route_id",routeId)
                .eq("line_id",lineId);
        int startStationId = routeDao.selectOne(queryWrapper).getStartStationId();
        int endStationId = routeDao.selectOne(queryWrapper).getEndStationId();
        data.setRecords(stationRulerDao.selectTurnList(startStationId, endStationId, rulerId,lineId));
        return data;
    }

    @Override
    public Boolean updateDwellTime(StationRulerData data, Integer lineId, Integer rulerId)
    {
        int success = 0;

        for (int i = 0;i < data.getRecords().size();i++)
        {
            QueryWrapper<StationRuler> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("station_id",data.getRecords().get(i).getStationId())
                    .eq("ruler_id",rulerId)
                    .eq("line_id",lineId);
            stationRulerDao.update(data.getRecords().get(i),queryWrapper);
            success++;
        }


        return success != 0 && success == data.getRecords().size();
    }

    @Override
    public Boolean updateTurnTime(StationRulerData data, Integer lineId, Integer rulerId)
    {

        int success = 0;
        for (int i = 0;i < data.getRecords().size();i++)
        {
            QueryWrapper<StationRuler> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("station_id",data.getRecords().get(i).getStationId())
                    .eq("ruler_id",rulerId)
                    .eq("line_id",lineId);
            stationRulerDao.update(data.getRecords().get(i),queryWrapper);
            success++;
        }
        return success != 0 && success == data.getRecords().size();
    }
}
