package com.itsymion.Algorithm;

import java.util.ArrayList;

public  class TimeTable
{


//    public final int[][] timeParameter = new int[2][10];
    //下行 0-首班车发车时间 1-末班车发车时间 2-平峰发车间隔 3-高峰发车间隔 4-超高峰发车间隔  5-旅行时间 6-trip数 7-折返时间
    //上行 0-首班车发车时间 1-末班车发车时间 2-平峰发车间隔 3-高峰发车间隔 4-超高峰发车间隔  5-旅行时间 6-trip数 7-折返时间
    public ArrayList<Section> upInterval = new ArrayList<>();//上行时段
    public ArrayList<Section> downInterval = new ArrayList<>();//上行时段
    public ArrayList<ArrayList<Section>> Interval = new ArrayList<>();//时段

    public ArrayList<ArrayList<Ratio>> peakTurningHeadway = new ArrayList<>();//转峰系数存放集合

    public ArrayList<Trip> TripSet = new ArrayList<>();//trip集合
    public ArrayList<Trip> upTrips;//上行时刻表集合
    public ArrayList<Trip> downTrips;//下行时刻表集合
    public ArrayList<LineMsg> onLineAndOutLine;

    public ArrayList<Ratio> upRatios = new ArrayList<>();
    public ArrayList<Ratio> downRatios = new ArrayList<>();
    public ArrayList<Train> trainWorkingSet;//上线列车集合
    public ArrayList<Trip> emptyTrips;
//    public ArrayList<LineMsg> onLineAndOutLine;
    public ArrayList<ArrayList<Integer>> sectionRulerTime = new ArrayList<>();//区间运行时间
    public ArrayList<Integer> upSectionRulerTime = new ArrayList<>();//上行区间行驶时间
    public ArrayList<Integer> downSectionRulerTime = new ArrayList<>();//下行区间行驶时间
    public ArrayList<ArrayList<Integer>> stationWaitingTime = new ArrayList<>();//车站停留时间
    public ArrayList<Integer> upStationWaitingTime = new ArrayList<>();//上行车站停留时间
    public ArrayList<Integer> downStationWaitingTime = new ArrayList<>();//下行车站停留时间

    public TimeTable()
    {
    }
}
