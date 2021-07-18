package com.wenjun.busines.system.mapper;

import com.wenjun.busines.system.dto.OperateLogQueryParam;
import com.wenjun.busines.system.pojo.OperateLog;

import java.util.List;


public interface OperateLogMapper {

    int deleteByPrimaryKey(String id);

    int insert(OperateLog record);

    int insertSelective(OperateLog record);

    OperateLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OperateLog record);

    int updateByPrimaryKey(OperateLog record);

    int selectCountByAttributes(OperateLogQueryParam operateLogParam);

   	List<OperateLog> selectByAttributes(OperateLogQueryParam operateLogQueryParam);
}
