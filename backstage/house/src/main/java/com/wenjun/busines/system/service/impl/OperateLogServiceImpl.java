package com.wenjun.busines.system.service.impl;


import com.wenjun.busines.system.dto.OperateLogQueryParam;
import com.wenjun.busines.system.mapper.OperateLogMapper;
import com.wenjun.busines.system.pojo.OperateLog;
import com.wenjun.busines.system.service.OperateLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wen jun tang
 */
@Service
public class OperateLogServiceImpl implements OperateLogService {
    @Resource
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
    public List<OperateLog> findByAttributes(OperateLogQueryParam operateLogQueryParam) {
        return operateLogMapper.selectByAttributes(operateLogQueryParam);
    }
}
