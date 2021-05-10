package com.study.mapper;

import com.study.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    void deleteByUserName(@Param("userName")String userName);
    
    User selectByUserName(@Param("userName")String userName);
    
    User selectByIdCard(@Param("idCard")String idCard);
    
    Long selectMaxByAttributes(@Param("name")String name,  @Param("idCard")String idCard, @Param("enabled")Long enabled);
    
   	List<User> selectByAttributes(@Param("name")String name, @Param("idCard")String idCard, @Param("enabled")Long enabled,
                                  @Param("pageIndex")Long pageIndex, @Param("pageSize")Long pageSize,
                                  @Param("orderProp")String orderProp, @Param("order")String order);

    
}