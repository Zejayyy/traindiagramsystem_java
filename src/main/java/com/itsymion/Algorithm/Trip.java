package com.itsymion.Algorithm;

import java.io.Serializable;

public class Trip implements Serializable
{
    public int ID;//车次
    public int tripStartTime;//开始时间
    public int tripTravelTime;//运行时间
    public int tripEndTime;//结束时间
    public int type;//1上行,0下行
    public boolean tripBeWorked;//是否在工作
    public boolean isEmpty;
    public int label;
    public int whichTrain;//由哪列车完成
    public boolean isDelay;
//    public boolean isSubscribed;
//    public int[][] tripStationTime;//存放下行到站时间和出站时间


}