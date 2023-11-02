    package com.itsymion.dao;


    import com.baomidou.mybatisplus.core.mapper.BaseMapper;
    import com.itsymion.domain.SectionRuler;
    import org.apache.ibatis.annotations.Mapper;
    import org.apache.ibatis.annotations.Select;

    import java.util.List;

    @Mapper
    public interface ISectionRulerDao extends BaseMapper<SectionRuler>
    {

        @Select("SELECT tt.ruler_id,tt.section_id,ts.start_station,ts.end_station,tt.default_travel_time,tt.up_travel_time,tt.down_travel_time,tt.line_id FROM t_sectionruler AS tt JOIN t_section AS ts WHERE tt.section_id = ts.section_id AND tt.ruler_id = #{rulerId} AND tt.section_id >= #{startStationId} AND tt.section_id < #{endStationId} AND tt.line_id = #{lineId} AND ts.line_id = #{lineId}")
        List<SectionRuler> GetTravelTimeList(Integer startStationId,Integer endStationId,Integer rulerId,Integer lineId);

    //    @Select("SELECT tt.rulerId,tt.sectionId,ts.startstation,ts.endstation,tt.defaultDwellTime,tt.upDwellTime,tt.downDwellTime FROM t_sectionruler AS tt JOIN t_section AS ts ON tt.sectionId = ts.id AND tt.rulerId = #{rulerId} AND tt.lineid = #{lineId}")
    //    List<SectionRuler> GetDwellTimeList(Integer rulerId,Integer lineId);
    //
    //    @Select("SELECT tt.rulerId,tt.sectionId,ts.startstation,ts.endstation,tt.defaultTurnTime,tt.turnTime FROM t_sectionruler AS tt JOIN t_section AS ts ON tt.sectionId = ts.id AND tt.rulerId = #{rulerId} AND tt.lineid = #{lineId}")
    //    List<SectionRuler> GetTurnTimeList(Integer rulerId,Integer lineId);


    //    @Select("SELECT tt.rulerId,tt.sectionId,ts.startstation,ts.endstation,tt.* FROM t_sectionruler AS tt JOIN t_section AS ts ON tt.sectionId = ts.id AND tt.rulerId = #{rulerId}")
    //    List<SectionRuler> GetAllList(Integer rulerId);





    }
