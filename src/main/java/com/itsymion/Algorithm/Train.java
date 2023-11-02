package com.itsymion.Algorithm;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
@Data

public  class Train implements Serializable
{
    public int ID;
//    public int trainStartTime;
    public int status;//1-到达车站，2-停站（待发），3-离开车站,4-下线，并且不可空驶
    public ArrayList<Trip> workingTrip;//执行的trip
    public int position;//上行(九-合)1为起点（九），0为终点（合）
//    public boolean canWorking;//是否能指派工作
//    public ArrayList<Integer> eventList;
    public boolean isCertain;
    public boolean willOff;
    public ArrayList<Trip> preServeTrip;
    public int dt;
    //    public boolean sign;//sign-in false,sign-out true 在平峰时期有些车辆sign-out，到晚高峰sign-in
//    public boolean trainBeWorking;//是否在工作
//    public int type;//0-第一次使用，1-被使用过，2-sign-in的车
    public  Train() {}



}