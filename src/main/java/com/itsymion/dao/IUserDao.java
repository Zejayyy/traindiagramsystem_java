package com.itsymion.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itsymion.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IUserDao extends BaseMapper<User>
{

    @Select("select type from t_user where username = #{username} and password = #{password}")
    String login(@Param("username") String username, @Param("password") String password);

}
