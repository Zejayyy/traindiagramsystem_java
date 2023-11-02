package com.itsymion.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itsymion.controller.DataForms.StationData;
import com.itsymion.domain.Station;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface IStationService extends IService<Station>
{

    Boolean modify(Integer id,Station station,Integer lineId);

    Boolean insertBeforeStation(Integer id,Station station,Integer lineId);

    Boolean insertAfterStation(Integer id,Station station,Integer lineId);

    StationData pagination(Integer currentPage,Integer pageSize,Integer lineId);
    StationData getAllStation(Integer lineId);
//    Integer getIdByStationName(String stationName);
    Boolean DeleteStation(Integer stationId, Integer lineId);

}
