package com.wenjun.busines.house.mapper;

import com.wenjun.busines.house.pojo.Evaluate;

public interface EvaluateMapper {
    int deleteByPrimaryKey(String id);

    int insert(Evaluate record);

    int insertSelective(Evaluate record);

    Evaluate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Evaluate record);

    int updateByPrimaryKey(Evaluate record);
}
