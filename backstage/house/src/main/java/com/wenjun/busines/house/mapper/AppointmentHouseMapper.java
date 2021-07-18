package com.wenjun.busines.house.mapper;

import com.wenjun.busines.house.pojo.AppointmentHouse;

public interface AppointmentHouseMapper {
    int deleteByPrimaryKey(String id);

    int insert(AppointmentHouse record);

    int insertSelective(AppointmentHouse record);

    AppointmentHouse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AppointmentHouse record);

    int updateByPrimaryKey(AppointmentHouse record);
}
