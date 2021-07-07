package com.study.system.controller;

import com.study.system.service.IRoleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @projectName: my-project
 * @package: com.study.system.controller
 * @className: RoleController
 * @author: wen jun tang
 * @description: 角色管理前端控制器
 * @date: 2021年07月07日 9:56
 * @version: 1.0
 */
@RestController
@RequestMapping("/role")
@Api("角色管理接口")
public class RoleController {
    @Resource
    private IRoleService iRoleService;

}
