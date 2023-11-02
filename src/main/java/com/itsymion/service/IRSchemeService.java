package com.itsymion.service;


import com.baomidou.mybatisplus.extension.service.IService;

import com.itsymion.controller.DataForms.RulerData;
import com.itsymion.controller.DataForms.RulerData1;
import com.itsymion.controller.DataForms.RulerSchemeData;

import com.itsymion.domain.RulerScheme;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IRSchemeService extends IService<RulerScheme>
{

    RulerSchemeData GetRulerSchemes(Integer lineId);

    RulerData SelectRulerInfos(Integer rulerId,Integer lineId,Integer routeId);
    boolean UpdateNewRuler(Integer lineId, RulerData1 rulerData);
}
