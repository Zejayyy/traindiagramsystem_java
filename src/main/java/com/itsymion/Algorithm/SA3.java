package com.itsymion.Algorithm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsymion.Algorithm.utils.CopyUtils;
import com.itsymion.controller.utils.SpringUtil;
import com.itsymion.domain.Time;
import com.itsymion.service.ISectionRulerService;
import com.itsymion.service.IStationRulerService;
import com.itsymion.service.ITimeService;
import lombok.Data;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.util.*;

@Data
public class SA3
{
    ApplicationContext applicationContext = SpringUtil.getApplicationContext();
    IStationRulerService stationRulerService = applicationContext.getBean(IStationRulerService.class);
    ISectionRulerService sectionRulerService = applicationContext.getBean(ISectionRulerService.class);
    ITimeService timeService = applicationContext.getBean(ITimeService.class);

    TimeTable timeTable = new TimeTable();
    Random random = new Random();
    Basic basic = new Basic();

    private ArrayList<Trip> curUpTrips = new ArrayList<>();
    private ArrayList<Trip> curDownTrips = new ArrayList<>();
    private ArrayList<Train> OptimalPath = new ArrayList<>();
    private ArrayList<Trip> curEmptyTrips = new ArrayList<>();
    private ArrayList<LineMsg> curOnLineAndOutLine = new ArrayList<>();
    public boolean solved = true;
    public boolean bestSolved = true;
//    private ArrayList<ArrayList<String>> OptimalPathString = new ArrayList<>();
//    private ArrayList<String> curUpTripsString = new ArrayList<>();
//    private ArrayList<String> curDownTripsString = new ArrayList<>();

    public SA3(Integer rulerId, Integer routeId, Integer lineId) throws IOException, ClassNotFoundException
    {
        ReadData(rulerId, routeId, lineId);
        SimulatedAnnealing();
//        Output();
    }


    private void ReadData(Integer rulerId, Integer routeId, Integer lineId)
    {
        QueryWrapper<Time> query1 = new QueryWrapper<>();
        query1
                .eq("ruler_id", rulerId)
                .eq("line_id", lineId)
                .eq("row_type", 1);

        QueryWrapper<Time> query2 = new QueryWrapper<>();
        query2
                .eq("ruler_id", rulerId)
                .eq("line_id", lineId)
                .eq("row_type", 0);

        ReadTimetable1(rulerId, routeId, lineId);
        ReadTimetable2(rulerId, routeId, lineId);
        ReadDownInterval(timeService.list(query2));
        ReadUpInterval(timeService.list(query1));


    }

    private int TimeFormatChange(String time)
    {
        int timeChanged;
        String[] str = time.split(":");
        timeChanged = Integer.parseInt(str[0]) * 3600 + Integer.parseInt(str[1]) * 60 + Integer.parseInt(str[2]);

        return timeChanged;
    }

    private void ReadTimetable1(Integer rulerId, Integer routeId, Integer lineId) //读取站点时间、停站时间
    {

        for (int i = 0; i < stationRulerService.GetDwellTimeList(rulerId, routeId, lineId).getRecords().size(); i++)
        {
            timeTable.downStationWaitingTime.add(Integer.parseInt(stationRulerService.GetDwellTimeList(rulerId, routeId, lineId).getRecords().get(i).getDownDwellTime()));
        }

        for (int i = stationRulerService.GetDwellTimeList(rulerId, routeId, lineId).getRecords().size() - 1; i >= 0; i--)
        {
            timeTable.upStationWaitingTime.add(Integer.parseInt(stationRulerService.GetDwellTimeList(rulerId, routeId, lineId).getRecords().get(i).getUpDwellTime()));
        }
        timeTable.stationWaitingTime.add(timeTable.downStationWaitingTime);
        timeTable.stationWaitingTime.add(timeTable.upStationWaitingTime);
        for (int i = 0; i < sectionRulerService.GetTravelTimeList(routeId, rulerId, lineId).getRecords().size(); i++)
        {
            timeTable.downSectionRulerTime.add(Integer.parseInt(sectionRulerService.GetTravelTimeList(routeId, rulerId, lineId).getRecords().get(i).getDownTravelTime()));
        }
        for (int i = sectionRulerService.GetTravelTimeList(routeId, rulerId, lineId).getRecords().size() - 1; i >= 0; i--)
        {
            timeTable.upSectionRulerTime.add(Integer.parseInt(sectionRulerService.GetTravelTimeList(routeId, rulerId, lineId).getRecords().get(i).getUpTravelTime()));
        }
        timeTable.sectionRulerTime.add(timeTable.downSectionRulerTime);
        timeTable.sectionRulerTime.add(timeTable.upSectionRulerTime);

    }

    private void ReadTimetable2(Integer rulerId, Integer routeId, Integer lineId)
    {
        int time = 0;
        for (int i = 0; i < timeTable.downStationWaitingTime.size(); i++)
        {
            time += timeTable.downStationWaitingTime.get(i);
        }
//        System.out.println("下行停站时间综合" + timeTable.timeParameter[0][5]);

        int eTime = 0;
        for (int i = 0; i < timeTable.downSectionRulerTime.size(); i++)
        {
            time += timeTable.downSectionRulerTime.get(i);
            eTime += timeTable.downSectionRulerTime.get(i);
        }
        basic.downTravelTime = time;
        basic.downETime = eTime;
        time = 0;
        eTime = 0;
//        System.out.println("下行运行时间综合" +  timeTable.timeParameter[0][5]);
        for (int i = 0; i < timeTable.upStationWaitingTime.size(); i++)
        {
            time += timeTable.upStationWaitingTime.get(i);
        }

        for (int i = 0; i < timeTable.upSectionRulerTime.size(); i++)
        {
            time += timeTable.upSectionRulerTime.get(i);
            eTime += timeTable.upSectionRulerTime.get(i);


        }
        basic.upETime = eTime;
        basic.upTravelTime = time;

        basic.xingTurnTime = Integer.parseInt(stationRulerService.GetTurnTimeList(rulerId, routeId, lineId).getRecords().get(1).getTurnTime());//幸福坝
        basic.xiangTurnTime = Integer.parseInt(stationRulerService.GetTurnTimeList(rulerId, routeId, lineId).getRecords().get(0).getTurnTime());//相城路
        basic.xingUpDwellTime = timeTable.upStationWaitingTime.get(timeTable.upStationWaitingTime.size() - 1);
        basic.xingDownDwellTime = timeTable.downStationWaitingTime.get(timeTable.downStationWaitingTime.size() - 1);
        basic.xiangUpDwellTime = timeTable.upStationWaitingTime.get(0);
        basic.xiangDownDwellTime = timeTable.downStationWaitingTime.get(0);
    }

