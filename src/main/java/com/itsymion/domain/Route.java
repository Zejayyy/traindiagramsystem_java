package com.itsymion.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "t_routing")
public class Route
{
    private Integer id;
    private Integer startStationId;
    private Integer endStationId;
    private String endTurnType;
    private String img;
    private String routingName;
    private String startTurnType;
    private Integer travelTime;
    private Integer lineId;
    private Integer routeId;
    private String vehicleType;
    private Integer trainNum;
}
