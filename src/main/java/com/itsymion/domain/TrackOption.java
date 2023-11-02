package com.itsymion.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_track_options")
public class TrackOption
{
    private Integer id;
    private String trackOptions;
    private Integer sectionId;
    private Integer lineId;

}
