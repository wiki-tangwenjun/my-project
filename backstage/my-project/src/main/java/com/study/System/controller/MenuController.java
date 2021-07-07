package com.study.system.controller;

import com.study.system.service.IMenuService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @projectName: my-project
 * @package: com.study.system.controller
 * @className: MenuController
 * @author: wen jun tang
 * @description: 菜单管理前端控制器
 * @date: 2021年07月07日 9:57
 * @version: 1.0
 */
@RestController
@RequestMapping("/menu")
@Api("菜单管理接口")
public class MenuController {
    @Resource
    private IMenuService iMenuService;
    
}
