package com.itsymion.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itsymion.domain.StationRuler;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IStationRulerDao extends BaseMapper<StationRuler>
{
    @Select("SELECT ts.station_id,tt.station_name,ts.ruler_id,ts.default_dwell_time,ts.up_dwell_time,ts.down_dwell_time,ts.line_id FROM t_station AS tt JOIN t_stationruler AS ts ON tt.station_id = ts.station_id AND ts.ruler_id = #{rulerId} AND ts.station_id >=  #{startStationId} AND ts.station_id <= #{endStationId} AND tt.line_id = #{lineId}")
    List<StationRuler> selectDwellList(Integer rulerId,Integer startStationId,Integer endStationId,Integer lineId);

    @Select("SELECT t_station.station_id,t_station.station_name,t_stationruler.ruler_id,t_stationruler.default_turn_time,t_stationruler.turn_time,t_stationruler.line_id  FROM t_station INNER JOIN t_stationruler ON t_station.station_id=t_stationruler.station_id WHERE t_stationruler.line_id = #{lineId} AND t_station.line_id = #{lineId} AND t_station.station_id = #{startStationId} OR t_stationruler.line_id = #{lineId} AND t_station.line_id = #{lineId} AND t_station.station_id = #{endStationId}")
    List<StationRuler> selectTurnList(Integer startStationId,Integer endStationId,Integer rulerId,Integer lineId);

}