    private void ReadUpInterval(List<Time> upInterval)
    {
        Section section;
        int id = 0;
        for (int i = 0; i < upInterval.size(); i++)
        {
            section = new Section();
            section.setStartTime(TimeFormatChange(upInterval.get(i).getStartTime()));
            section.setEndTime(TimeFormatChange(upInterval.get(i).getEndTime()));
            section.setInterval(Integer.parseInt(upInterval.get(i).getTimeInterval()));
            section.setId(id);
            timeTable.upInterval.add(section);
            id++;
        }
        timeTable.Interval.add(timeTable.upInterval);
    }

    private void ReadDownInterval(List<Time> downInterval)
    {
        Section section;
        int id = 0;
        for (int i = 0; i < downInterval.size(); i++)
        {
            section = new Section();
            section.setStartTime(TimeFormatChange(downInterval.get(i).getStartTime()));
            section.setEndTime(TimeFormatChange(downInterval.get(i).getEndTime()));
            section.setInterval(Integer.parseInt(downInterval.get(i).getTimeInterval()));
            section.setId(id);
            timeTable.downInterval.add(section);
            id++;
        }
        timeTable.Interval.add(timeTable.downInterval);

    }



    private void SimulatedAnnealing() throws IOException, ClassNotFoundException
    {
        GetTurningHeadway();//生成初始解
        ArrayList<ArrayList<Ratio>> originRatio;//存放初始的转峰系数
        originRatio = CopyUtils.CopyRatio(timeTable.peakTurningHeadway);

        double T = basic.OriTemperature; //初始化温度=初始温度
        double T_end = basic.EndTemperature;//结束温度
        double f1, f2, df; //f1为初始解目标函数值，f2为新解目标函数值，df为二者差值
        double r;// 0-1之间的随机数，用来决定是否接受新解
        f1 = GetObjectiveValue();//旧解(最大线上列车数)
        while (T > T_end) // 当温度低于结束温度时，退火结束
        {
            for (int i = 0; i < basic.MaxNum; i++)//MaxNum-最大迭代次数
            {

                timeTable.peakTurningHeadway = CopyUtils.CopyRatio(originRatio);//每次进行迭代时，都在原始解上进行

                GetTurningHeadway();// 产生新解

                f2 = GetObjectiveValue();//新解(最大线上列车数)
                df = f2 - f1;//差值=新解-旧解
                // 以下是Metropolis准则
                if (df >= 0)//如果df<0，得到更好的解，则接受新解
                {
                    r = random.nextDouble();
                    if (Math.exp(-df / T) > r) //若＞，接受新解
                    {
                        OptimalPath = CopyUtils.CopyTrain(timeTable.trainWorkingSet);
                        curUpTrips = CopyUtils.CopyTrip(timeTable.upTrips);
                        curDownTrips = CopyUtils.CopyTrip(timeTable.downTrips);
                        curEmptyTrips = CopyUtils.CopyTrip(timeTable.emptyTrips);
                        bestSolved = solved;
//                        curOnLineAndOutLine = CopyUtils.CopyMsg(timeTable.onLineAndOutLine);
                        f1 = f2;

                    }
                } else
                {
                    OptimalPath = CopyUtils.CopyTrain(timeTable.trainWorkingSet);
                    curUpTrips = CopyUtils.CopyTrip(timeTable.upTrips);
                    curDownTrips = CopyUtils.CopyTrip(timeTable.downTrips);
                    curEmptyTrips = CopyUtils.CopyTrip(timeTable.emptyTrips);
                    bestSolved = solved;
//                    curOnLineAndOutLine = CopyUtils.CopyMsg(timeTable.onLineAndOutLine);
                    f1 = f2;
                }

            }
            T *= basic.a; // 降温， a-降温系数
        }
//        AddEE();//加入空驶
        OptimalPath.removeIf(train -> train.workingTrip.size() == 0);
        AddLineMsg();//加入上下线信息
        curOnLineAndOutLine = CopyUtils.CopyMsg(timeTable.onLineAndOutLine);

    }

