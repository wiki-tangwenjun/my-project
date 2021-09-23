package com.wenjun.busines.house.service.impl;

import com.wenjun.busines.house.mapper.AppointmentHouseMapper;
import com.wenjun.busines.house.pojo.AppointmentHouse;
import com.wenjun.busines.house.service.IAppointmentHouseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @projectName: house
 * @package: com.wenjun.busines.house.service.impl
 * @className: AppointmentHouseService
 * @author: wen jun tang
 * @description: 预约看房服务接口实现类
 * @date: 2021年07月15日 18:00
 * @version: 1.0
 */
@Service
public class AppointmentHouseServiceImpl implements IAppointmentHouseService {
    @Resource
    private AppointmentHouseMapper appointmentHouseMapper;
    @Override
    public void add(AppointmentHouse entity) {
        appointmentHouseMapper.insert(entity);
    }

    @Override
    public void delete(Object id) {
        appointmentHouseMapper.deleteByPrimaryKey((String)id);
    }

    @Override
    public void update(AppointmentHouse entity) {
        appointmentHouseMapper.updateByPrimaryKey(entity);
    }

    @Override
    public AppointmentHouse findById(String id) {
        return appointmentHouseMapper.selectByPrimaryKey(id);
    }
}
