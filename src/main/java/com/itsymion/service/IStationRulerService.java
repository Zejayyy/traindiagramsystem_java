package com.itsymion.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itsymion.controller.DataForms.StationRulerData;
import com.itsymion.domain.StationRuler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IStationRulerService extends IService<StationRuler>
{



    StationRulerData GetDwellTimeList(Integer rulerId, Integer routeId, Integer lineId);
    StationRulerData GetTurnTimeList(Integer rulerId,Integer routeIdId,Integer lineId);


    Boolean updateDwellTime(StationRulerData data,Integer lineId,Integer rulerId);
    Boolean updateTurnTime(StationRulerData data,Integer lineId,Integer rulerId);
}
