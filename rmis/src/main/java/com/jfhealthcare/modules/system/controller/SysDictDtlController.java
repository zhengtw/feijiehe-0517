package com.jfhealthcare.modules.system.controller;

import java.util.Date;
import java.util.List;

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
import com.jfhealthcare.modules.system.entity.SysDictDtl;
import com.jfhealthcare.modules.system.entity.SysOperator;
import com.jfhealthcare.modules.system.request.DictDtlRequest;
import com.jfhealthcare.modules.system.service.SysDictDtlService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * Title: SysDictDtlController
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: jf
 * </p>
 * 
 * @author jianglinyan
 * @date 2017年9月20日下午8:18:27
 */
@Slf4j
@Api(value = "z字典->字典目录详情接口api")
@RestController
@RequestMapping("/v2/rmis/sysop/dictDtl")
public class SysDictDtlController {

	@Autowired
	private SysDictDtlService sysDictDtlService;

	/* 查询所有的字典目录详情信息 */
	@ApiOperation(value = "查询所有字典目录详情", notes ="查询所有字典目录详情")
	@RequestMapping(path = "/all", method = RequestMethod.POST)
	public BaseResponse queryDictDtl(@RequestBody BasicPageEntity basic) {
		try {
			PageInfo<SysDictDtl> dictDtl = sysDictDtlService.queryDictDtl(basic.getPageNum(), basic.getPageSize());
			return PageResponse.getSuccessPage(dictDtl);
		} catch (RmisException rmis) {
			log.info("查询字典目录详情失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("查询字典目录详情失败!", e);
			return BaseResponse.getFailResponse("查询字典目录详情失败!");
		}
	}
	
	/* 查询单个字典目录详情信息 */
	@ApiOperation(value = "查询单个字典目录详情", notes ="查询单个字典目录详情，查询条件为ID ID写在请求路径中")
	@RequestMapping(path="/{id}",method = RequestMethod.GET)
	public BaseResponse queryDictDtlById(@PathVariable String id) {
		try {
			Assert.isBlank(id, "id不能为空!");
			SysDictDtl dictDtl = sysDictDtlService.queryDictDtlById(id);
			return BaseResponse.getSuccessResponse(dictDtl);
		} catch (Exception e) {
			log.error("查询单个字典目录详情失败!", e);
			return BaseResponse.getFailResponse("查询单个字典目录详情失败!");
		}
	}
	
	/* 查询单个字典目录详情信息 */
	@ApiOperation(value = "查询code查询详情", notes ="查询code查询详情")
	@RequestMapping(path="/code",method = RequestMethod.POST)
	public BaseResponse queryDictDtlByCode(@RequestBody DictDtlRequest dictDtlRequest) {
		try {
			Assert.isBlank(dictDtlRequest.getCode(), "code不能为空!");
			List<SysDictDtl> sysDictDtls=sysDictDtlService.queryDictDtlByCode(dictDtlRequest.getCode());
			return BaseResponse.getSuccessResponse(sysDictDtls);
		} catch (Exception e) {
			log.error("查询code查询详情失败!", e);
			return BaseResponse.getFailResponse("查询code查询详情失败!");
		}
	}

	/* 删除字典目录详情信息 */
	@SysLogAop("删除字典目录详情")
	@ApiOperation(value = "删除字典目录详情", notes = "使用IDS删除字典目录详情，并会将关联的目录详情删除，需要删除的IDs 放到路径中如：/v2/dictDtl/1,2,3")
	@RequestMapping(path = "/{ids}", method = RequestMethod.DELETE)
	public BaseResponse deleteDictDtl(@PathVariable String ids) {
		try {
			Assert.isBlank(ids, "ids字段不能为空!");
			String[] split = ids.split(",");
			sysDictDtlService.deleteDictDtl(split);
			return BaseResponse.getSuccessResponse("删除字典目录详情成功!");
		} catch (RmisException rmis) {
			log.info("删除字典目录详情失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("删除字典目录详情失败!", e);
			return BaseResponse.getFailResponse("删除字典目录详情失败!");
		}
	}

	/* 更新字典目录详情信息 */
	@SysLogAop("更新字典目录")
	@ApiOperation(value = "更新字典目录", notes = "更新字典目录,ID为必传字段 ，其他字段看情况传 ，传的字段将会更改原先的值")
	@RequestMapping(method = RequestMethod.POST)
	public BaseResponse updateDictDtl(@RequestBody DictDtlRequest dictDtlRequest) {
		try {
			ValidatorUtils.validateEntity(dictDtlRequest, Edit.class);
			SysDictDtl sysDictDtl = new SysDictDtl();
			TransferUtils.copyPropertiesIgnoreNull(dictDtlRequest, sysDictDtl);
			sysDictDtl.setUpdTime(new Date());
			sysDictDtl.setUpdUser(NameUtils.getLoginCode());
			sysDictDtlService.updateDictDtl(sysDictDtl);
			return BaseResponse.getSuccessResponse();
		} catch (RmisException rmis) {
			log.info("更新字典目录详情失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("更新字典目录详情失败!", e);
			return BaseResponse.getFailResponse("更新字典目录详情失败!");
		}
	}

	/* 查询字典目录详情信息 --条件查询 */
	@ApiOperation(value = "查询字典目录详情", notes = "查询字典目录详情信息--条件查询（注意：五笔简拼和拼音简拼的查询都放在namepy的字段里面）")
	@RequestMapping(path = "/one", method = RequestMethod.POST)
	public BaseResponse queryDictDtlByKeys(@RequestBody DictDtlRequest dictDtlRequest) {
		try {
			SysDictDtl sysDictDtl = new SysDictDtl();
			TransferUtils.copyPropertiesIgnoreNull(dictDtlRequest, sysDictDtl);
			PageInfo<SysDictDtl> dictDtl = sysDictDtlService.selectDictDtlByKeys(sysDictDtl,
					dictDtlRequest.getPageNum(), dictDtlRequest.getPageSize());
			return PageResponse.getSuccessPage(dictDtl);
		} catch (RmisException rmis) {
			log.info("条件查询字典目录详情失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("条件查询字典目录失败!", e);
			return BaseResponse.getFailResponse("条件查询字典目录失败!");
		}
	}

	/* 新增字典目录信息 */
	@SysLogAop("新增字典目录信息")
	@ApiOperation(value = "新增字典目录详情", notes = "新增字典目录详情，ID 不需要传 ，ismodify,status需要传 默认为False")
	@RequestMapping(method = RequestMethod.PUT)
	public BaseResponse saveDictDtl(@RequestBody DictDtlRequest dictDtlRequest) {
		try {
			SysDictDtl sysDictDtl = new SysDictDtl();
			TransferUtils.copyPropertiesIgnoreNull(dictDtlRequest, sysDictDtl);
			sysDictDtl.setCrtTime(new Date());
			sysDictDtl.setCrtUser(NameUtils.getLoginCode());
			sysDictDtl.setIsdelete(Boolean.FALSE);
			sysDictDtlService.saveDictDtl(sysDictDtl);
			return BaseResponse.getSuccessResponse();
		} catch (RmisException rmis) {
			log.info("新增字典目录详情失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("新增查询字典目录详情失败!", e);
			return BaseResponse.getFailResponse("新增查询字典目录详情失败!");
		}
	}
}
