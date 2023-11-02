package com.itsymion.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_stationruler")
public class StationRuler
{
    private String stationName;
    private String defaultDwellTime;
    private String upDwellTime;
    private String downDwellTime;
    private String defaultTurnTime;
    private String turnTime;
    private Integer id;
    private Integer rulerId;
    private Integer stationId;
    private Integer lineId;

}
