package com.study.service.impl;


import com.study.mapper.OperateLogMapper;
import com.study.pojo.OperateLog;
import com.study.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OperateLogServiceImpl implements OperateLogService {
    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public void add(OperateLog o) {
        operateLogMapper.insertSelective(o);
    }

    @Override
    public void update(OperateLog o) {
        operateLogMapper.updateByPrimaryKeySelective(o);
    }
    
    @Override
    public OperateLog findById(String id){
        return operateLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(OperateLog o) {
        operateLogMapper.deleteByPrimaryKey(o.getId());
    }

    @Override
    public Long findMaxByAttributes(String userName, String userId, String module, String style, Date beginTime, Date endTime) {
        return operateLogMapper.selectMaxByAttributes(userName, userId, module, style, beginTime, endTime);
    }

    @Override
    public List<OperateLog> findByAttributes(String userName, String userId, String module, String style, Date beginTime, Date endTime, Long pageIndex, Long pageSize, String orderProp, String order){
        return operateLogMapper.selectByAttributes(userName, userId, module, style, beginTime, endTime, pageIndex, pageSize, orderProp, order);
    }
}
