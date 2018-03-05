package com.jfhealthcare.modules.apply.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.common.enums.CheckStatusEnum;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.utils.CallbackUtils;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.modules.apply.request.ApplyWorklistRequest;
import com.jfhealthcare.modules.apply.request.PrintWorklistRequest;
import com.jfhealthcare.modules.apply.response.ApplyWorklistResponse;
import com.jfhealthcare.modules.apply.response.PrintWorklistResponse;
import com.jfhealthcare.modules.apply.service.ApplyWorklistService;
import com.jfhealthcare.modules.business.entity.BusinChecklistIndex;
import com.jfhealthcare.modules.business.mapper.BusinChecklistIndexMapper;
import com.jfhealthcare.modules.business.service.ViewWorklistService;
import com.jfhealthcare.modules.system.annotation.LoginUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author xujinma
 */
@Slf4j
@Api(value = "a-apply->提供给基层客户端api")
@RestController
@RequestMapping(value="/v2/rmis/sysop/applyWorkList")
public class ApplyWorklistController {
	
	@Autowired
	private ApplyWorklistService applyWorklistService;
	
	@Autowired
	private BusinChecklistIndexMapper businChecklistIndexMapper;
	
	private static ExecutorService es = Executors.newFixedThreadPool(20);
	/**
	 * 基层申请页面查询
	 * @param ApplyWorklistRequest
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,path="/apply")
	@ApiOperation(value = "基层申请页面查询", notes = "基层申请页面查询详情")
	public BaseResponse queryApplyWorkList(@RequestBody ApplyWorklistRequest applyWorklistRequest){
		try {
			
			PageInfo<ApplyWorklistResponse> vwls=applyWorklistService.queryApplyWorkList(applyWorklistRequest);
			
			return BaseResponse.getSuccessResponse(vwls);
		}catch (Exception e) {
			log.error("基层申请页面查询失败！", e);
		}
		return BaseResponse.getFailResponse("基层申请页面查询失败！");
	}
	
	/**
	 * 基层打印页面查询
	 * @param ApplyWorklistRequest
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,path="/print")
	@ApiOperation(value = "基层打印页面查询", notes = "基层打印页面查询详情")
	public BaseResponse queryPrintWorkList(@RequestBody PrintWorklistRequest printWorklistRequest){
		try {
			PageInfo<PrintWorklistResponse> vwls=applyWorklistService.queryPrintWorklist(printWorklistRequest);
			return BaseResponse.getSuccessResponse(vwls);
		}catch (Exception e) {
			log.error("基层打印页面查询失败！", e);
		}
		return BaseResponse.getFailResponse("基层打印页面查询失败！");
	}
	
	/**
	 * 状态栏数据显示
	 * @param ApplyWorklistRequest
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,path="/printstatus")
	@ApiOperation(value = "状态栏数据显示查询", notes = "状态栏数据显示查询详情")
	public BaseResponse queryPrintWorkListStatus(@RequestBody PrintWorklistRequest printWorklistRequest){
		try {
			List<Map<String, Object>> statusNum = applyWorklistService.queryPrintWorkListStatus(printWorklistRequest);
			return BaseResponse.getSuccessResponse(statusNum);
		}catch (Exception e) {
			log.error("基层打印页面查询失败！", e);
		}
		return BaseResponse.getFailResponse("基层打印页面查询失败！");
	}
	
	/**
	 * 申请弹出框信息查询
	 * @param id
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,path="/{checkNum}")
	@ApiOperation(value = "申请弹出框信息查询", notes = "申请弹出框信息查询详情")
	public BaseResponse queryApplyWorkListById(@PathVariable("checkNum") String checkNum){
		try {
			ApplyWorklistResponse applyWorklistResponse=applyWorklistService.queryApplyWorkListById(checkNum);
			return BaseResponse.getSuccessResponse(applyWorklistResponse);
		}catch (Exception e) {
			log.error("申请弹出框信息查询失败！", e);
		}
		return BaseResponse.getFailResponse("申请弹出框信息查询失败！");
	}
	
	/**
	 * 基层申请页面申请接口
	 * @param ApplyWorklistRequest
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,path="/apply/view")
	@ApiOperation(value = "基层申请页面申请提交接口", notes = "基层申请页面申请提交接口详情")
	public BaseResponse updateApplyWorkListToView(@RequestBody ApplyWorklistRequest applyWorklistRequest,@ApiIgnore @LoginUser LoginUserEntity loginUserEntity){
		try {
			//提交后 改变申请状态
			applyWorklistService.updateApplyWorkListStatus(applyWorklistRequest,loginUserEntity);
			
			es.submit(new Runnable() {
				@Override
				public void run() {
					applyWorklistService.updateApplyWorkListToView(applyWorklistRequest,loginUserEntity);
				}
			});
			return BaseResponse.getSuccessResponse();
		}catch (RmisException e) {
			log.error("基层申请页面申请提交接口:{}", e.getMessage());
		}catch (Exception e) {
			log.error("基层申请页面申请提交接口", e);
		}
		return BaseResponse.getFailResponse("基层申请页面申请提交接口失败！");
	}
	
	/**
	 * 基层申请页面提示处理中接口
	 * @param ApplyWorklistRequest
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,path="/apply/remind/{checkNum}")
	@ApiOperation(value = "基层申请页面提示处理中接口", notes = "基层申请页面提示处理中接口详情,{0:不需要提示，1：提示}")
	public BaseResponse queryApplyWorkListToRemind(@PathVariable("checkNum") String checkNum){
		try {
			String remind=applyWorklistService.queryApplyWorkListToRemind(checkNum);
			return BaseResponse.getSuccessResponse((Object)remind);
		}catch (RmisException e) {
			log.error("基层申请页面提示处理中接口:{}", e.getMessage());
		}catch (Exception e) {
			log.error("基层申请页面提示处理中接口失败", e);
		}
		return BaseResponse.getFailResponse("基层申请页面提示处理中接口失败！");
	}
	
	/**
	 * 基层申请页面提交图像异常效验接口
	 * @param ApplyWorklistRequest
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,path="/apply/checkImage")
	@ApiOperation(value = "基层申请页面提交图像异常效验接口", notes = "基层申请页面提交图像异常效验接口详情,{0:没有异常图像，可以提交，1：有异常图像，不可以提交}")
	public BaseResponse queryApplyWorkToCheckImageByUids(@RequestBody ApplyWorklistRequest applyWorklistRequest){
		try {
			  
			List<String> instanceUids = applyWorklistRequest.getInstanceUids();
			Assert.isNull(instanceUids, "uids不能为空！");
			String flag=applyWorklistService.queryApplyWorkToCheckImageByUids(instanceUids);
			return BaseResponse.getSuccessResponse((Object)flag);
		}catch (RmisException e) {
			log.error("基层申请页面提交图像异常效验接口:{}", e.getMessage());
		}catch (Exception e) {
			log.error("基层申请页面提交图像异常效验接口", e);
		}
		return BaseResponse.getFailResponse("基层申请页面提交图像异常效验接口失败！");
	}
	
	/**
	 * 打印页面打印状态更改
	 * @param checkAccessionNum
	 * @return
	 */
	@GetMapping(path="/reportstatus/{checkAccessionNum}")
	@ApiOperation(value = "打印页面打印状态更改", notes = "打印页面打印状态更改详情")
	public BaseResponse updateApplyWorkPrintStatus(@PathVariable("checkAccessionNum") String checkAccessionNum,@LoginUser LoginUserEntity loginUserEntity){
		try {
			BusinChecklistIndex businChecklistIndex=new BusinChecklistIndex();
			businChecklistIndex.setAccessionNum(checkAccessionNum);
			businChecklistIndex.setStatus(CheckStatusEnum.COMPLETE_PRINT.getStatus());
			businChecklistIndex.setStatusCode(CheckStatusEnum.COMPLETE_PRINT.getStatusCode());
			businChecklistIndex.setPrintDr(loginUserEntity.getSysOperatorDtl().getName());
			businChecklistIndex.setPrintTime(new Date());
			businChecklistIndexMapper.updateByPrimaryKeySelective(businChecklistIndex);
			return BaseResponse.getSuccessResponse();
		}catch (Exception e) {
			log.error("打印页面打印状态更改失败！", e);
		}
		return BaseResponse.getFailResponse("打印页面打印状态更改失败！");
	}
}
