package com.itsymion.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName(value = "t_section")
public class Section implements Serializable
{
    private Integer id;
    private String startStation;
    private String endStation;
    private Integer sectionDistance;
    private String sectionName;
    private Integer sectionId;
    private Integer rowType;
    private String plan;
    private List<TrackOption> options;//各区间所有股道方案
    private Integer lineId;
//    @TableField(exist = false)
//    private Track track;


}
