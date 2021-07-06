package com.study.system.service;

import com.study.system.dto.OperateLogQueryParam;
import com.study.system.pojo.OperateLog;

import java.util.Date;
import java.util.List;


/**
 * @author wen jun tang
 * @description: 日志查询接口
 * @date 2021/7/6 14:38
 */
public interface OperateLogService extends BaseService<OperateLog> {

    /**
     * 根据条件查询日志
     *
     * @param operateLogQueryParam 查询参数对象
     * @return java.util.List<com.study.system.pojo.OperateLog>
     * @author wen jun tang
     * @date 2021/7/6 14:38
     */
    List<OperateLog> findByAttributes(OperateLogQueryParam operateLogQueryParam);

}
