package com.itsymion.controller;



import com.itsymion.controller.utils.R;

import com.itsymion.domain.Station;
import com.itsymion.service.IStationService;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping( "/stations")
public class StationController
{
    private final IStationService stationService;

    public StationController(IStationService stationService)
    {
        this.stationService = stationService;
    }

    /**
     * 得到当前线路所有站台
     */
    @GetMapping("/all/{lineId}")
    public R  getAll(@PathVariable Integer lineId)
    {



        return new R(200,true,stationService.getAllStation(lineId),"成功");
    }

    /**
     * 分页展示
     */
    @GetMapping("/{lineId}/{currentPage}/{pageSize}")
    public R  selectPage(@PathVariable Integer currentPage,@PathVariable Integer pageSize,@PathVariable Integer lineId)
    {



        return new R(200,true,stationService.pagination(currentPage, pageSize, lineId),"成功");
    }


    /**
     * 站前加站
     */
    @PostMapping("/before/{lineId}/{stationId}")
    public R inBeforeStation(@PathVariable Integer stationId,@RequestBody Station station,@PathVariable Integer lineId)
    {


        if (stationService.insertBeforeStation(stationId,station,lineId))
        {
            return  new R(200,  "添加成功");
        }
        else
        {
            return  new R(30000,  "添加失败");
        }
    }


    /**
     * 站后加站
     */
    @PostMapping("after/{lineId}/{stationId}")
    public R inAfterStation(@PathVariable Integer stationId,@RequestBody Station station,@PathVariable Integer lineId)
    {


        if (stationService.insertAfterStation(stationId,station,lineId))
        {

            return  new R(200,  "添加成功");
        }
        else
        {

            return  new R(30000,  "添加失败");
        }
    }



    /**
     * 修改站台信息
     */
    @PutMapping("modify/{lineId}/{stationId}")
    public R update(@RequestBody Station station,@PathVariable Integer stationId,@PathVariable Integer lineId)
    {

        if (stationService.modify(stationId,station,lineId))
        {
            return  new R(200,  "修改成功");
        }
        else
        {
            return  new R(30000,  "修改失败");
        }

    }

    /**
     * 删除站台信息
     */
    @DeleteMapping("delete/{lineId}/{stationId}")
    public R delete(@PathVariable Integer stationId,@PathVariable Integer lineId)
    {

      if (stationService.DeleteStation(stationId, lineId))

        return new R(200,"删除成功");
      else
          return new R(30000,"删除失败");
    }


}
