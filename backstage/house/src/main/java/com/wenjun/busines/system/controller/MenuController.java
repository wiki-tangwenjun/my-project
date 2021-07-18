package com.wenjun.busines.system.controller;

import com.wenjun.busines.system.service.IMenuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: house
 * @package: com.wenjun.busines.system.controller
 * @className: MenuController
 * @author: wen jun tang
 * @description: 菜单前端控制器
 * @date: 2021年07月16日 17:38
 * @version: 1.0
 */
@Slf4j
@RequestMapping("/menu")
@RestController
@Api(value = "菜单相关操作接口", tags = "菜单相关操作接口")
public class MenuController {
    private IMenuService iMenuService;


}
