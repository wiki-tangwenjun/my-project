package com.wenjun.busines.house.mapper;

import com.wenjun.busines.house.pojo.HouseEnclosure;

public interface HouseEnclosureMapper {
    int deleteByPrimaryKey(String id);

    int insert(HouseEnclosure record);

    int insertSelective(HouseEnclosure record);

    HouseEnclosure selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HouseEnclosure record);

    int updateByPrimaryKey(HouseEnclosure record);
}
