package com.itsymion.controller;


import com.itsymion.controller.utils.R;
import com.itsymion.controller.DataForms.TimeData;

import com.itsymion.service.ITimeService;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/times")
public class TimeController
{

    private final ITimeService timeService;

    public TimeController(ITimeService timeService)
    {
        this.timeService = timeService;

    }

    /**
     * 获得所有时段
     */
    @GetMapping("get/{lineId}/{rulerId}")
    public R GetAll(@PathVariable Integer rulerId, @PathVariable Integer lineId)
    {

        return new R(200, true, timeService.GetIntervalList(rulerId, lineId), "成功");

    }

//    /*
//     *修改标尺
//     */
//    @PostMapping("modify")
//    public R save(@RequestBody TimeData data)
//    {
//        boolean flag = timeService.modify(data.getTime());
//        if (flag)
//        {
//
//            return  new R(200,  "修改成功");
//        }
//        else
//        {
//
//            return  new R(30000,  "修改失败");
//        }
//
//    }

    /**
     * 修改时段
     */
    @PostMapping("modify/{lineId}/{rulerId}")
    public R Save( @RequestBody TimeData timeData, @PathVariable Integer rulerId, @PathVariable Integer lineId)
    {


//        System.out.println("??????????????==================");
//        System.out.println(timeData.getRecords().size());
//        for (int i = 0;i < timeData.getRecords().size(); i++)
//        {
//            System.out.println("数据已获得");
//            System.out.println(timeData.getRecords().get(i).getStartTime());
//        }

//        if (timeService.SaveIntervalList(startStationId,endStationId,rulerId,lineId,timeData.getRecords()))
        if (timeService.SaveIntervalList(rulerId,lineId, timeData.getRecords()))
        {
            return new R(200, "修改成功");
        } else
        {
            return new R(30000, "修改失败");
        }


    }
}
