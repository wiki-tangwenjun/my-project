package com.wenjun.busines.house.service;

import com.wenjun.busines.house.dto.HouseAddParam;
import com.wenjun.busines.house.dto.HouseQueryParam;
import com.wenjun.busines.house.pojo.House;
import com.wenjun.busines.system.service.IService;

import javax.servlet.http.HttpServletRequest;
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
     * 新增出租屋及其附件
     *
     * @param houseAddParam 添加参数
     * @return void
     * @author wen jun tang
     * @date 2021/7/20 10:48
     */
    void insert(HttpServletRequest request, HouseAddParam houseAddParam) throws Exception;
}
