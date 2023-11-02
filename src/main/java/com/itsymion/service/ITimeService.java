package com.itsymion.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itsymion.controller.DataForms.TimeData;
import com.itsymion.domain.Time;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ITimeService extends IService<Time>
{
//    boolean modify(Integer startStationId,Integer endStationId,Time time,Integer lineId);

    TimeData GetIntervalList( Integer rulerId, Integer lineId);

    boolean SaveIntervalList(Integer rulerId,Integer lineId,List<Time> timeList);
}