    private void AddLineMsg()
    {
        LineMsg lineMsg;
        int trainIndex;
        int tripIndex;
        int intervalIndex;
        int on = 1;
        int out = 0;
        int tempInterval = 0;
        //首，添加上线信息
        for (trainIndex = 0; trainIndex < OptimalPath.size(); trainIndex++)
        {
            if (OptimalPath.get(trainIndex).workingTrip.get(0).type == 0 && !OptimalPath.get(trainIndex).workingTrip.get(0).isEmpty)
                continue;
            lineMsg = new LineMsg(OptimalPath.get(trainIndex).workingTrip.get(0).ID, OptimalPath.get(trainIndex).workingTrip.get(0).type, on, OptimalPath.get(trainIndex).workingTrip.get(0).isEmpty);
            timeTable.onLineAndOutLine.add(lineMsg);
        }

        //中，添加下线信息，后面有接续，即伴随上线
        for (trainIndex = 0; trainIndex < OptimalPath.size(); trainIndex++)
        {
            for (tripIndex = 0; tripIndex < OptimalPath.get(trainIndex).getWorkingTrip().size() - 1; tripIndex++)
            {
                if (OptimalPath.get(trainIndex).getWorkingTrip().get(tripIndex).type == 0)//0-下行（合-九）
                {
                    for (intervalIndex = 0; intervalIndex < timeTable.downInterval.size(); intervalIndex++)
                    {
                        if (OptimalPath.get(trainIndex).getWorkingTrip().get(tripIndex).tripStartTime >= timeTable.downInterval.get(intervalIndex).getStartTime() && OptimalPath.get(trainIndex).getWorkingTrip().get(tripIndex).tripStartTime < timeTable.downInterval.get(intervalIndex).getEndTime())
                        {
                            tempInterval = timeTable.downInterval.get(intervalIndex).getInterval();
                            break;
                        }
                    }
                    if (OptimalPath.get(trainIndex).getWorkingTrip().get(tripIndex + 1).tripStartTime - OptimalPath.get(trainIndex).getWorkingTrip().get(tripIndex).tripEndTime >= 2 * tempInterval)
                    {
                        lineMsg = new LineMsg(OptimalPath.get(trainIndex).workingTrip.get(tripIndex).ID, OptimalPath.get(trainIndex).workingTrip.get(tripIndex).type, out, OptimalPath.get(trainIndex).workingTrip.get(tripIndex).isEmpty);
                        timeTable.onLineAndOutLine.add(lineMsg);
                        lineMsg = new LineMsg(OptimalPath.get(trainIndex).workingTrip.get(tripIndex + 1).ID, OptimalPath.get(trainIndex).workingTrip.get(tripIndex + 1).type, on, OptimalPath.get(trainIndex).workingTrip.get(tripIndex + 1).isEmpty);
                        timeTable.onLineAndOutLine.add(lineMsg);
                    }
                }
            }
        }
        //末，添加下线信息
        int num;
        ArrayList<Trip> tempTripList = new ArrayList<>();
        for (trainIndex = 0; trainIndex < OptimalPath.size(); trainIndex++)
        {
            num = OptimalPath.get(trainIndex).workingTrip.size() - 1;
            if (OptimalPath.get(trainIndex).workingTrip.get(num).type == 0)//只有在九联圩可以下线
                tempTripList.add(OptimalPath.get(trainIndex).workingTrip.get(num));
        }
        tempTripList.sort(Comparator.comparingInt(o -> o.tripEndTime));

        for (trainIndex = 0; trainIndex < tempTripList.size() - 2; trainIndex++)
        {
            lineMsg = new LineMsg(tempTripList.get(trainIndex).ID, tempTripList.get(trainIndex).type, out, tempTripList.get(trainIndex).isEmpty);
            timeTable.onLineAndOutLine.add(lineMsg);
        }

    }

    private void GetTurningHeadway()
    {
        timeTable.peakTurningHeadway.clear();
        GenerateHeadway(timeTable.upInterval, timeTable.upRatios);
        GenerateHeadway(timeTable.downInterval, timeTable.downRatios);
        timeTable.peakTurningHeadway.add(timeTable.downRatios);
        timeTable.peakTurningHeadway.add(timeTable.upRatios);

    }

    private void GenerateHeadway(ArrayList<Section> intervalList, ArrayList<Ratio> ratioList)
    {
        Ratio ratio;
        ratioList.clear();
        for (int i = 0; i < intervalList.size() - 1; i++)
        {
            if (intervalList.get(i).getInterval() != intervalList.get(i + 1).getInterval())
            {
                ratio = new Ratio();
                ratio.setMax(Math.max(intervalList.get(i).getInterval(), intervalList.get(i + 1).getInterval()));
                ratio.setMin(Math.min(intervalList.get(i).getInterval(), intervalList.get(i + 1).getInterval()));
                int value = random.nextInt(ratio.getMax()) % (ratio.getMax() - ratio.getMin() + 1) + ratio.getMin();
                if (value % 60 >= 30)
                    value = ((value / 60) * 60) + 60;
                else
                    value = ((value / 60) * 60);
                ratio.setValue(value);
                ratioList.add(ratio);
            }
        }
    }

    private void GetTrip(int direction, ArrayList<Trip> tripArrayList)
    {
        int[] temp = new int[2];
        Trip trip;
        int travelTime;
        if (direction == 0)
            travelTime = basic.downTravelTime;
        else
            travelTime = basic.upTravelTime;
        //0-SectionIndex=0
        //1-RatioIndex=0
        //2-tempNum=0
        //3-interval
        int curTime = timeTable.Interval.get(direction).get(0).getStartTime();
        while (curTime < timeTable.Interval.get(direction).get(timeTable.Interval.get(direction).size() - 1).getEndTime())
        {
            trip = new Trip();
            trip.type = direction;
            trip.ID = temp[0];
            trip.tripTravelTime = travelTime;//运行时间
            temp[1] = GetInterval(curTime, direction, timeTable.Interval, timeTable.peakTurningHeadway);//发车间隔
            trip.tripStartTime = curTime;
            trip.tripEndTime = trip.tripStartTime + trip.tripTravelTime;
            trip.isEmpty = false;

            tripArrayList.add(trip);
            temp[0]++;
            curTime = trip.tripStartTime + temp[1];
        }
        basic.peakTurningSection = 0;

    }

    private int GetInterval(int currentTime, int direction, ArrayList<ArrayList<Section>> intervalList, ArrayList<ArrayList<Ratio>> headwayList)
    {
        //确定所处峰段
        int intervalIndex = 0;
        if (currentTime == intervalList.get(direction).get(0).getStartTime())
        {
            return intervalList.get(direction).get(intervalIndex).getInterval();
        } else
        {
            for (int i = 0; i < intervalList.get(direction).size(); i++)
            {
                if (currentTime > intervalList.get(direction).get(i).getStartTime() && currentTime <= intervalList.get(direction).get(i).getEndTime())
                {
                    intervalIndex = i;
                }
            }
        }
        //random.nextInt(Max) % (Max - Min + 1) + Min;
        if (intervalIndex != intervalList.get(direction).size() - 1)
        {
            if (basic.ifPeakTurning && currentTime <= intervalList.get(direction).get(basic.peakTurningSection).getEndTime())
            {
                basic.ifPeakTurning = false;
                return intervalList.get(direction).get(intervalIndex + 1).getInterval();
            }
            if (basic.ifPeakTurning && currentTime > intervalList.get(direction).get(basic.peakTurningSection).getEndTime())
            {
                basic.ifPeakTurning = false;
                return intervalList.get(direction).get(intervalIndex).getInterval();
            }
            if (currentTime + intervalList.get(direction).get(intervalIndex).getInterval() >= intervalList.get(direction).get(intervalIndex).getStartTime() && currentTime + intervalList.get(direction).get(intervalIndex).getInterval() <= intervalList.get(direction).get(intervalIndex).getEndTime())
            {
                return intervalList.get(direction).get(intervalIndex).getInterval();
            } else if (currentTime + intervalList.get(direction).get(intervalIndex).getInterval() > intervalList.get(direction).get(intervalIndex + 1).getStartTime())
            {
                basic.ifPeakTurning = true;
                basic.peakTurningSection = intervalIndex;
                return headwayList.get(direction).get(intervalIndex).getValue();
            }
        } else
        {
            if (currentTime + intervalList.get(direction).get(intervalIndex).getInterval() > intervalList.get(direction).get(intervalIndex).getStartTime() && currentTime + intervalList.get(direction).get(intervalIndex).getInterval() <= intervalList.get(direction).get(intervalIndex).getEndTime())
            {
                return intervalList.get(direction).get(intervalIndex).getInterval();
            }
        }
        return basic.M;
    }

