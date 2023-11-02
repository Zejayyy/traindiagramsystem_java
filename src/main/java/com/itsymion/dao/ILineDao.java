package com.itsymion.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itsymion.domain.Line;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ILineDao extends BaseMapper<Line>
{

}
