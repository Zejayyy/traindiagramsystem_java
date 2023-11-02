package com.itsymion.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "t_time_period")
public class Time implements Serializable
{
    private Integer id;
    private Integer rulerId;
    private Integer lineId;
    private Integer rowType;
    private String startTime;
    private String endTime;
    private String timeInterval;
    private Integer sortId;
    private String periodName;

























}
