package com.wenjun.busines.house.service.impl;

import com.wenjun.busines.house.mapper.HouseMapper;
import com.wenjun.busines.house.pojo.House;
import com.wenjun.busines.house.service.IHouseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @projectName: house
 * @package: com.wenjun.busines.house.service.impl
 * @className: HouseServiceImpl
 * @author: wen jun tang
 * @description: 房屋接口实现类
 * @date: 2021年07月15日 17:43
 * @version: 1.0
 */
@Service
public class HouseServiceImpl implements IHouseService {

    @Resource
    private HouseMapper houseMapper;

    @Override
    public void add(House entity) {
        houseMapper.insert(entity);
    }

    @Override
    public void delete(Object id) {
        houseMapper.deleteByPrimaryKey((String) id);
    }

    @Override
    public void update(House entity) {

    }

    @Override
    public House findById(String id) {
        return null;
    }
}
