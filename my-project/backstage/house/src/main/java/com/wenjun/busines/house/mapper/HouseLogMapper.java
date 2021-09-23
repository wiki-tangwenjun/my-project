package com.wenjun.busines.house.mapper;

import com.wenjun.busines.house.pojo.HouseLog;

public interface HouseLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(HouseLog record);

    int insertSelective(HouseLog record);

    HouseLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HouseLog record);

    int updateByPrimaryKey(HouseLog record);
}