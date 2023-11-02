package com.itsymion.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsymion.controller.DataForms.RouteTrackData;
import com.itsymion.controller.DataForms.SectionData;
import com.itsymion.dao.IRouteDao;
import com.itsymion.dao.ISectionDao;
import com.itsymion.dao.ITrackOptionDao;
import com.itsymion.dao.ITrackSchemeDao;
import com.itsymion.domain.Route;
import com.itsymion.domain.TrackOption;
import com.itsymion.domain.TrackScheme;
import com.itsymion.service.IRouteService;
import com.itsymion.service.ITrackSchemeService;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl extends ServiceImpl<IRouteDao, Route> implements IRouteService
{

    private final IRouteDao routeDao;
    private final ISectionDao sectionDao;
    private final ITrackOptionDao trackOptionDao;
    private final ITrackSchemeDao trackSchemeDao;
    public RouteServiceImpl(IRouteDao routeDao, ISectionDao sectionDao, ITrackOptionDao trackOptionDao, ITrackSchemeDao trackSchemeDao)
    {
        this.routeDao = routeDao;
        this.sectionDao = sectionDao;
        this.trackOptionDao = trackOptionDao;
        this.trackSchemeDao = trackSchemeDao;
    }

    @Override
    public RouteTrackData SelectAllRoutes(Integer lineId)
    {

        RouteTrackData data = new RouteTrackData();
        QueryWrapper<Route> query = new QueryWrapper<>();
        query.eq("line_id",lineId);
        data.setRouteList(routeDao.selectList(query));

        QueryWrapper<TrackScheme> query_1 = new QueryWrapper<>();
        query_1.eq("line_id",lineId);
        data.setTrackSchemes(trackSchemeDao.selectList(query_1));
        return data;
    }

    @Override
    public boolean UpdateNewRoute(Integer lineId, Route route)
    {
        int maxIndex = 0;
        for (int i = 0;i < routeDao.selectList(null).size();i++)
        {
            if (routeDao.selectList(null).get(i).getRouteId() > maxIndex)
                maxIndex = routeDao.selectList(null).get(i).getRouteId();

        }

        route.setEndStationId(route.getEndStationId());
        route.setRouteId(maxIndex + 1);
        route.setLineId(lineId);
        return routeDao.insert(route) > 0;
    }

    @Override
    public SectionData SelectInformation(Integer lineId,Integer routeId,Integer trackId)
    {
        SectionData data = new SectionData();


        QueryWrapper<Route> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("route_id",routeId)
                .eq("line_id",lineId);
        int startStationId = routeDao.selectOne(queryWrapper).getStartStationId();
        int endStationId = routeDao.selectOne(queryWrapper).getEndStationId();
//        System.out.println("=======================================");
//        System.out.println(startStationId);
//        System.out.println(endStationId);
//        System.out.println("=======================================");

        data.setRecords(sectionDao.getByRouteId(startStationId, endStationId,lineId,trackId));

        for (int i = 0; i < data.getRecords().size(); i++)
        {
            QueryWrapper<TrackOption> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("section_id", data.getRecords().get(i).getSectionId())
                            .eq("line_id",lineId);
                data.getRecords().get(i).setOptions(trackOptionDao.selectList(queryWrapper1));

        }

        return data;
    }


//    @Override
//    public Integer getIdByName(String startStation, String endStation,Integer lineId)
//    {
//        return routeDao.selectIdByName(startStation, endStation,lineId);
//    }


}
