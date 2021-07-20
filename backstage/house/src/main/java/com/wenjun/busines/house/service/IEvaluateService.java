package com.wenjun.busines.house.service;

import com.wenjun.busines.house.dto.EvaluateParam;
import com.wenjun.busines.house.pojo.Evaluate;
import com.wenjun.busines.system.service.IService;

import java.util.List;

/**
 * @projectName: house
 * @package: com.wenjun.busines.house.service
 * @className: IEvaluateService
 * @author: wen jun tang
 * @description: 评价表
 * @date: 2021年07月15日 17:41
 * @version: 1.0
 */
public interface IEvaluateService extends IService<Evaluate> {
    Integer selectCountByAttributes(EvaluateParam evaluateParam);

    List<Evaluate> selectByAttributes(EvaluateParam evaluateParam);
}
