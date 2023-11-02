package com.itsymion.controller.DataForms;


import com.itsymion.domain.StationRuler;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StationRulerData
{

    private List<StationRuler> records = new ArrayList<>();

}
