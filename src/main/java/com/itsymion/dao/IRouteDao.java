package com.itsymion.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itsymion.domain.Route;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IRouteDao extends BaseMapper<Route>
{

//    @Select("SELECT route_id FROM t_routing WHERE start_station = #{startstation} and end_station = #{endstation}")
//    Integer selectIdByName(@Param("start_station")String startStation, @Param("end_station")String endStation);

}
