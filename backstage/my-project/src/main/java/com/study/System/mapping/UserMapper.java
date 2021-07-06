package com.study.system.mapping;

import com.study.system.dto.UserQueryParam;
import com.study.system.pojo.User;
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

    List<User> selectByAttributes(UserQueryParam userQueryParam);

}
