package com.itsymion.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itsymion.controller.DataForms.SectionRulerData;
import com.itsymion.domain.SectionRuler;

public interface ISectionRulerService extends IService<SectionRuler>
{

    SectionRulerData GetTravelTimeList(Integer routeId, Integer rulerId, Integer lineId);


//    List<SectionRuler> GetDwellTimeList(Integer rulerId);

//    List<SectionRuler> GetTurnTimeList(Integer rulerId);

    Boolean modify(SectionRulerData sectionRuler, Integer lineId, Integer rulerId);


//    List<SectionRuler> GetRulerList(Integer rulerId);

}
