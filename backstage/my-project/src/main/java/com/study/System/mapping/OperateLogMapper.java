package com.study.system.mapping;

import java.util.List;

import com.study.system.dto.OperateLogQueryParam;
import com.study.system.pojo.OperateLog;

public interface OperateLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(OperateLog record);

    int insertSelective(OperateLog record);

    OperateLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OperateLog record);

    int updateByPrimaryKey(OperateLog record);

   	List<OperateLog> selectByAttributes(OperateLogQueryParam operateLogQueryParam);
}
