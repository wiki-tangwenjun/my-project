package com.study.service;

import com.study.pojo.User;

import java.util.List;

/**
 * 用户相关接口对象
 *
 * @author tang wen jun
 * @date 2021/6/11 17:39
 */
public interface UserService extends BaseService<User> {

    /**
     * 根据用户名称查找记录
     *
     * @param userName
     * @return
     */
    User findByUserName(String userName);


    /**
     * 根据条件查找最大数
     *
     * @param userName
     * @param idCard
     * @param enabled
     * @return
     */
    Long findMaxByAttributes(String userName, String idCard, Long enabled);

    /**
     * 根据条件查找用户相关信息
     *
     * @param userName
     * @param idCard
     * @param enabled
     * @param pageIndex
     * @param pageSize
     * @param orderProp
     * @param order
     * @return
     */
    List<User> findByAttributes(String userName, String idCard, Long enabled, Long pageIndex, Long pageSize, String orderProp, String order);

}
