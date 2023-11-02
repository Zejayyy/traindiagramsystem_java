package com.itsymion.controller;


import com.itsymion.controller.utils.R;
import com.itsymion.domain.Line;
import com.itsymion.service.ILineService;

import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/lines")
public class LineController
{


    private final ILineService iLineService;

    public LineController(ILineService iLineService)
    {
        this.iLineService = iLineService;
    }

    /**
     * 返回所有线路
     */
    @GetMapping()
    public R GetAll()
    {

        return new R(200,true,iLineService.list(),"操作成功");
    }

    /**
     * 增加线路
     */
    @PostMapping("add")
    public  R addNewline(@RequestBody Line line)
    {

        if (iLineService.save(line))
        {
            return  new R(200,  "添加成功");
        }
        else
        {
            return  new R(30000,  "添加失败");
        }
    }


}
