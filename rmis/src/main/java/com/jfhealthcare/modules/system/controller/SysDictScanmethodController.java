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
import com.jfhealthcare.modules.system.entity.SysDictBodypart;
import com.jfhealthcare.modules.system.entity.SysDictScanmethod;
import com.jfhealthcare.modules.system.request.DictRequest;
import com.jfhealthcare.modules.system.request.SysDictBodypartRequest;
import com.jfhealthcare.modules.system.request.SysDictScanmethodRequest;
import com.jfhealthcare.modules.system.service.SysDictBodypartService;
import com.jfhealthcare.modules.system.service.SysDictScanmethodService;
import com.jfhealthcare.modules.system.service.SysDictService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xujinma
 */
@Slf4j 
@RestController
@Api(value = "z字典->字典检查方法接口api")
@RequestMapping("/v2/rmis/sysop/dictscanmethod")
public class SysDictScanmethodController {
	@Autowired
	private SysDictScanmethodService sysDictScanmethodService;

	/* 查询所有的字典目录信息 */
	@RequestMapping(path = "/all", method = RequestMethod.POST)
	@ApiOperation(value = "查询字典检查方法列表", notes = "查询字典检查方法列表详情")
	public BaseResponse queryDictScanmethod(@RequestBody SysDictScanmethodRequest sysDictScanmethodRequest) {
		try {
			PageInfo<SysDictScanmethod> dict =sysDictScanmethodService.queryDictScanmethod(sysDictScanmethodRequest);
			return PageResponse.getSuccessPage(dict);
		} catch (RmisException rmis) {
			log.info("查询字典部位列表失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("查询字典部位列表失败!", e);
			return BaseResponse.getFailResponse("查询字典部位列表失败!");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET,path="{id}")
	@ApiOperation(value = "查询单条数据", notes = "查询单条数据详情")
	public BaseResponse queryDictScanmethodById(@PathVariable String id) {
		try {
			SysDictScanmethod sysDictScanmethod=sysDictScanmethodService.queryDictScanmethodById(id);
			return BaseResponse.getSuccessResponse(sysDictScanmethod);
		}catch (Exception e) {
			log.error("查询单条数据失败!", e);
			return BaseResponse.getFailResponse("查询单条数据失败!");
		}
	}
	
	/* 删除字典目录信息 */
	@SysLogAop("删除字典检查方法")
	@ApiOperation(value = "删除字典检查方法", notes = "删除字典检查方法详情")
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public BaseResponse deleteDictScanmethod(@PathVariable String id) {
		try {
			Assert.isBlank(id, "id字段不能为空!");
			sysDictScanmethodService.deleteDictScanmethod(id);
			return BaseResponse.getSuccessResponse();
		} catch (RmisException rmis) {
			log.info("删除字典部位失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("删除字典部位失败!", e);
			return BaseResponse.getFailResponse("删除字典部位失败!");
		}
	}

	/* 更新字典目录信息 */
	@SysLogAop("更新字典部位")
	@ApiOperation(value = "更新字典检查方法", notes = "更新字典检查方法信息,ID必传!")
	@RequestMapping(method = RequestMethod.POST)
	public BaseResponse updateDictScanmethod(@RequestBody SysDictScanmethodRequest sysDictScanmethodRequest) {
		try {
			ValidatorUtils.validateEntity(sysDictScanmethodRequest, Edit.class);
			sysDictScanmethodService.updateDictScanmethod(sysDictScanmethodRequest);
			return BaseResponse.getSuccessResponse();
		} catch (RmisException rmis) {
			log.info("更新字典部位失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("更新字典部位失败!", e);
			return BaseResponse.getFailResponse("更新字典部位失败!");
		}
	}


	/* 新增字典目录信息 */
	@SysLogAop("新增字典检查方法")
	@ApiOperation(value = "新增字典检查方法", notes = "新增字典检查方法")
	@RequestMapping(method = RequestMethod.PUT)
	public BaseResponse saveDictScanmethod(@RequestBody SysDictScanmethodRequest sysDictScanmethodRequest) {
		try {
			sysDictScanmethodService.saveDictScanmethod(sysDictScanmethodRequest);
			return BaseResponse.getSuccessResponse();
		} catch (RmisException rmis) {
			log.info("新增字典检查方法失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("新增字典检查方法失败!", e);
			return BaseResponse.getFailResponse("新增字典检查方法失败!");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET,path="/nindex/{code}")
	@ApiOperation(value = "查询最大nindex", notes = "查询最大nindex详情")
	public BaseResponse queryMaxNindex(@PathVariable String code) {
		try {
			String nindex=sysDictScanmethodService.queryMaxNindex(code);
			return BaseResponse.getSuccessResponse((Object)nindex);
		}catch (Exception e) {
			log.error("查询最大nindex失败!", e);
			return BaseResponse.getFailResponse("查询最大nindex失败!");
		}
	}
}
