package com.wenjun.busines.house.service.impl;

import com.wenjun.busines.house.mapper.HouseEnclosureMapper;
import com.wenjun.busines.house.pojo.HouseEnclosure;
import com.wenjun.busines.house.service.IHouseEnclosureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @projectName: house
 * @package: com.wenjun.busines.house.service.impl
 * @className: HouseEnclosereServiceImpl
 * @author: wen jun tang
 * @description: 房屋附件接口实现类
 * @date: 2021年07月15日 17:58
 * @version: 1.0
 */
@Service
public class HouseEnclosereServiceImpl implements IHouseEnclosureService {
    @Resource
    private HouseEnclosureMapper houseEnclosureMapper;

    @Override
    public void add(HouseEnclosure entity) {
        houseEnclosureMapper.insert(entity);
    }

    @Override
    public void delete(Object id) {
        houseEnclosureMapper.deleteByPrimaryKey((String) id);
    }

    @Override
    public void update(HouseEnclosure entity) {
        houseEnclosureMapper.updateByPrimaryKey(entity);
    }

    @Override
    public HouseEnclosure findById(String id) {
        return houseEnclosureMapper.selectByPrimaryKey(id);
    }
}
