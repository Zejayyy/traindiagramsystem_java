package com.itsymion.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsymion.controller.DataForms.RulerData;
import com.itsymion.controller.DataForms.RulerData1;
import com.itsymion.controller.DataForms.RulerSchemeData;
import com.itsymion.dao.*;
import com.itsymion.domain.Route;
import com.itsymion.domain.RulerScheme;
import com.itsymion.domain.StationRuler;
import com.itsymion.service.IRSchemeService;
import com.itsymion.service.ISectionRulerService;
import com.itsymion.service.IStationRulerService;
import com.itsymion.service.ITimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RSchemeServiceImpl extends ServiceImpl<IRSchemeDao, RulerScheme> implements IRSchemeService
{


    private final IRSchemeDao schemeDao;
    private final IRouteDao routeDao;
    private final ISectionRulerDao sectionRulerDao;
    private final IStationRulerDao stationRulerDao;
    private final ITimeDao timeDao;
    private final ISectionRulerService sectionRulerService;
    private final IStationRulerService stationRulerService;
    public RSchemeServiceImpl(IRSchemeDao schemeDao, IRouteDao routeDao, ISectionRulerDao sectionRulerDao, IStationRulerDao stationRulerDao, ITimeDao timeDao, ISectionRulerService sectionRulerService, ITimeService timeService, IStationRulerService stationRulerService)
    {
        this.schemeDao = schemeDao;
        this.routeDao = routeDao;
        this.sectionRulerDao = sectionRulerDao;
        this.stationRulerDao = stationRulerDao;
        this.timeDao = timeDao;
        this.sectionRulerService = sectionRulerService;

        this.stationRulerService = stationRulerService;
    }

    @Override
    public RulerSchemeData GetRulerSchemes(Integer lineId)
    {
        QueryWrapper<RulerScheme> query = new QueryWrapper<>();
        query.eq("line_id",lineId);
        RulerSchemeData data = new RulerSchemeData();
        data.setRecords(schemeDao.selectList(query));


        return data;
    }

    @Override
    public RulerData SelectRulerInfos(Integer rulerId, Integer lineId, Integer routeId)
    {
        RulerData data = new RulerData();

        QueryWrapper<Route> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("route_id",routeId)
                .eq("line_id",lineId);
        int startStationId = routeDao.selectOne(queryWrapper).getStartStationId();
        int endStationId = routeDao.selectOne(queryWrapper).getEndStationId();

        data.setSectionRulerList(sectionRulerDao.GetTravelTimeList(startStationId, endStationId, rulerId, lineId));
        data.setDwellRulerList(stationRulerDao.selectDwellList(rulerId,startStationId,endStationId,lineId));
        data.setTurnRulerList(stationRulerDao.selectTurnList(startStationId, endStationId, rulerId,lineId));
        data.setTimeList(timeDao.GetInterval(rulerId,lineId));
        return data;
    }

    @Override
    public boolean UpdateNewRuler(Integer lineId, RulerData1 rulerData)
    {
        int maxIndex = 0;

        for (int i = 0;i < schemeDao.selectList(null).size();i++)
        {
            if (schemeDao.selectList(null).get(i).getRulerId() > maxIndex)
                maxIndex = schemeDao.selectList(null).get(i).getRulerId();
        }
        //保存标尺方案
        RulerScheme rulerScheme = new RulerScheme();
        rulerScheme.setRulerId(maxIndex + 1);
        rulerScheme.setRulerName(rulerData.getRulerName());
        rulerScheme.setLineId(lineId);
        schemeDao.insert(rulerScheme);

        //保存区间运行标尺
        for (int i = 0;i < rulerData.getSectionRulerList().size(); i++)
        {
            rulerData.getSectionRulerList().get(i).setRulerId(maxIndex + 1);
            rulerData.getSectionRulerList().get(i).setLineId(lineId);
        }
        sectionRulerService.saveBatch(rulerData.getSectionRulerList());
        System.out.println("获取到的区间运行时间的区间数量为" + rulerData.getSectionRulerList().size());
        //保存停站标尺
        for (int i = 0;i < rulerData.getDwellRulerList().size();i++)
        {
            rulerData.getDwellRulerList().get(i).setRulerId(maxIndex + 1);
            rulerData.getDwellRulerList().get(i).setLineId(lineId);

        }
        stationRulerService.saveBatch(rulerData.getDwellRulerList());
        System.out.println("获取到的停站时间的数量为" + rulerData.getDwellRulerList().size());
        //保存折返标尺
        int success = 0;
        for (int i = 0;i < rulerData.getTurnRulerList().size();i++)
        {

            QueryWrapper<StationRuler> query = new QueryWrapper<>();
            query.eq("station_id",rulerData.getTurnRulerList().get(i).getStationId());
            stationRulerService.update(rulerData.getTurnRulerList().get(i),query);
            success++;
        }
        System.out.println("获得到的折返标尺数量为" + rulerData.getTurnRulerList().size());
        //保存峰段间隔
        int success_1 = 0;
        for (int i = 0;i < rulerData.getTimeList().size();i++)
        {
            rulerData.getTimeList().get(i).setRulerId(maxIndex + 1);
            rulerData.getTimeList().get(i).setLineId(lineId);
            timeDao.insert(rulerData.getTimeList().get(i));
            success_1++;
        }
//        timeService.saveBatch(rulerData.getTimeList());
        System.out.println("获取到的峰段数量为" + rulerData.getTimeList().size());

        return success_1 == rulerData.getTimeList().size();
    }
}
