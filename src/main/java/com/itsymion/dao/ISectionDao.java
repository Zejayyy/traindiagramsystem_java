package com.itsymion.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itsymion.domain.Section;
import org.apache.ibatis.annotations.*;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface ISectionDao extends BaseMapper<Section>
{

//    @Results
//            ({
//                    @Result(column = "routeid",property = "routeid"),
//                    @Result(column = "routeid",property = "track",
//                    one = @One(select = "com.itsymion.dao.ITrackDao.selectBySectionId"))
//            })
//    @Select("SELECT displayedid,startstation,endstation FROM t_section WHERE id >= #{startstationid} AND id <= #{endstationid}")
//    Section getByRouteId(Integer routeid,Integer startstationid,Integer endstationid);

    @Select("SELECT  t_section.start_station,t_section.end_station,t_track.plan,t_track.row_type,t_track.section_id,t_track.line_id FROM t_section,t_track WHERE t_section.section_id = t_track.section_id  AND t_track.section_id >= #{startStationId}  AND t_track.section_id < #{endStationId} AND t_track.track_id = #{trackId} AND t_section.line_id = #{lineId} ORDER BY row_type,t_track.section_id ASC")
    List<Section> getByRouteId( Integer startStationId,Integer endStationId, Integer lineId, Integer trackId);
//    @Select("SELECT startstation,endstation,displayedid FROM t_section WHERE id = #{sectionid}")
//    Section SelectBySectionId(Integer sectionid);

}
