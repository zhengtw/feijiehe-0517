package com.jfhealthcare.modules.system.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.base.PageResponse;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.utils.NameUtils;
import com.jfhealthcare.common.utils.TransferUtils;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.common.validator.ValidatorUtils;
import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.modules.basics.BasicPageEntity;
import com.jfhealthcare.modules.system.annotation.SysLogAop;
import com.jfhealthcare.modules.system.entity.SysDict;
import com.jfhealthcare.modules.system.request.DictRequest;
import com.jfhealthcare.modules.system.service.SysDictService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jianglinyan
 * @date 2017年9月20日下午5:53:23
 */
@Slf4j // 日志注解--lombok
@RestController
@Api(value = "z字典->字典目录接口api")
@RequestMapping("/v2/rmis/sysop/dict")
public class SysDictController {
	@Autowired
	private SysDictService sysDictService;

	/* 查询所有的字典目录信息 */
	@RequestMapping(path = "/all", method = RequestMethod.POST)
	@ApiOperation(value = "查询字典目录", notes = "分页查询所有的字典目录")
	public BaseResponse queryDict(@RequestBody BasicPageEntity basic) {
		try {
			PageInfo<SysDict> dict = sysDictService.queryDict(basic.getPageNum(), basic.getPageSize());
			return PageResponse.getSuccessPage(dict);
		} catch (RmisException rmis) {
			log.info("查询字典目录失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("查询字典目录失败!", e);
			return BaseResponse.getFailResponse("查询字典目录失败!");
		}
	}
	
	/* 查询单个字典目录信息 */
	@RequestMapping(path="/{id}",method = RequestMethod.GET)
	@ApiOperation(value = "查询单个字典目录", notes = "查询单个字典目录，查询条件为ID ID写在路径中")
	public BaseResponse queryDictById(@PathVariable String id) {
		try {
			Assert.isBlank(id, "id不能为空!");
			SysDict dict = sysDictService.queryDictById(id);
			return BaseResponse.getSuccessResponse(dict);
		} catch (Exception e) {
			log.error("查询单个字典目录失败!", e);
			return BaseResponse.getFailResponse("查询单个字典目录失败!");
		}
	}

	/* 删除字典目录信息 */
	@SysLogAop("删除字典目录信息")
	@ApiOperation(value = "删除字典目录", notes = "使用IDS删除字典目录并会将关联的目录详情删除，需要删除的IDs 放到路径中如：/v2/dict/[1,2,3]")
	@RequestMapping(path = "/{ids}", method = RequestMethod.DELETE)
	public BaseResponse deleteDict(@PathVariable String ids) {
		try {
			Assert.isBlank(ids, "ids字段不能为空!");
			String[] split = ids.split(",");
			sysDictService.deleteDict(split);
			return BaseResponse.getSuccessResponse();
		} catch (RmisException rmis) {
			log.info("删除字典目录失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("删除字典目录失败!", e);
			return BaseResponse.getFailResponse("删除字典目录失败!");
		}
	}

	/* 更新字典目录信息 */
	@SysLogAop("更新字典目录")
	@ApiOperation(value = "更新字典目录", notes = "更新字典目录信息,ID必传!")
	@RequestMapping(method = RequestMethod.POST)
	public BaseResponse updateDict(@RequestBody DictRequest dictRequest) {
		try {
			ValidatorUtils.validateEntity(dictRequest, Edit.class);
			SysDict sysDict = new SysDict();
			TransferUtils.copyPropertiesIgnoreNull(dictRequest, sysDict);
			sysDict.setUpdTime(new Date());
			sysDict.setUpdUser(NameUtils.getLoginCode());
			sysDictService.updateDict(sysDict);
			return BaseResponse.getSuccessResponse();
		} catch (RmisException rmis) {
			log.info("更新字典目录失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("更新字典目录失败!", e);
			return BaseResponse.getFailResponse("更新字典目录失败!");
		}
	}

	/* 查询字典目录信息 --条件查询 */
	@ApiOperation(value = "查询字典目录", notes = "查询字典目录信息--条件查询（注意：五笔简拼和拼音简拼的查询都放在namepy的字段里面）")
	@RequestMapping(path = "/one", method = RequestMethod.POST)
	public BaseResponse queryDictByKeys(@RequestBody DictRequest dictRequest) {
		try {
			SysDict sysDict = new SysDict();
			TransferUtils.copyPropertiesIgnoreNull(dictRequest, sysDict);
			PageInfo<SysDict> dict = sysDictService.selectDictByKeys(sysDict, dictRequest.getPageNum(),
					dictRequest.getPageSize());
			return PageResponse.getSuccessPage(dict);
		} catch (RmisException rmis) {
			log.info("条件查询字典目录失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("条件查询字典目录失败!", e);
			return BaseResponse.getFailResponse("条件查询字典目录失败!");
		}
	}

	/* 新增字典目录信息 */
	@SysLogAop("新增字典目录")
	@ApiOperation(value = "新增字典目录", notes = "新增字典目录,ID 不需要传 ，ismodify,status需要传 默认为False")
	@RequestMapping(method = RequestMethod.PUT)
	public BaseResponse saveDict(@RequestBody DictRequest dictRequest) {
		try {
			SysDict sysDict = new SysDict();
			TransferUtils.copyPropertiesIgnoreNull(dictRequest, sysDict);
			sysDict.setCrtTime(new Date());
			sysDict.setCrtUser(NameUtils.getLoginCode());
			sysDict.setIsdelete(Boolean.FALSE);
			sysDictService.saveDict(sysDict);
			return BaseResponse.getSuccessResponse();
		} catch (RmisException rmis) {
			log.info("新增字典目录失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("新增字典目录失败!", e);
			return BaseResponse.getFailResponse("新增查询字典目录失败!");
		}
	}
}
