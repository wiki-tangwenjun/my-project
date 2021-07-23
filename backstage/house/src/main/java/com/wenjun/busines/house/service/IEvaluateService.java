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

    /**
     * 根据条件查询评价总数
     *
     * @param evaluateParam 查询条件
     * @return java.util.List<com.wenjun.busines.house.pojo.Evaluate>
     * @author wen jun tang
     * @date 2021/7/23 15:03
     */
    Integer selectCountByAttributes(EvaluateParam evaluateParam);

    /**
     * 根据条件查询评价信息
     *
     * @param evaluateParam 查询条件
     * @return java.util.List<com.wenjun.busines.house.pojo.Evaluate>
     * @author wen jun tang
     * @date 2021/7/23 15:03
     */
    List<Evaluate> findByAttributes(EvaluateParam evaluateParam);
}
