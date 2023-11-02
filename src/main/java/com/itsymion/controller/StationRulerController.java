package com.itsymion.controller;



import com.itsymion.controller.DataForms.StationRulerData;
import com.itsymion.controller.utils.R;
import com.itsymion.service.IStationRulerService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/stationrulers")
public class StationRulerController
{

    private final IStationRulerService stationRulerService;
    public StationRulerController( IStationRulerService stationRulerService)
    {
        this.stationRulerService = stationRulerService;
    }

    /**
     * 获得停站时间
     */
    @GetMapping("dwell/{lineId}/{routeId}/{rulerId}")
    public R GetDwellTime(@PathVariable Integer routeId, @PathVariable Integer rulerId, @PathVariable Integer lineId)
    {

        return new R(200,true,stationRulerService.GetDwellTimeList(rulerId,routeId,lineId),"成功");

    }

    /**
     * 获得折返时间
     */
    @GetMapping("/turn/{lineId}/{routeId}/{rulerId}")
    public R GetTurnTime(@PathVariable Integer routeId, @PathVariable Integer rulerId, @PathVariable Integer lineId)
    {

        return new R(200,true,stationRulerService.GetTurnTimeList(rulerId,routeId,lineId),"成功");

    }

    /**
     * 修改停站时间
     */
    @PostMapping("/modify/dwell/{lineId}/{rulerId}")
    public R UpdateDwellTime(@RequestBody StationRulerData data, @PathVariable Integer lineId, @PathVariable Integer rulerId)
    {

        if (stationRulerService.updateDwellTime(data, lineId, rulerId))
        {

            return  new R(200,  "修改成功");
        }
        else
        {

            return  new R(30000,  "修改失败");
        }

    }

    /**
     * 修改折返标尺
     */
    @PostMapping("/modify/turn/{lineId}/{rulerId}")
    public R UpdateTurnTime(@RequestBody StationRulerData data,@PathVariable Integer lineId,@PathVariable Integer rulerId)
    {



        if (stationRulerService.updateTurnTime(data, lineId, rulerId))
        {

            return  new R(200,  "修改成功");
        }
        else
        {

            return  new R(30000,  "修改失败");
        }

    }



}
