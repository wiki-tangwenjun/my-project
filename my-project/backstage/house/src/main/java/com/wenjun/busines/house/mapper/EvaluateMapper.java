package com.wenjun.busines.house.mapper;

import com.wenjun.busines.house.dto.EvaluateParam;
import com.wenjun.busines.house.pojo.Evaluate;

import java.util.List;

public interface EvaluateMapper {
    int deleteByPrimaryKey(String id);

    int insert(Evaluate record);

    int insertSelective(Evaluate record);

    Evaluate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Evaluate record);

    int updateByPrimaryKey(Evaluate record);

    Integer selectCountByAttributes(EvaluateParam evaluateParam);

    List<Evaluate> selectByAttributes(EvaluateParam evaluateParam);
}
