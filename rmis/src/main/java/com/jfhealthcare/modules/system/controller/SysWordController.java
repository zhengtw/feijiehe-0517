package com.jfhealthcare.modules.system.controller;

import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.modules.system.annotation.AuthIgnore;
import com.jfhealthcare.modules.system.entity.SysWord;
import com.jfhealthcare.modules.system.service.SysWordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xujinma
 */
@Slf4j
@Api(value = "z转换->拼音五笔转换管理管理api")
@RestController
@RequestMapping("/v2/rmis/sysop/sysword")
public class SysWordController {

	@Autowired
	private SysWordService sysWordService;
	
	@AuthIgnore
	@ApiOperation(value = "简拼查询", notes = "简拼查询说明")
	@RequestMapping(method = RequestMethod.GET,path="/simple/{word}")
	public BaseResponse querySimpleSysWord(@PathVariable("word") String word) {
		try {
			Assert.isBlank(word, "转化的字符不能为空！");
			String decode = URLDecoder.decode(word,"UTF-8");
			SysWord querySysWord = sysWordService.querySimpleSysWord(decode);
//			log.info("转义前：{},转义后：{},返回结果：{}",word,decode,JSON.toJSONString(querySysWord));
			return BaseResponse.getSuccessResponse(querySysWord);
		} catch (Exception e) {
			return BaseResponse.getFailResponse("简码转换失败");
		}
	}
	
	@AuthIgnore
	@ApiOperation(value = "全拼查询", notes = "全拼查询说明")
	@RequestMapping(method = RequestMethod.GET,path="/full/{word}")
	public BaseResponse queryFullSysWord(@PathVariable("word") String word) {
		try {
			Assert.isBlank(word, "转化的字符不能为空！");
			String decode = URLDecoder.decode(word,"UTF-8");
			SysWord querySysWord = sysWordService.queryFullSysWord(decode);
			return BaseResponse.getSuccessResponse(querySysWord);
		} catch (Exception e) {
			return BaseResponse.getFailResponse("全拼转换失败");
		}
	}
	
	
	
	
}
