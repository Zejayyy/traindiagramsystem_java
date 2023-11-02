package com.itsymion.controller.DataForms;


import com.itsymion.domain.SectionRuler;
import com.itsymion.domain.StationRuler;
import com.itsymion.domain.Time;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RulerData
{

    private List<SectionRuler> sectionRulerList = new ArrayList<>();
    private List<StationRuler> dwellRulerList = new ArrayList<>();
    private List<StationRuler> turnRulerList = new ArrayList<>();
    private List<Time> timeList = new ArrayList<>();

}
