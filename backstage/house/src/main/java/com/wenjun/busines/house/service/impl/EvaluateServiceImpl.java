package com.wenjun.busines.house.service.impl;

import com.wenjun.busines.house.dto.EvaluateParam;
import com.wenjun.busines.house.mapper.EvaluateMapper;
import com.wenjun.busines.house.pojo.Evaluate;
import com.wenjun.busines.house.service.IEvaluateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @projectName: house
 * @package: com.wenjun.busines.house.service.impl
 * @className: EvaluateServiceImpl
 * @author: wen jun tang
 * @description: 评价服务接口实现lei
 * @date: 2021年07月15日 17:55
 * @version: 1.0
 */
@Service
public class EvaluateServiceImpl implements IEvaluateService {
    @Resource
    private EvaluateMapper evaluateMapper;

    @Override
    public void add(Evaluate entity) {
        evaluateMapper.insert(entity);
    }

    @Override
    public void delete(Object id) {
        evaluateMapper.deleteByPrimaryKey((String)id);
    }

    @Override
    public void update(Evaluate entity) {
        evaluateMapper.updateByPrimaryKey(entity);
    }

    @Override
    public Evaluate findById(String id) {
        return evaluateMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer selectCountByAttributes(EvaluateParam evaluateParam) {
        return evaluateMapper.selectCountByAttributes(evaluateParam);
    }

    @Override
    public List<Evaluate> findByAttributes(EvaluateParam evaluateParam) {
        return evaluateMapper.selectByAttributes(evaluateParam);
    }

}
