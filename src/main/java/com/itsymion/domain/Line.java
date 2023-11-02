package com.itsymion.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_line")
public class Line
{
    private Integer id;
    private String lineName;
    private Integer lineId;

}
