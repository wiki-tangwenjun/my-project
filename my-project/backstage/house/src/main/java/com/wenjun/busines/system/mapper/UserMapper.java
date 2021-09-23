package com.wenjun.busines.system.mapper;

import com.wenjun.busines.system.dto.UserQueryParam;
import com.wenjun.busines.system.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByPersonName(@Param("personName") String personName);

    Integer selectCountByAttributes(UserQueryParam userQueryParam);

    List<User> selectByAttributes(UserQueryParam userQueryParam);
}
