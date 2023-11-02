package com.itsymion.controller;



import com.itsymion.controller.DataForms.RulerData;
import com.itsymion.controller.DataForms.RulerData1;
import com.itsymion.controller.utils.R;
import com.itsymion.domain.Route;
import com.itsymion.service.IRSchemeService;
import org.springframework.web.bind.annotation.*;



@CrossOrigin
@RestController
@RequestMapping("/RSchemes")
public class RSchemeController
{

    private final IRSchemeService schemeService;

    public RSchemeController(IRSchemeService schemeService)
    {
        this.schemeService = schemeService;
    }


    /**
     * 获得该线路的所有标尺
     */
    @GetMapping("/get/{lineId}")
    public R GetAllRulers(@PathVariable Integer lineId)
    {


        return new R(200,true,schemeService.GetRulerSchemes(lineId),"成功");
    }



    /**
     * 新增标尺
     */
    @PostMapping("/add/{lineId}")
    public R saveNewRulers(@PathVariable Integer lineId, @RequestBody RulerData1 rulerData)
    {


        if (schemeService.UpdateNewRuler(lineId,rulerData))
            return new R(200,"添加成功");
        else
            return new R(30000,"添加失败");
    }
    /*
     * 根据标尺id获得标尺所有内容
     */
    @GetMapping("/select/{lineId}/{routeId}/{rulerId}")
    public R selectRulerInfo(@PathVariable Integer rulerId,@PathVariable Integer lineId,@PathVariable Integer routeId)
    {

        return new R(200,true,schemeService.SelectRulerInfos(rulerId, lineId, routeId),"成功");
    }




}
