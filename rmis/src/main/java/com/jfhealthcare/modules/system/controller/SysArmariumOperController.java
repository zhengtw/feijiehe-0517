package com.jfhealthcare.modules.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
* <p>Title: SysArmariumOperController</p>
* <p>Description: </p>
* <p>Company: jf</p> 
* @author jianglinyan
* @date 2017年9月27日下午4:06:03
*/
@Slf4j // 日志注解--lombok
@RestController
@Api(value = "仪器权限接口api")
@RequestMapping("/v2/rmis/sysop/sysArmariumOper")
public class SysArmariumOperController {
	//预留类
}
