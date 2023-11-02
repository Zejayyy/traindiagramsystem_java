package com.itsymion.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsymion.controller.DataForms.StationData;
import com.itsymion.dao.IStationDao;
import com.itsymion.domain.Station;
import com.itsymion.service.IStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl extends ServiceImpl<IStationDao, Station> implements IStationService
{
    @Autowired
    private IStationDao stationDao;

    //修改
    @Override
    public Boolean modify(Integer id,Station station,Integer lineId)
    {
        QueryWrapper<Station> queryWrapper = new QueryWrapper<Station>();
        queryWrapper.eq("station_id",id)
                .eq("line_id",lineId);
        return stationDao.update(station,queryWrapper) > 0;
    }

    //站前加站
    public Boolean insertBeforeStation(Integer id,Station station,Integer lineId)
    {
        stationDao.updateBeforeByStationId(id,lineId);

        station.setStationId(id);
        station.setLineId(lineId);
        return  stationDao.insert(station) > 0;
    }



    //站后加站
    @Override
    public Boolean insertAfterStation(Integer id, Station station,Integer lineId)
    {
        stationDao.updateAfterByStationId(id,lineId);
        station.setLineId(lineId);
        station.setStationId(id + 1);
        return stationDao.insert(station) > 0;
    }

    @Override
    public StationData pagination(Integer currentPage, Integer pageSize, Integer lineId)
    {
        StationData data = new StationData();
        int id1 = (currentPage - 1) * pageSize;
        QueryWrapper<Station> query = new QueryWrapper<>();
        query.eq("line_id",lineId);
        data.setRecords(stationDao.selectPageList(id1, pageSize, lineId));
        data.setTotal(stationDao.selectCount(query));
        data.setCurrent(currentPage);
        data.setSize(pageSize);
        data.setSearchCount(true);
        data.setPages(Math.toIntExact((data.getTotal() + pageSize - 1) / pageSize));


        return data;
    }

    @Override
    public StationData getAllStation(Integer lineId)
    {
        StationData data = new StationData();
        data.setRecords(stationDao.getAll(lineId));
        QueryWrapper<Station> query = new QueryWrapper<>();
        query.eq("line_id",lineId);
        data.setTotal(stationDao.selectCount(query));
        return data;
    }

    @Override
    public Boolean DeleteStation(Integer stationId, Integer lineId)
    {

        QueryWrapper<Station> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("station_id",stationId)
                .eq("line_id",lineId);
        return stationDao.delete(queryWrapper) > 0 && stationDao.updateAfterDelete(stationId, lineId);
    }

//    @Override
//    public Integer getIdByStationName(String stationName)
//    {
//        QueryWrapper<Station> query = new QueryWrapper<>();
//        query.eq("stationname",stationName);
//        return stationDao.selectOne(query).getStationId();
//    }


}