    private void AdjustLastTrips()
    {
        int i;
        //获得上行时段截止时刻
        int upLastTime = 0;
        for (i = 0; i < timeTable.upInterval.size(); i++)
        {
            if (timeTable.upInterval.get(i).getEndTime() > upLastTime)
                upLastTime = timeTable.upInterval.get(i).getEndTime();
        }

        //获得下行时段截止时刻
        int downLastTime = 0;
        for (i = 0; i < timeTable.downInterval.size(); i++)
        {
            if (timeTable.downInterval.get(i).getEndTime() > downLastTime)
                downLastTime = timeTable.downInterval.get(i).getEndTime();


        }

        //调整上行最后车次开始时间
        if (timeTable.upTrips.get(timeTable.upTrips.size() - 1).tripStartTime != upLastTime)
        {
            timeTable.upTrips.get(timeTable.upTrips.size() - 1).tripStartTime = upLastTime;
            timeTable.upTrips.get(timeTable.upTrips.size() - 1).tripEndTime = upLastTime + timeTable.upTrips.get(timeTable.upTrips.size() - 1).tripTravelTime;

        }

        //调整下行最后车次开始时间
        if (timeTable.downTrips.get(timeTable.downTrips.size() - 1).tripStartTime != downLastTime)
        {
            timeTable.downTrips.get(timeTable.downTrips.size() - 1).tripStartTime = downLastTime;
            timeTable.downTrips.get(timeTable.downTrips.size() - 1).tripEndTime = downLastTime + timeTable.downTrips.get(timeTable.downTrips.size() - 1).tripTravelTime;


        }


    }

    private double GetObjectiveValue()  //得到运行时刻表
    {
        timeTable.upTrips = new ArrayList<>();//上行时刻表
        timeTable.downTrips = new ArrayList<>();//下行时刻表
        timeTable.emptyTrips = new ArrayList<>();//空驶车次集合n
        timeTable.onLineAndOutLine = new ArrayList<>();

        basic.ifPeakTurning = false;

        GetTrip(1, timeTable.upTrips);
        basic.ifPeakTurning = false;

        GetTrip(0, timeTable.downTrips);
        AdjustLastTrips();
        GetTripSort();//整合trip到一个集合中
        InitializeTrain();//初始化列车

        DiscreteEvent();//得到执行每个事件的列车
        int num = 0;
        for (int i = 0; i < timeTable.trainWorkingSet.size(); i++)
        {
            if (timeTable.trainWorkingSet.get(i).workingTrip.size() != 0)
            {
                num++;
            } else
                break;
        }

        basic.onlineNum = num;
//        System.out.println(num);
        basic.variance = GetVariance();
        basic.numOfDeployment = NumOfDeployment(timeTable.trainWorkingSet);
        basic.numOfLoopback = NumOfLoopback(timeTable.trainWorkingSet);
        return basic.onlineNum + 0.1 * (basic.numOfLoopback + 0.8 * basic.numOfDeployment);


    }

    //计算调配数
    private int NumOfDeployment(ArrayList<Train> trains)
    {
        int num = 0;
        for (int i = 0; i < basic.trainNum; i++)
        {
            if (trains.get(i).workingTrip.size() != 0)
            {
                if (trains.get(i).workingTrip.get(0).type == 0)
                {
                    num++;
                }
            } else
            {
                break;
            }
        }
        return num;
    }

    //计算回送数
    private int NumOfLoopback(ArrayList<Train> trains)
    {
        int num = 0;
        for (int i = 0; i < basic.trainNum; i++)
        {
            if (trains.get(i).workingTrip.size() != 0)
            {
                if (trains.get(i).workingTrip.get(trains.get(i).workingTrip.size() - 1).type == 1)
                {
                    num++;
                }
            } else
            {
                break;
            }
        }

        return num;
    }

    private double GetVariance()
    {
        double[] Number = new double[5];
        //0-平均值 1-总和 2-分母 3-分子 4-方差
        for (int i = 0; i < timeTable.trainWorkingSet.size(); i++)
        {
            Number[1] += timeTable.trainWorkingSet.get(i).workingTrip.size();
        }
        for (int i = 0; i < timeTable.trainWorkingSet.size(); i++)
        {
            if (timeTable.trainWorkingSet.get(i).workingTrip.size() != 0)
                Number[3]++;
            else
                break;
        }
        Number[0] = Number[1] / Number[3];
        for (int i = 0; i < Number[3]; i++)
        {
            Number[2] += Math.pow(Math.abs(timeTable.trainWorkingSet.get(i).workingTrip.size() - Number[0]), 2);
        }
        Number[4] = Number[2] / Number[3];
        return Number[4];
    }

