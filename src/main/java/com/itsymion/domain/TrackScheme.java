package com.itsymion.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_track_scheme")
public class TrackScheme
{

    private Integer id;
    private Integer trackId;
    private String trackName;

}
