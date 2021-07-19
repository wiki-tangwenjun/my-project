package com.wenjun.busines.house.mapper;

import com.wenjun.busines.house.dto.HouseQueryParam;
import com.wenjun.busines.house.pojo.House;

import java.util.List;

public interface HouseMapper {
    int deleteByPrimaryKey(String id);

    int insert(House record);

    int insertSelective(House record);

    House selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(House record);

    int updateByPrimaryKey(House record);

    /**
     * 根据条件查询出租屋总数
     *
     * @param houseQueryParam 查询条件
     * @return java.util.List<com.wenjun.busines.house.pojo.House>
     * @author wen jun tang
     * @date 2021/7/19 17:29
     */
    Integer selectCountByAttributes(HouseQueryParam houseQueryParam);

    /**
     * 根据条件查询出租屋信息
     *
     * @param houseQueryParam 查询条件
     * @return java.util.List<com.wenjun.busines.house.pojo.House>
     * @author wen jun tang
     * @date 2021/7/19 17:29
     */
    List<House> selectByAttributes(HouseQueryParam houseQueryParam);

    /**
     * 根据用户id查找用户出租屋
     *
     * @param userId 查询条件
     * @return java.util.List<com.wenjun.busines.house.pojo.House>
     * @author wen jun tang
     * @date 2021/7/19 17:29
     */
    List<House> selectByUserId(String userId);
}