    private void GetTripSort()//将所有trip时刻表整合到一个集合中
    {
        timeTable.TripSet = new ArrayList<>();//定义trip集合tripset
        timeTable.TripSet.addAll(timeTable.upTrips);     //先将上行trip加入到集合中，然后使用二分法按次序将下行trip加入到该集合中，如果时间重复，放在上行trip点的后面

        int middle, low, high;
        Trip temp;
        for (int i = 0; i < timeTable.downTrips.size(); i++)
        {
            temp = timeTable.downTrips.get(i);
            low = 0;
            high = timeTable.TripSet.size() - 1;
            while (low <= high)                               //二分法
            {
                middle = (low + high) / 2;
                if (temp.tripStartTime <= timeTable.TripSet.get(middle).tripStartTime)
                {
                    high = middle - 1;
                } else
                {
                    low = middle + 1;
                }
            }
            timeTable.TripSet.add(low, temp);

        }
        for (int i = 0; i < timeTable.TripSet.size(); i++)
            timeTable.TripSet.get(i).label = i;
    }

    private void InitializeTrain()//初始化列车属性
    {
        timeTable.trainWorkingSet = new ArrayList<>();

        for (int i = 0; i < basic.trainNum; i++)
        {
            Train train = new Train();
//            train.beWorking = false;
            train.isCertain = false;
            train.willOff = false;
            train.dt = basic.M;
            train.workingTrip = new ArrayList<>();
            train.position = -1;//初始位置
            train.ID = i;
            train.status = 1;
//            train.canWorking = true;
            timeTable.trainWorkingSet.add(train);
        }

    }

