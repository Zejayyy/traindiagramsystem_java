package com.itsymion.controller;



import com.itsymion.controller.utils.R;
import com.itsymion.controller.DataForms.TrackData;
import com.itsymion.domain.Route;

import com.itsymion.service.*;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/routes")
public class RouteController
{

    private final IRouteService routeService;
    private final ITrackService trackService;

    public RouteController(IRouteService routeService, ITrackService trackService)
    {
        this.routeService = routeService;
        this.trackService = trackService;
    }

//    /**
//     * 获得两个站台之间的所有站台
//     * @return
//     */
//    @GetMapping("/select")
//    public R GetCurrentRoute(@RequestBody )
//    {
//        QueryWrapper<Station> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByAsc("stationid").between("stationid",id1,id2);
//        return new R(200,stationService.list(queryWrapper));
//
//    }


//    @PostMapping("/select")
//    public R SelectInfo(@RequestBody Route route)
//    {
//
//        int routeid = routeService.getIdByName(route.getStartstation(),route.getEndstation());
//
////        int startstationid = stationService.getIdByStationName(route.getStartstation());
////        int endstationid = stationService.getIdByStationName(route.getEndstation()) - 1;
//        GenData data = new GenData();
//        data.records = sectionService.selectByRouteId(routeid);
//
//
//
//        return new R(200,true,data,"成功");
//    }
    /**
     * 获得该线路所有预设交路和股道方案
     */
    @GetMapping("/get/{lineId}")
    public R GetAllRoutes(@PathVariable Integer lineId)
    {



        return new R(200,true,routeService.SelectAllRoutes(lineId),"成功");
    }



    /**
     * 新增交路
     */
    @PostMapping("/add/{lineId}")
    public R saveNewRoutes(@PathVariable Integer lineId,@RequestBody Route route)
    {


        if (routeService.UpdateNewRoute(lineId,route))
            return new R(200,"添加成功");
        else
            return new R(30000,"添加失败");
    }

    /**
     * 根据交路id查询交路站台、股道方案
     *
     */
    @GetMapping("/select/{lineId}/{routeId}/{trackId}")
    public R selectInfo(@PathVariable Integer lineId,@PathVariable Integer routeId,@PathVariable Integer trackId)
    {


        return new R(200,true,routeService.SelectInformation(lineId,routeId,trackId),"成功");
    }
    /*
      获得股道方案
     */
//    @GetMapping("/select/{startStationId}/{endStationId}/{lineId}/{trackId}")
//    public R SelectInfo(@PathVariable Integer startStationId,@PathVariable Integer endStationId,@PathVariable Integer lineId,@PathVariable Integer trackId)
//    {
////        int routeId = GetRouteId(startStationId,endStationId,lineId);
//
//        GenData data = new GenData();
//
//        data.setRecords(sectionService.selectByRouteId(startStationId, endStationId,lineId, trackId));
//
//        for (int i = 0; i < data.getRecords().size(); i++)
//        {
//            QueryWrapper queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("section_id", data.getRecords().get(i).getSectionId());
//                data.getRecords().get(i).setOptions(trackOptionService.list(queryWrapper));
//
//        }
//
//
//
//        int num = 0;
//        for (int i = 0; i < data.getRecords().size(); i++)
//        {
//            if (data.getRecords().get(i).getRowType() == 0)
//            {
//                num = i + 1;
//            }
//            else
//            {
//                break;
//            }
//        }
//        String info = null;
//        for (int i = 0;i < num;i++)
//        {
//            if (i == 0)
//                data.setSumUpInfo("上行：" + "(" + data.getRecords().get(i).getStartStation() + ")" + data.getRecords().get(i).getPlan() + "(" + (data.getRecords().get(i).getEndStation()) + ")");
//            else
//            {
//                 info +=  "(" + data.getRecords().get(i).getStartStation() + ")" + data.getRecords().get(i).getPlan() + "(" + (data.getRecords().get(i).getEndStation()) + ")";
//            }
//        }
//        data.setSumUpInfo(info);
//        info = null;
//        for (int i = num; i < data.getRecords().size(); i++)
//        {
//            if (i == 0)
//                data.setSumDownInfo("下行：" + "(" + data.getRecords().get(i).getStartStation() + ")" + data.getRecords().get(i).getPlan() + "(" + (data.getRecords().get(i).getEndStation()) + ")");
//            else
//            {
//                 info +=  "(" + data.getRecords().get(i).getStartStation() + ")" + data.getRecords().get(i).getPlan() + "(" + (data.getRecords().get(i).getEndStation()) + ")";
//            }
//        }
//
//        data.setSumDownInfo(info);
//
//        return new R(200,true,data,"成功");
//    }


//    @PutMapping("modify")
//    public R updateInfo(@RequestBody RouteAndTrack routeAndTrack)
//    {
//        int routeid = routeService.getIdByName(routeAndTrack.getStartstation(),routeAndTrack.getEndstation());
//        Track track = new Track();
//        track.setPlan(routeAndTrack.getPlan());
//        boolean ifSuccess = trackService.modify(routeid,routeAndTrack.getSectionid(),routeAndTrack.getRowtype(),track);
//        if (ifSuccess)
//        {
//            return new R(200,"修改成功");
//        }
//        else
//        {
//            return  new R(30000,"修改失败");
//        }
//
//    }

    /**
     * 修改股道方案
     */
    @PostMapping("modify/{lineId}/{trackId}")
    public R updateInfo(@RequestBody TrackData trackData,@PathVariable Integer lineId,@PathVariable Integer trackId)
    {




        if (trackService.modify(trackData, lineId, trackId))
        {
            return new R(200,"全部修改成功");
        }
        else
        {
            return  new R(30000,"修改失败");
        }

    }
//    /**
//     * 新增交路
//     * @param id1
//     * @param id2
//     * @param route
//     * @return
//     */
//    @PostMapping("{id1}/{id2}")
//    public R SaveNewRoute(@PathVariable Integer id1, @PathVariable Integer id2, @RequestBody Route route)
//    {
//        route.setStartstation(id1);
//        route.setEndstation(id2);
//        boolean flag = routeService.save(route);
//        int code;
//        if (flag)
//        {
//            code = 200;
//            return  new R(code,  "添加成功");
//        }
//        else
//        {
//            code = 30000;
//            return  new R(code,  "添加失败");
//        }
//    }



}
