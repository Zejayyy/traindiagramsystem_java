package com.itsymion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itsymion.controller.DataForms.DrawData;
import com.itsymion.domain.Line;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

public interface ILineService extends IService<Line>
{


    DrawData draw(Integer routeId, Integer rulerId,Integer lineId) throws IOException, ClassNotFoundException;

}
