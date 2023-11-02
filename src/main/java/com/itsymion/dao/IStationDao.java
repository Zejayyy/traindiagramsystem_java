package com.itsymion.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itsymion.domain.Station;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface IStationDao extends BaseMapper<Station>
{
    @Update("Update  t_station SET station_id =  station_id + 1 where station_id in (select station_id from (select * from t_station WHERE station_id >= #{id}  AND line_id = #{lineId}) as station_id)")
    void updateBeforeByStationId(Integer id,Integer lineId);

    @Update("Update  t_station SET station_id =  station_id + 1 where station_id in (select station_id from (select * from t_station WHERE station_id > #{id} AND line_id = #{lineId}) as station_id)")
    void updateAfterByStationId(Integer id,Integer lineId);

    @Select("SELECT * FROM t_station ORDER BY station_id ASC limit #{id1},#{id2}")
    List<Station> selectPageList(Integer id1,Integer id2,Integer lineId);

    @Update("UPDATE t_station SET station_id = station_id - 1 where station_id > #{id} AND line_id = #{lineId}")
    Boolean updateAfterDelete(Integer id,Integer lineId);
    @Select("SELECT * FROM t_station WHERE line_id = #{lineId} ORDER BY station_id ASC ")
    List<Station> getAll(Integer lineId);
}
