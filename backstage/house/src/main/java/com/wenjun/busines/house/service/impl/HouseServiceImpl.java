package com.wenjun.busines.house.service.impl;

import com.wenjun.busines.house.dto.HouseQueryParam;
import com.wenjun.busines.house.mapper.HouseMapper;
import com.wenjun.busines.house.pojo.House;
import com.wenjun.busines.house.service.IHouseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        houseMapper.updateByPrimaryKey(entity);
    }

    @Override
    public House findById(String id) {
        return houseMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer findCountByAttributes(HouseQueryParam houseQueryParam) {
        return houseMapper.selectCountByAttributes(houseQueryParam);
    }

    @Override
    public List<House> findByAttributes(HouseQueryParam houseQueryParam) {
        return houseMapper.selectByAttributes(houseQueryParam);
    }

    @Override
    public List<House> findByUserId(String userId) {
        return houseMapper.selectByUserId(userId);
    }

    @Override
    public void insert() {

    }
}
