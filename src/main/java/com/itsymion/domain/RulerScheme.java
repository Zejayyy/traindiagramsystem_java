package com.itsymion.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_ruler_scheme")
public class RulerScheme
{

    private Integer id;
    private Integer rulerId;
    private String rulerName;
    private Integer lineId;


}
