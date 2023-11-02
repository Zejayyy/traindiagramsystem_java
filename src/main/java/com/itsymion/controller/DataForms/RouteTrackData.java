package com.itsymion.controller.DataForms;


import com.itsymion.domain.Route;
import com.itsymion.domain.TrackScheme;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RouteTrackData
{


    private List<Route> routeList = new ArrayList<>();
    private List<TrackScheme> trackSchemes = new ArrayList<>();
}
