package com.itsymion.controller.DataForms;

import com.itsymion.domain.RouteAndTrack;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TrackData
{
    private List<RouteAndTrack> planList = new ArrayList<>();
}
