package com.itsymion.Algorithm;




public class Basic
{
    public final int M = 1000000;
    public final double OriTemperature = 10.0;//初始温度
    public  final double EndTemperature = 1e-5;//结束温度
    public  final double a = 0.8;//因子、降火系数降温
    public  final int MaxNum = 100;//最大迭代次数
    public  double variance;
    public  int numOfDeployment;
    public  int numOfLoopback;
    public  int trainNum = 400;
    public  int onlineNum;
    public int upETime;//上行空驶时间
    public int upTravelTime;//上行运行时间
    public int xiangTurnTime;//上行后折返时间（合肥火车站）
    public int heETurnTime = 840;//上行后空驶折返时间（合肥火车站）
    public int downETime;//下行后空驶时间
    public int downTravelTime;//下行运行时间
    public int xingTurnTime;//下行后折返时间（九联圩）
    public int jiuETurnTime = 840;//下行后空驶折返时间（九联圩）
    public final int upTerminalCount = 2;//上行末站-合肥站可停车辆
    public boolean ifPeakTurning = false;
    public int peakTurningSection;
    public int volume = 2;//车辆段容量
    public int xingUpDwellTime;
    public int xingDownDwellTime;
    public int xiangUpDwellTime;
    public int xiangDownDwellTime;

}
