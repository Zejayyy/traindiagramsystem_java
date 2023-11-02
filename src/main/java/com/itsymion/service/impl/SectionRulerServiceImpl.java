package com.itsymion.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.itsymion.controller.DataForms.SectionData;
import com.itsymion.controller.DataForms.SectionRulerData;
import com.itsymion.dao.IRouteDao;
import com.itsymion.dao.ISectionRulerDao;
import com.itsymion.domain.Route;
import com.itsymion.domain.SectionRuler;
import com.itsymion.service.ISectionRulerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class SectionRulerServiceImpl extends ServiceImpl<ISectionRulerDao, SectionRuler> implements ISectionRulerService
{
    private final ISectionRulerDao sectionRulerDao;
    private final IRouteDao routeDao;
    public SectionRulerServiceImpl(ISectionRulerDao sectionRulerDao, IRouteDao routeDao)
    {
        this.sectionRulerDao = sectionRulerDao;
        this.routeDao = routeDao;
    }

    @Override
    public SectionRulerData GetTravelTimeList(Integer routeId, Integer rulerId, Integer lineId)
    {
        SectionRulerData data = new SectionRulerData();


        QueryWrapper<Route> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("route_id",routeId)
                .eq("line_id",lineId);
        int startStationId = routeDao.selectOne(queryWrapper).getStartStationId();
        int endStationId = routeDao.selectOne(queryWrapper).getEndStationId();

        data.setRecords(sectionRulerDao.GetTravelTimeList(startStationId, endStationId, rulerId, lineId));
        return data;
    }

    @Override
    public Boolean modify(SectionRulerData rulerData, Integer lineId, Integer rulerId)
    {
        int success = 0;

        for (int i = 0; i < rulerData.getRecords().size(); i++)
        {
            QueryWrapper<SectionRuler> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ruler_id",rulerId)
                    .eq("section_id",rulerData.getRecords().get(i).getSectionId())
                    .eq("line_id",lineId);
            sectionRulerDao.update(rulerData.getRecords().get(i),queryWrapper);

            success++;
        }
        return success != 0 && success == rulerData.getRecords().size();
    }


//    @Override
//    public List<SectionRuler> GetDwellTimeList(Integer rulerId,Integer lineId)
//    {
//        return sectionRulerDao.GetDwellTimeList(rulerId,lineId);
//    }

//    @Override
//    public List<SectionRuler> GetTurnTimeList(Integer rulerId)
//    {
//        return sectionRulerDao.GetTurnTimeList(rulerId);
//    }




//    @Override
//    public List<SectionRuler> GetRulerList(Integer rulerId)
//    {
//        return sectionRulerDao.GetAllList(rulerId);
//    }
}
