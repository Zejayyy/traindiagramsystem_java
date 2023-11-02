package com.itsymion.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itsymion.domain.Time;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ITimeDao extends BaseMapper<Time>
{

    @Select("SELECT * FROM t_time_period WHERE ruler_id = #{rulerId} AND line_id = #{lineId}")
    List<Time> GetInterval(Integer rulerId, Integer lineId);





}
