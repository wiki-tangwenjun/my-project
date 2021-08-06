package com.wenjun.busines.house.service.impl;

import com.wenjun.busines.house.dto.HouseAddParam;
import com.wenjun.busines.house.dto.HouseQueryParam;
import com.wenjun.busines.house.mapper.HouseEnclosureMapper;
import com.wenjun.busines.house.mapper.HouseLogMapper;
import com.wenjun.busines.house.mapper.HouseMapper;
import com.wenjun.busines.house.pojo.House;
import com.wenjun.busines.house.pojo.HouseEnclosure;
import com.wenjun.busines.house.service.IHouseService;
import com.wenjun.util.JWTUtil;
import com.wenjun.util.ServletHttpRequest;
import com.wenjun.util.TextUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    @Resource
    private HouseLogMapper houseLogMapper;
    @Resource
    private HouseEnclosureMapper houseEnclosureMapper;

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
    @Transactional(rollbackFor = Exception.class)
    public void insert(HttpServletRequest request, HouseAddParam houseAddParam) throws Exception {
        houseAddParam.getHouse().setId(TextUtil.getUUID());
        houseAddParam.getHouse().setUserId(JWTUtil.getUserId(ServletHttpRequest.getToken(request)));
        houseMapper.insert(houseAddParam.getHouse());

        houseAddParam.getHouseLog().setId(TextUtil.getUUID());
        houseAddParam.getHouseLog().setHourseId(houseAddParam.getHouse().getId());
        houseLogMapper.insert(houseAddParam.getHouseLog());

        List<HouseEnclosure> houseEnclosures = houseAddParam.getHouseEnclosure();
        for (HouseEnclosure houseEnclosure: houseEnclosures) {
            houseEnclosure.setId(TextUtil.getUUID());
            houseEnclosure.setHourseId(houseAddParam.getHouse().getId());
            houseEnclosureMapper.insert(houseEnclosure);
        }
    }
}
