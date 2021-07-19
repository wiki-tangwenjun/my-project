package com.wenjun.busines.house.service;

import com.wenjun.busines.house.dto.HouseQueryParam;
import com.wenjun.busines.house.pojo.House;
import com.wenjun.busines.system.service.IService;

import java.util.List;

/**
 * @projectName: house
 * @package: com.wenjun.busines.house.service.impl
 * @className: IHouseService
 * @author: wen jun tang
 * @description: 房屋接口
 * @date: 2021年07月15日 17:37
 * @version: 1.0
 */
public interface IHouseService extends IService<House> {

    /**
     * 根据条件查询出租屋总数
     *
     * @param houseQueryParam 查询条件
     * @return java.util.List<com.wenjun.busines.house.pojo.House>
     * @author wen jun tang
     * @date 2021/7/19 17:29
     */
    Integer findCountByAttributes(HouseQueryParam houseQueryParam);

    /**
     * 根据条件查询出租屋信息
     *
     * @param houseQueryParam 查询条件
     * @return java.util.List<com.wenjun.busines.house.pojo.House>
     * @author wen jun tang
     * @date 2021/7/19 17:29
     */
    List<House> findByAttributes(HouseQueryParam houseQueryParam);

    /**
     * 根据用户id查询用户出租屋
    * @author wen jun tang
    * @param userId 用户id
    * @return java.util.List<com.wenjun.busines.house.pojo.House>
    * @date 2021/7/19 18:29
    */
    List<House> findByUserId(String userId);

    void insert();
}
