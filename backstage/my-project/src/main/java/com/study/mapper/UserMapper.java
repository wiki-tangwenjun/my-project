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

    Long selectMaxByAttributes(@Param("name") String name, @Param("idCard") String idCard, @Param("enabled") Long enabled);

    User selectByPersonName(@Param("personName") String personName);
    /**
     * @param personName 用户名
     * @param idNumber 证件号
     * @param enabled 是否启用
     * @param pageIndex 分页索引
     * @param pageSize 分页大小
     * @param orderProp 排序字段
     * @param order 排序方式
     * @return java.util.List<com.study.pojo.User>
     * @author wen jun tang
     * @date 2021/6/15 17:18
     */
    List<User> selectByAttributes(@Param("personName") String personName, @Param("idNumber") String idNumber, @Param("enabled") Long enabled,
                                  @Param("pageIndex") Long pageIndex, @Param("pageSize") Long pageSize,
                                  @Param("orderProp") String orderProp, @Param("order") String order);

}
