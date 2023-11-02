package com.itsymion.domain;



import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;





@Data
@TableName(value = "t_station")
public class Station
{
    private Integer id;
    private Integer isSingleOrDouble;
    private Integer isTransfer;
    private Integer stationId;
    private Integer longitude;
    private Integer latitude;
    private String stationName;
    private String stationType;
    private Integer lineId;
    private String stationEnglishName;
    private String remark;


}
