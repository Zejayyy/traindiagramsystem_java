package com.itsymion.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsymion.dao.ISectionDao;
import com.itsymion.domain.Section;
import com.itsymion.service.ISectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class SectionServiceImpl extends ServiceImpl<ISectionDao, Section> implements ISectionService
{
    @Autowired
    private ISectionDao sectionDao;

    @Override
    public List<Section> selectByRouteId(Integer startStationId,Integer endStationId, Integer lineId, Integer trackId)
    {
        return sectionDao.getByRouteId(startStationId,endStationId,lineId,trackId);
    }
}
