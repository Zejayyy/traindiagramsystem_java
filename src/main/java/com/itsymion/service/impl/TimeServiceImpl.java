package com.itsymion.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsymion.controller.DataForms.TimeData;
import com.itsymion.dao.ITimeDao;
import com.itsymion.domain.Route;
import com.itsymion.domain.Time;
import com.itsymion.service.IRouteService;
import com.itsymion.service.ITimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeServiceImpl extends ServiceImpl<ITimeDao, Time> implements ITimeService
{

private final ITimeDao timeDao;




    public TimeServiceImpl(ITimeDao timeDao)
    {
        this.timeDao = timeDao;

    }
//    @Override
//    public boolean modify(Integer startstationid,Integer endstationid,Time time,Integer lineId)
//    {
////        data = new TimeData();
//        int id = endstationid - 1;
//        QueryWrapper<Route> query = new QueryWrapper<>();
//        query.eq("startstationid", startstationid)
//                .eq("endstationid",id)
//                .eq("lineid",lineId);
//        int routeId = routeService.getOne(query).getId();
//        QueryWrapper<Time> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("routeid",routeId)
//                .eq("rulerid",time.getRulerId())
//                .eq("lineid",lineId);
//
//        return timeDao.update(time,queryWrapper)> 0;
//    }


    @Override
    public TimeData GetIntervalList(Integer rulerId, Integer lineId)
    {
        TimeData timeData = new TimeData();

        timeData.setRecords(timeDao.GetInterval(rulerId,lineId));
        return timeData;
    }

    @Override
    public boolean SaveIntervalList(Integer rulerId, Integer lineId, List<Time> timeList)
    {

        QueryWrapper<Time> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("line_id",lineId)
                        .eq("ruler_id",rulerId);
        timeDao.delete(queryWrapper);
        System.out.println("删除后剩余应为0，实际为" + timeDao.selectCount(null));
        int success = 0;
        int tempId = 1;
        boolean ifChanged = false;
        for (int i = 0;i < timeList.size();i++)
        {
            if (timeList.get(i).getRowType() == 0 && !ifChanged)
            {
                tempId = 1;
                ifChanged = true;
            }
            timeList.get(i).setId(i + 1);
            timeList.get(i).setLineId(lineId);
            timeList.get(i).setRulerId(rulerId);
            timeList.get(i).setSortId(tempId);
            timeDao.insert(timeList.get(i));
            tempId++;

            success++;
        }

        return success != 0 && success == timeList.size();
    }
}
