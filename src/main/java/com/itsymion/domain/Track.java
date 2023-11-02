package com.itsymion.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_track")
public class Track
{
    private Integer id;
    private String plan;
    private Integer sectionId;
    private Integer rowType;
    private Integer trackId;
    private Integer lineId;
    private String img;
    @TableField(exist = false)
    private Section section;
}
