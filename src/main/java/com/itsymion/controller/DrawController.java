package com.itsymion.controller;


import com.itsymion.controller.utils.R;
import com.itsymion.service.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping( "/draws")
public class DrawController
{

    private final ILineService lineService;

    public DrawController(ILineService lineService)
    {
        this.lineService = lineService;
    }


    /**
     * 画图
     */
    @GetMapping("diagram/{lineId}/{routeId}/{rulerId}")
    public R DrawDiagram(@PathVariable Integer routeId,@PathVariable Integer rulerId,@PathVariable Integer lineId) throws IOException, ClassNotFoundException
    {

        return new R(200,true,lineService.draw(routeId,rulerId,lineId),"成功");
    }


}
