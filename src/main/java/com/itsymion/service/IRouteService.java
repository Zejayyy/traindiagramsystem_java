package com.itsymion.service;


import com.baomidou.mybatisplus.extension.service.IService;

import com.itsymion.controller.DataForms.RouteTrackData;
import com.itsymion.controller.DataForms.SectionData;
import com.itsymion.domain.Route;

public interface IRouteService extends IService<Route>
{
//    Integer getIdByName(String startStation,String endStation);


    RouteTrackData SelectAllRoutes(Integer lineId);

    boolean UpdateNewRoute(Integer lineId,Route route);

    SectionData SelectInformation(Integer lineId,Integer routeId,Integer trackId);
}