    private void DiscreteEvent()
    {
        int M = 10000000;
        int on = 1;
        int out = 0;
//        int num;
        int temp = 0;//空驶trip计数
        int count = 0;//停站待发车辆计数
        int index, intervalIndex1,intervalIndex2 = 0;
        int dt_next;//发生下一个离散事件的时间间隔
        int dt_trip = M;
        int tripIndex = 0;
        int trainNum = basic.trainNum;
//        int[] dt_train = new int[trainNum];//每辆车发生下一个离散事件的时间间隔
        ArrayList<Integer> train_1;
        ArrayList<Train> train_7;
        ArrayList<Integer> tempIndex;
//        Arrays.fill(dt_train, M);
        LineMsg lineMsg;
        boolean found;
        int trainIndex;
        int TT = M;
        int currentTime = timeTable.TripSet.get(0).tripStartTime;
//        System.out.println("=====================");
//        System.out.println(timeTable.TripSet.size());
//        System.out.println("=========================");
        m:
        while (currentTime <= TT)
        {
//            System.out.println(currentTime);
            /*确定每列车的下一个状态与时间*/
            train_1 = new ArrayList<>();

            if (!timeTable.TripSet.get(tripIndex).tripBeWorked && dt_trip == M)
            {
                dt_trip = timeTable.TripSet.get(tripIndex).tripStartTime - currentTime;
            }

            for (trainIndex = 0; trainIndex < trainNum; trainIndex++)
            {
                //1-在场站，2-到达首站，3-首站待发，4-到达末站，5-末站待发，6-下线，7-折返
                switch (timeTable.trainWorkingSet.get(trainIndex).status)
                {

                    case 1 -> train_1.add(timeTable.trainWorkingSet.get(trainIndex).ID);//车辆在场站
                    case 2, 4 ->
                            {//到达首末站时
                                if (!timeTable.trainWorkingSet.get(trainIndex).isCertain)
                                {
                                    //确定车辆位置
                                    if (timeTable.trainWorkingSet.get(trainIndex).workingTrip.get(timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() - 1).type == 0)
                                        timeTable.trainWorkingSet.get(trainIndex).dt = basic.xingUpDwellTime;
                                    else
                                        timeTable.trainWorkingSet.get(trainIndex).dt = basic.xiangDownDwellTime;
                                    timeTable.trainWorkingSet.get(trainIndex).isCertain = true;
                                }
                            }
                    case 3 ->
                            {
                                if (!timeTable.trainWorkingSet.get(trainIndex).isCertain)
                                {//确定运行时间
                                    if (timeTable.trainWorkingSet.get(trainIndex).workingTrip.get(timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() - 1).type == 1)
                                        timeTable.trainWorkingSet.get(trainIndex).dt = timeTable.trainWorkingSet.get(trainIndex).workingTrip.get(timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() - 1).tripTravelTime - basic.xingUpDwellTime - basic.xiangDownDwellTime;
                                    else
                                        timeTable.trainWorkingSet.get(trainIndex).dt = timeTable.trainWorkingSet.get(trainIndex).workingTrip.get(timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() - 1).tripTravelTime - basic.xiangDownDwellTime - basic.xingUpDwellTime;
                                    timeTable.trainWorkingSet.get(trainIndex).isCertain = true;
                                }
                            }
                    case 5 ->
                            {
                                if (!timeTable.trainWorkingSet.get(trainIndex).isCertain)
                                {
                                    if (timeTable.trainWorkingSet.get(trainIndex).position == 1)
                                    {
                                        int tempIndex1 = M,tempIndex2 = M;
                                        for (index = 0; index < timeTable.TripSet.size(); index++)//找到下一个执行的trip并预定
                                        {
                                            if (!timeTable.TripSet.get(index).tripBeWorked && timeTable.TripSet.get(index).type == timeTable.trainWorkingSet.get(trainIndex).position)
                                            {
                                                if (timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() == 0)
                                                {
                                                    tempIndex1 = index;
                                                    break;
                                                } else
                                                {
                                                    if (timeTable.TripSet.get(index).tripStartTime >= timeTable.trainWorkingSet.get(trainIndex).workingTrip.get(timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() - 1).tripEndTime + basic.xingTurnTime)
                                                    {
                                                        tempIndex1 = index;
                                                        break;
                                                    }
                                                }
                                            }
                                        }

                                        if (tempIndex1 != M)
                                        {
                                            intervalIndex1 = 0;

                                            for (index = 0; index < timeTable.Interval.get(timeTable.TripSet.get(tempIndex1).type).size(); index++)
                                            {
                                                if (timeTable.TripSet.get(tempIndex1).tripStartTime >= timeTable.Interval.get(timeTable.TripSet.get(tempIndex1).type).get(index).getStartTime() && timeTable.TripSet.get(tempIndex1).tripStartTime < timeTable.Interval.get(timeTable.TripSet.get(tempIndex1).type).get(index).getEndTime())
                                                {
                                                    intervalIndex1 = index;
                                                    break;
                                                }
                                            }
                                            for (index = 0; index < timeTable.TripSet.size(); index++)//找到下下一个执行的trip并预定
                                            {
                                                if (!timeTable.TripSet.get(index).tripBeWorked && timeTable.TripSet.get(index).type != timeTable.TripSet.get(tempIndex1).type && timeTable.TripSet.get(index).tripStartTime >= timeTable.TripSet.get(tempIndex1).tripEndTime + basic.xiangTurnTime)
                                                {
                                                    tempIndex2 = index;
                                                    break;
                                                }
                                            }

                                            if (tempIndex2 != M)
                                            {
                                                for (index = 0; index < timeTable.Interval.get(timeTable.TripSet.get(tempIndex1).type).size(); index++)
                                                {
                                                    if (!timeTable.TripSet.get(tempIndex2).tripBeWorked && timeTable.TripSet.get(tempIndex2).tripStartTime >= timeTable.Interval.get(timeTable.TripSet.get(tempIndex2).type).get(index).getStartTime() && timeTable.TripSet.get(tempIndex2).tripStartTime < timeTable.Interval.get(timeTable.TripSet.get(tempIndex2).type).get(index).getEndTime())
                                                    {
                                                        intervalIndex2 = index;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (timeTable.TripSet.get(tempIndex1).tripStartTime - timeTable.trainWorkingSet.get(trainIndex).workingTrip.get(timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() - 1).tripEndTime - basic.xingTurnTime >= 2 * timeTable.Interval.get(timeTable.TripSet.get(tempIndex1).type).get(intervalIndex1).getInterval())
                                            {
                                                timeTable.trainWorkingSet.get(trainIndex).dt = 0;
                                                timeTable.trainWorkingSet.get(trainIndex).willOff = true;
                                            } else
                                            {
                                                if (tempIndex2 != M)
                                                {
                                                    if (timeTable.TripSet.get(tempIndex2).tripStartTime - timeTable.TripSet.get(tempIndex1).tripEndTime - basic.xiangTurnTime >= 2 * timeTable.Interval.get(timeTable.TripSet.get(tempIndex2).type).get(intervalIndex2).getInterval())
                                                    {
                                                        timeTable.trainWorkingSet.get(trainIndex).dt = 0;
                                                        timeTable.trainWorkingSet.get(trainIndex).willOff = true;
                                                    }
                                                    else
                                                    {

                                                        timeTable.trainWorkingSet.get(trainIndex).willOff = false;
                                                        timeTable.trainWorkingSet.get(trainIndex).dt = 0;
                                                    }
                                                }
                                                else
                                                {

                                                    timeTable.trainWorkingSet.get(trainIndex).willOff = false;
                                                    timeTable.trainWorkingSet.get(trainIndex).dt = 0;
                                                }


                                            }
                                        } else
                                        {
                                            timeTable.trainWorkingSet.get(trainIndex).dt = 0;
                                            timeTable.trainWorkingSet.get(trainIndex).willOff = true;
                                        }
                                    } else
                                    {
                                        timeTable.trainWorkingSet.get(trainIndex).dt = 0;
                                        timeTable.trainWorkingSet.get(trainIndex).willOff = false;
                                    }
                                    timeTable.trainWorkingSet.get(trainIndex).isCertain = true;
                                }

                            }
                    case 6 ->
                            {
                                if (!timeTable.trainWorkingSet.get(trainIndex).isCertain)
                                {
                                    timeTable.trainWorkingSet.get(trainIndex).dt = M;
                                    timeTable.trainWorkingSet.get(trainIndex).isCertain = true;
                                }

                            }
                    case 7 ->
                            {
                                if (!timeTable.trainWorkingSet.get(trainIndex).isCertain)
                                {
                                    if (!timeTable.TripSet.get(tripIndex).tripBeWorked && dt_trip == 0 && timeTable.TripSet.get(tripIndex).type == timeTable.trainWorkingSet.get(trainIndex).position)
                                    {
                                        if (timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() == 0)
                                        {
                                            timeTable.trainWorkingSet.get(trainIndex).dt = 0;
                                            timeTable.trainWorkingSet.get(trainIndex).workingTrip.add(timeTable.TripSet.get(tripIndex));
                                            timeTable.TripSet.get(tripIndex).tripBeWorked = true;
                                            dt_trip = M;
                                            timeTable.TripSet.get(tripIndex).whichTrain = timeTable.trainWorkingSet.get(trainIndex).ID;
                                            timeTable.trainWorkingSet.get(trainIndex).isCertain = true;
                                        } else
                                        {
                                            if (timeTable.TripSet.get(tripIndex).type == 1)
                                            {
                                                if (timeTable.TripSet.get(tripIndex).tripStartTime >= timeTable.trainWorkingSet.get(trainIndex).workingTrip.get(timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() - 1).tripEndTime + basic.xingTurnTime)
                                                {
                                                    timeTable.trainWorkingSet.get(trainIndex).workingTrip.add(timeTable.TripSet.get(tripIndex));
                                                    timeTable.TripSet.get(tripIndex).tripBeWorked = true;
                                                    dt_trip = M;
                                                    timeTable.TripSet.get(tripIndex).whichTrain = timeTable.trainWorkingSet.get(trainIndex).ID;
                                                    timeTable.trainWorkingSet.get(trainIndex).isCertain = true;
                                                    if (timeTable.trainWorkingSet.get(trainIndex).position == 0)
                                                        timeTable.trainWorkingSet.get(trainIndex).dt = basic.xiangTurnTime;
                                                    else
                                                        timeTable.trainWorkingSet.get(trainIndex).dt = basic.xingTurnTime;
                                                }
                                            } else
                                            {
                                                if (timeTable.TripSet.get(tripIndex).tripStartTime >= timeTable.trainWorkingSet.get(trainIndex).workingTrip.get(timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() - 1).tripEndTime + basic.xiangTurnTime)
                                                {
                                                    found = false;
                                                    for (int i = trainIndex + 1; i < trainNum; i++)
                                                    {
                                                        if (!timeTable.trainWorkingSet.get(i).isCertain
                                                                && timeTable.trainWorkingSet.get(i).position == timeTable.trainWorkingSet.get(trainIndex).position
                                                                && timeTable.trainWorkingSet.get(i).status == 7
                                                                && timeTable.trainWorkingSet.get(i).workingTrip.get(timeTable.trainWorkingSet.get(i).workingTrip.size() - 1).tripEndTime < timeTable.trainWorkingSet.get(trainIndex).workingTrip.get(timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() - 1).tripEndTime
                                                                && timeTable.TripSet.get(tripIndex).tripStartTime >= timeTable.trainWorkingSet.get(i).workingTrip.get(timeTable.trainWorkingSet.get(i).workingTrip.size() - 1).tripEndTime + basic.xiangTurnTime)
                                                        {
                                                            found = true;
                                                            break;
                                                        }
                                                    }
                                                    if (!found)
                                                    {
                                                        timeTable.trainWorkingSet.get(trainIndex).workingTrip.add(timeTable.TripSet.get(tripIndex));
                                                        timeTable.TripSet.get(tripIndex).tripBeWorked = true;
                                                        dt_trip = M;
                                                        timeTable.TripSet.get(tripIndex).whichTrain = timeTable.trainWorkingSet.get(trainIndex).ID;
                                                        timeTable.trainWorkingSet.get(trainIndex).isCertain = true;
                                                        if (timeTable.trainWorkingSet.get(trainIndex).position == 0)
                                                            timeTable.trainWorkingSet.get(trainIndex).dt = basic.xiangTurnTime;
                                                        else
                                                            timeTable.trainWorkingSet.get(trainIndex).dt = basic.xingTurnTime;
                                                    }
                                                    else
                                                    {
                                                        timeTable.trainWorkingSet.get(trainIndex).dt = M;
                                                    }
                                                }
                                            }
//                                    }
                                        }
                                    } else
                                    {
                                        timeTable.trainWorkingSet.get(trainIndex).isCertain = false;
                                        timeTable.trainWorkingSet.get(trainIndex).dt = M;
                                    }

                                }

                            }
                }
            }

            for (trainIndex = 0; trainIndex < train_1.size(); trainIndex++)
            {
                if (timeTable.TripSet.get(tripIndex).tripBeWorked && dt_trip == M)
                {
                    if (!timeTable.trainWorkingSet.get(train_1.get(trainIndex)).isCertain)
                    {
                        timeTable.trainWorkingSet.get(train_1.get(trainIndex)).dt = M;

                    }
                } else if (!timeTable.TripSet.get(tripIndex).tripBeWorked && dt_trip == 0)
                {
                    if (!timeTable.trainWorkingSet.get(train_1.get(trainIndex)).isCertain)
                    {
                        if (timeTable.trainWorkingSet.get(train_1.get(trainIndex)).position == timeTable.TripSet.get(tripIndex).type)
                        {
                            timeTable.TripSet.get(tripIndex).tripBeWorked = true;
                            dt_trip = M;
                            timeTable.trainWorkingSet.get(train_1.get(trainIndex)).workingTrip.add(timeTable.TripSet.get(tripIndex));
                            timeTable.TripSet.get(tripIndex).whichTrain = train_1.get(trainIndex);
                            timeTable.trainWorkingSet.get(train_1.get(trainIndex)).isCertain = true;

                        } else
                        {
                            Trip emptyTrip = new Trip();
                            emptyTrip.tripEndTime = timeTable.TripSet.get(tripIndex).tripStartTime - basic.heETurnTime;
                            emptyTrip.tripStartTime = emptyTrip.tripEndTime - basic.upETime;
                            emptyTrip.tripTravelTime = emptyTrip.tripEndTime - emptyTrip.tripStartTime;
                            emptyTrip.type = 1;
                            emptyTrip.ID = temp;
                            emptyTrip.whichTrain = train_1.get(trainIndex);
                            emptyTrip.tripBeWorked = true;
                            emptyTrip.isEmpty = true;
                            timeTable.trainWorkingSet.get(train_1.get(trainIndex)).workingTrip.add(0, emptyTrip);
                            timeTable.emptyTrips.add(emptyTrip);
                            temp++;
                            timeTable.TripSet.get(tripIndex).tripBeWorked = true;
                            dt_trip = M;
                            timeTable.trainWorkingSet.get(train_1.get(trainIndex)).workingTrip.add(timeTable.TripSet.get(tripIndex));
                            timeTable.TripSet.get(tripIndex).whichTrain = train_1.get(trainIndex);
//                            lineMsg = new LineMsg(timeTable.trainWorkingSet.get(trainIndex).workingTrip.get(timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() - 1).ID,timeTable.trainWorkingSet.get(trainIndex).workingTrip.get(timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() - 1).type,on,timeTable.trainWorkingSet.get(trainIndex).workingTrip.get(timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() - 1).isEmpty);
//                            timeTable.onLineAndOutLine.add(lineMsg);
                            timeTable.trainWorkingSet.get(train_1.get(trainIndex)).isCertain = true;
                        }
                        timeTable.trainWorkingSet.get(train_1.get(trainIndex)).dt = 0;
//                        dt_train[] = 0;
                    }
                }
            }
            if (dt_trip == M)
                tripIndex++;
            /*确定下一个离散事件*/
            dt_next = timeTable.trainWorkingSet.get(0).dt;
            for (int i = 0; i < timeTable.trainWorkingSet.size(); i++)
            {
                if (dt_next > timeTable.trainWorkingSet.get(i).dt)
                {
                    dt_next = timeTable.trainWorkingSet.get(i).dt;
                }
            }
            if (dt_trip != M)
                dt_next = Math.min(dt_trip, dt_next);
            /*更新列车信息*/
            for (trainIndex = 0; trainIndex < timeTable.trainWorkingSet.size(); trainIndex++)
            {//1-在场站，2-到达首站，3-首站待发，4-到达末站，5-末站待发，6-下线，7-折返
                switch (timeTable.trainWorkingSet.get(trainIndex).status)
                {
                    case 1, 7 ->
                            {
                                if (dt_next == timeTable.trainWorkingSet.get(trainIndex).dt)
                                {
                                    timeTable.trainWorkingSet.get(trainIndex).status = 2;
                                    timeTable.trainWorkingSet.get(trainIndex).isCertain = false;

                                }

                            }
                    case 2 ->
                            {
                                if (dt_next == timeTable.trainWorkingSet.get(trainIndex).dt)
                                {
                                    timeTable.trainWorkingSet.get(trainIndex).status = 3;
                                    timeTable.trainWorkingSet.get(trainIndex).isCertain = false;

                                }

                            }
                    case 3 ->
                            {
                                if (dt_next == timeTable.trainWorkingSet.get(trainIndex).dt)
                                {
                                    tempIndex = new ArrayList<>();
                                    for (index = 0; index < timeTable.trainWorkingSet.size(); index++)
                                    {
                                        if (index == trainIndex)
                                            continue;
                                        if (timeTable.trainWorkingSet.get(index).status == 4 && timeTable.trainWorkingSet.get(index).position == timeTable.trainWorkingSet.get(trainIndex).position)
                                        {
                                            tempIndex.add(index);
                                        }
                                    }
                                    if (tempIndex.size() >= 2)
                                    {

                                        int min = timeTable.trainWorkingSet.get(tempIndex.get(0)).dt;
                                        for (index = 1; index < tempIndex.size(); index++)
                                        {
                                            if (timeTable.trainWorkingSet.get(tempIndex.get(index)).dt < min)
                                                min = timeTable.trainWorkingSet.get(tempIndex.get(index)).dt;
                                        }

                                        timeTable.trainWorkingSet.get(trainIndex).dt += min;
                                        timeTable.trainWorkingSet.get(trainIndex).workingTrip.get(timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() - 1).tripStartTime += min;
                                        timeTable.trainWorkingSet.get(trainIndex).workingTrip.get(timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() - 1).tripEndTime += min;
                                        timeTable.trainWorkingSet.get(trainIndex).isCertain = true;
                                    } else
                                    {
                                        timeTable.trainWorkingSet.get(trainIndex).status = 4;
                                        if (timeTable.trainWorkingSet.get(trainIndex).workingTrip.get(timeTable.trainWorkingSet.get(trainIndex).workingTrip.size() - 1).type == 1)
                                        {
                                            timeTable.trainWorkingSet.get(trainIndex).position = 0;
                                        } else
                                        {
                                            timeTable.trainWorkingSet.get(trainIndex).position = 1;
                                        }
                                        timeTable.trainWorkingSet.get(trainIndex).isCertain = false;
                                    }

                                }

                            }
                    case 4 ->
                            {
                                if (dt_next == timeTable.trainWorkingSet.get(trainIndex).dt)
                                {
                                    timeTable.trainWorkingSet.get(trainIndex).status = 5;
                                    timeTable.trainWorkingSet.get(trainIndex).isCertain = false;
                                }
                            }
                    case 5 ->
                            {
                                if (dt_next == timeTable.trainWorkingSet.get(trainIndex).dt)
                                {
                                    if (timeTable.trainWorkingSet.get(trainIndex).willOff)
                                    {
                                        timeTable.trainWorkingSet.get(trainIndex).status = 6;

                                        timeTable.trainWorkingSet.get(trainIndex).willOff = false;

                                    } else
                                    {
                                        timeTable.trainWorkingSet.get(trainIndex).status = 7;
                                        timeTable.trainWorkingSet.get(trainIndex).willOff = false;
                                    }
                                    timeTable.trainWorkingSet.get(trainIndex).isCertain = false;

                                }

                            }
                    case 6 ->
                            {
                                timeTable.trainWorkingSet.get(trainIndex).status = 1;
                                timeTable.trainWorkingSet.get(trainIndex).isCertain = false;
                            }
                }
            }

            for (index = 0; index < timeTable.trainWorkingSet.size(); index++)
            {
                if (timeTable.trainWorkingSet.get(index).dt != M)
                {
                    timeTable.trainWorkingSet.get(index).dt -= dt_next;
                }
            }
            if (dt_trip != M)
                dt_trip -= dt_next;
            currentTime += dt_next;


            timeTable.trainWorkingSet.sort(Comparator.comparingInt(o -> o.dt));

            count = 0;
            for (index = 0; index < timeTable.TripSet.size(); index++)
            {
                if (timeTable.TripSet.get(index).tripBeWorked)
                {
                    count++;
                }
            }
            int count1 = 0;
            int tempInterval = 0;
            for (index = 0;index < timeTable.trainWorkingSet.size();index++)
            {

                if (timeTable.trainWorkingSet.get(index).status == 7 && timeTable.trainWorkingSet.get(index).position == 0 && timeTable.trainWorkingSet.get(index).isCertain )
                {
                    for (int intervalIndex = 0; intervalIndex < timeTable.downInterval.size(); intervalIndex++)
                    {
                        if (timeTable.trainWorkingSet.get(index).workingTrip.get(timeTable.trainWorkingSet.get(index).workingTrip.size() - 1).tripStartTime >= timeTable.downInterval.get(intervalIndex).getStartTime() && timeTable.trainWorkingSet.get(index).workingTrip.get(timeTable.trainWorkingSet.get(index).workingTrip.size() - 1).tripStartTime < timeTable.downInterval.get(intervalIndex).getEndTime())
                        {
                            tempInterval = timeTable.downInterval.get(intervalIndex).getInterval();
                            break;
                        }
                    }
                    if (timeTable.trainWorkingSet.get(index).workingTrip.get(timeTable.trainWorkingSet.get(index).workingTrip.size() - 1).tripStartTime - timeTable.trainWorkingSet.get(index).workingTrip.get(timeTable.trainWorkingSet.get(index).workingTrip.size() - 2).tripEndTime > 2 * tempInterval)
                    {
                        count1++;
                    }
                }

            }

            if (count1 > 1)
                solved = false;

            if (count == timeTable.TripSet.size())
            {
//                System.out.println("所有车次全部执行完毕！！！");

                break;
            }
//            System.out.println(count);
        }

    }


}



