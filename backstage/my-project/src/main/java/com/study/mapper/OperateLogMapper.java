package com.study.mapper;

import java.util.Date;
import java.util.List;

import com.study.pojo.OperateLog;
import org.apache.ibatis.annotations.Param;


public interface OperateLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(OperateLog record);

    int insertSelective(OperateLog record);

    OperateLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OperateLog record);

    int updateByPrimaryKey(OperateLog record);
    
    Long selectMaxByAttributes(@Param("userName")String userName, @Param("userId")String userId, 
    		@Param("module")String module,@Param("style")String style, 
    		@Param("beginTime")Date beginTime, @Param("endTime")Date endTime);
    
   	List<OperateLog> selectByAttributes(@Param("userName")String userName, @Param("userId")String userId,
                                        @Param("module")String module, @Param("style")String style,
                                        @Param("beginTime")Date beginTime, @Param("endTime")Date endTime,
                                        @Param("pageIndex")Long pageIndex, @Param("pageSize")Long pageSize,
                                        @Param("orderProp")String orderProp, @Param("order")String order);
}