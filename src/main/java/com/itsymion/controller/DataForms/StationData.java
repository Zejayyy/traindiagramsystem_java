package com.itsymion.controller.DataForms;

import com.itsymion.domain.Station;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class StationData
{
    private List<Station> records = new ArrayList<>();
    private Long total;
    private Integer size;
    private Integer current;
    private Boolean searchCount;
    private Integer pages;

}
