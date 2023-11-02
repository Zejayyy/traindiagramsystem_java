package com.itsymion.controller.DataForms;

import com.itsymion.Algorithm.LineMsg;
import com.itsymion.Algorithm.Train;
import com.itsymion.Algorithm.Trip;
import com.itsymion.domain.SectionRuler;
import com.itsymion.domain.StationRuler;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DrawData
{

    private ArrayList<Trip> upTrips = new ArrayList<>();
    private ArrayList<Trip> downTrips = new ArrayList<>();
    private ArrayList<Train> optimalPath = new ArrayList<>();
    private ArrayList<Trip> emptyTrips = new ArrayList<>();
    private ArrayList<LineMsg> onLineAndOutLine = new ArrayList<>();
    private List<StationRuler> stationTime = new ArrayList<>();
    private List<SectionRuler> sectionRunningTime = new ArrayList<>();
    private boolean ifSolved;
//    private ArrayList<ArrayList<String>> OptimalPathString = new ArrayList<>();
//    private ArrayList<String> upTripsString = new ArrayList<>();
//    private ArrayList<String> downTripsString = new ArrayList<>();

}
