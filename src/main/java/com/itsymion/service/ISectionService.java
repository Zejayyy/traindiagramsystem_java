package com.itsymion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itsymion.domain.Section;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ISectionService extends IService<Section>
{


    List<Section> selectByRouteId( Integer startStationId, Integer endStationId,Integer lineId, Integer trackId);
}
