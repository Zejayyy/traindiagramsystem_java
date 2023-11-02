package com.itsymion.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsymion.domain.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StationDaoTest
{

    @Autowired
    private IStationDao stationDao;

    @Test
    void testGetStationById()
    {

        System.out.println(stationDao.selectById(2));

    }

    /**
     * 新增记录
     */
    @Test
    void testSave()
    {


    }

    /**
     * 修改
     */
    @Test
    void testUpdate()
    {

    }
    /**
     * 删除操作
     */
    @Test
    void testDelete()
    {
        QueryWrapper<Station> queryWrapper = new QueryWrapper<Station>();
        queryWrapper.eq("stationid",5);
        stationDao.delete(queryWrapper);
    }

    /**
     * 获得全部
     */
    @Test
    void testGetAll()
    {
        stationDao.selectList(null);
    }


}

