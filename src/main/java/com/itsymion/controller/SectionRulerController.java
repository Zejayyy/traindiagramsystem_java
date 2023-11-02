package com.itsymion.controller;





import com.itsymion.controller.DataForms.SectionRulerData;
import com.itsymion.controller.utils.R;

import com.itsymion.service.ISectionRulerService;

import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/sectionrulers")
public class SectionRulerController
{
    private final ISectionRulerService sectionRulerService;

    public SectionRulerController(ISectionRulerService sectionRulerService)
    {
        this.sectionRulerService = sectionRulerService;

    }





    /**
     * 获得运行时间
     */
    @GetMapping("travel/{lineId}/{routeId}/{rulerId}")
    public R GetTravelTime(@PathVariable Integer rulerId,@PathVariable Integer lineId,@PathVariable Integer routeId)
    {


        return new R(200,true,sectionRulerService.GetTravelTimeList(routeId,rulerId,lineId),"成功");

    }



    /**
     * 修改运行时间
     */
    @PostMapping("modify/travel/{lineId}/{rulerId}")
    public R UpdateTravelTime(@RequestBody SectionRulerData data, @PathVariable Integer lineId, @PathVariable Integer rulerId)
    {



        if (sectionRulerService.modify(data,lineId,rulerId))
        {

            return  new R(200,  "修改成功");
        }
        else
        {

            return  new R(30000,  "修改失败");
        }

    }

}
