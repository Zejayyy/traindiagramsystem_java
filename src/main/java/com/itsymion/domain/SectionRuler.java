package com.itsymion.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_sectionruler")
public class SectionRuler
{
    private String startStation;
    private String endStation;
    private Integer id;
    private Integer rulerId;
    private Integer sectionId;
    private String defaultTravelTime;
    private String upTravelTime;
    private String downTravelTime;
    private Integer lineId;


}
