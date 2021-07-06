package com.study.system.service.impl;


import com.study.system.dto.OperateLogQueryParam;
import com.study.system.mapping.OperateLogMapper;
import com.study.system.pojo.OperateLog;
import com.study.system.service.OperateLogService;
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
