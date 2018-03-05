package com.jfhealthcare.modules.system.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.base.PageResponse;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.common.entity.PublicKeyMap;
import com.jfhealthcare.common.enums.EncryptionEnum;
import com.jfhealthcare.common.enums.RedisEnum;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.utils.DateUtils;
import com.jfhealthcare.common.utils.RSAUtils;
import com.jfhealthcare.common.utils.RedisUtils;
import com.jfhealthcare.common.utils.TransferUtils;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.common.validator.ValidatorUtils;
import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.common.validator.group.Insert;
import com.jfhealthcare.modules.basics.BasicPageEntity;
import com.jfhealthcare.modules.system.annotation.AuthIgnore;
import com.jfhealthcare.modules.system.annotation.LoginUser;
import com.jfhealthcare.modules.system.annotation.SysLogAop;
import com.jfhealthcare.modules.system.entity.SysOperator;
import com.jfhealthcare.modules.system.entity.SysOperatorDtl;
import com.jfhealthcare.modules.system.mapper.SysOperatorMapper;
import com.jfhealthcare.modules.system.request.LoginRequest;
import com.jfhealthcare.modules.system.request.OrdinaryOperatorRequest;
import com.jfhealthcare.modules.system.request.SysOperatorAllRequest;
import com.jfhealthcare.modules.system.request.SysOperatorQueryRequest;
import com.jfhealthcare.modules.system.response.OperatorResponse;
import com.jfhealthcare.modules.system.service.SysOperatorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

import java.io.File;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;

/**
 * 用户管理
 * @author xujinma
 */
@Slf4j
@Api(value = "a用户->用户管理api")
@RestController
@RequestMapping("/v2/rmis/sysop/sysoperator")
public class SysOperatorController {

	@Autowired
	private SysOperatorService sysOperatorService;
	@Autowired
	private SysOperatorMapper sysOperatorMapper;
	@Autowired
	private RedisUtils redisUtils;
	
	@Value("${yun.image.sigpic}")
    private String sigpicPath;
	
	@Value("${yun.image.sys.path}")
	private String sysPsth;
	
	@Value("${rmis.evn.code}")
    private String evnCode;
	
	@AuthIgnore
	@ApiOperation(value = "登录", notes = "登录说明")
	@RequestMapping(method = RequestMethod.POST, value = "login")
	public BaseResponse login(@RequestBody LoginRequest loginRequest) throws Exception {
		ValidatorUtils.validateEntity(loginRequest);
		log.info("============:"+loginRequest.getLogincode()+":已登录！");
		String pwdreal = RSAUtils.decryptStringByJs(URLDecoder.decode(loginRequest.getPassword(), "UTF-8"));
		String encryptPassword = getEncryptPassword(loginRequest.getLogincode(), pwdreal);
		Map<String, Object> retmap = sysOperatorService.login(loginRequest.getLogincode(), encryptPassword);
		return BaseResponse.getSuccessResponse(retmap);
	}

	@ApiOperation(value = "登出", notes = "登出")
	@RequestMapping(method = RequestMethod.GET, value = "logout")
	public BaseResponse logout() {
		try {
			SecurityUtils.getSubject().logout();
		} catch (Exception e) {
			log.error("退出异常", e);
			return BaseResponse.getFailResponse("登出异常");
		}
		return BaseResponse.getSuccessResponse();
	}
	
	@AuthIgnore
	@ApiOperation(value = "注册", notes = "注册说明")
	@RequestMapping(method = RequestMethod.GET, value = "register")
	@ApiImplicitParams({ @ApiImplicitParam(name = "logincode", value = "账号", paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "password", value = "密码", paramType = "query", dataType = "string") })
	public BaseResponse register(@RequestParam String logincode, @RequestParam String password) throws Exception {
		
		try {
			Assert.isBlank(logincode, "账号不能为空！");
			Assert.isBlank(password, "密码不能为空！");
			String encryptPassword = getEncryptPassword(logincode, password);
			sysOperatorService.save(logincode,encryptPassword);
		} catch (Exception e) {
			log.error("注册失败异常！", e);
			return BaseResponse.getFailResponse("注册失败！");
		}
		return BaseResponse.getSuccessResponse("注册成功！");
	}
	
	@ApiOperation(value = "用户信息查询", notes = "用户信息查询")
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public BaseResponse login(@LoginUser LoginUserEntity loginUserEntity) throws Exception { 
		return BaseResponse.getSuccessResponse(loginUserEntity);
	}
	
	@ApiOperation(value = "账号是否重复查询", notes = "账号是否重复查询")
	@RequestMapping(method = RequestMethod.GET, value = "/judgelogin/{logincode}")
	public BaseResponse judgelogin(@PathVariable String logincode) throws Exception { 
		String status=sysOperatorService.judgelogin(logincode);
		return BaseResponse.getSuccessResponse((Object)status);
	}
	
	@AuthIgnore
	@ApiOperation(value = "获取私钥", notes = "获取私钥说明")
	@RequestMapping(method = RequestMethod.GET, value = "keyPair")
	public PublicKeyMap keyPair() throws Exception {
		PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
		return publicKeyMap;
	}
	
	@SysLogAop("新增用户")
	@ApiOperation(value = "新增用户", notes = "新增用户")
	@RequestMapping(method = RequestMethod.PUT)
	public BaseResponse saveOperator(@RequestBody SysOperatorAllRequest sysOperatorAllRequest, @ApiIgnore @LoginUser LoginUserEntity loginUserEntity)
			throws Exception {

		try {
			//TODO 判断ROOT用户的权限
			String roleIdd = sysOperatorAllRequest.getRoleIdd();
			String armariums = sysOperatorAllRequest.getArmariums();
			
			ValidatorUtils.validateEntity(sysOperatorAllRequest, Insert.class);
			SysOperator sysOperatorNew = new SysOperator();
			SysOperatorDtl sysOperatorDtlNew = new SysOperatorDtl();

			TransferUtils.copyPropertiesIgnoreNull(sysOperatorAllRequest, sysOperatorNew);
			TransferUtils.copyPropertiesIgnoreNull(sysOperatorAllRequest, sysOperatorDtlNew);
			//解密前端密码
			String pwdreal = RSAUtils.decryptStringByJs(URLDecoder.decode(sysOperatorNew.getPassword(), "UTF-8"));
			String encryptPassword = getEncryptPassword(sysOperatorNew.getLogincode(), pwdreal);
			sysOperatorNew.setPassword(encryptPassword);

			sysOperatorService.saveOperatorAndDtl(sysOperatorNew, sysOperatorDtlNew, loginUserEntity.getSysOperator().getLogincode(),roleIdd,armariums);

		}catch (Exception e) {
			log.error("新增失败异常！", e);
			return BaseResponse.getFailResponse("新增用户失败！");
		}
		return BaseResponse.getSuccessResponse("新增用户成功！");
	}
	
	@SysLogAop("删除用户")
	@ApiOperation(value = "删除用户", notes = "删除用户,并会将关联的角色数据删除，需要删除的IDs 放到路径中如：/v2/sysoperator/1,2,3")
	@RequestMapping(path="/{ids}",method = RequestMethod.DELETE)
	public BaseResponse deleteOperator(@PathVariable String ids)
			throws Exception {

		try {
			Assert.isBlank(ids, "ID不能为空!");
			String[] split = ids.split(",");
			sysOperatorService.deleteOperator(split);
		}catch (Exception e) {
			log.error("删除用户失败异常！", e);
			return BaseResponse.getFailResponse("删除用户失败！");
		}
		return BaseResponse.getSuccessResponse("删除用户成功！");
	}
	
	@SysLogAop("管理角色更新用户信息")
	@ApiOperation(value = "更新用户信息", notes = "更新用户信息 此接口用于ROOT等管理用户 使用 可以修改详情")
	@RequestMapping(path="/root",method = RequestMethod.POST)
	public BaseResponse updateOperator(@RequestBody SysOperatorAllRequest sysOperatorAllRequest,@ApiIgnore @LoginUser LoginUserEntity loginUserEntity) {
		try {
			sysOperatorService.updateOperator(sysOperatorAllRequest);
			return BaseResponse.getSuccessResponse();
		}catch (RmisException e) {
			log.error("更新用户信息失败!", e);
			return BaseResponse.getFailResponse(e.getMessage());
		}catch (Exception e) {
			log.error("更新用户信息失败!", e);
			return BaseResponse.getFailResponse("更新用户信息失败!");
		}
	}
	
	@SysLogAop("普通角色更新用户信息")
	@ApiOperation(value = "更新用户信息", notes = "更新用户信息 此接口用于普通用户使用 可以修改密码、主页等  operateType |1为修改密码  2为修改用户其他可修改参数 | id和operateType为必填参数")
	@RequestMapping(path="/ordinary",method = RequestMethod.POST)
	public BaseResponse updateOrdinaryOperator(@RequestBody OrdinaryOperatorRequest ooRequest,
			@ApiIgnore @LoginUser LoginUserEntity loginUserEntity) {
		try {
			//删除缓存
			redisUtils.delete(RedisEnum.LOGINCODE.getValue()+":"+loginUserEntity.getSysOperator().getLogincode());
			
			ValidatorUtils.validateEntity(ooRequest, Edit.class);
			if(StringUtils.isNotBlank(ooRequest.getOldPassword())&&StringUtils.isNotBlank(ooRequest.getNewPassword())){
				// 解密前端老密码
				String pwdrealOld = RSAUtils.decryptStringByJs(URLDecoder.decode(ooRequest.getOldPassword(), "UTF-8"));
				String encryptPasswordOld = getEncryptPassword(loginUserEntity.getSysOperator().getLogincode(), pwdrealOld);
				ooRequest.setOldPassword(encryptPasswordOld);
				// 解密前端新密码
				String pwdrealNew = RSAUtils.decryptStringByJs(URLDecoder.decode(ooRequest.getNewPassword(), "UTF-8"));
				String encryptPasswordNew = getEncryptPassword(loginUserEntity.getSysOperator().getLogincode(), pwdrealNew);
				ooRequest.setNewPassword(encryptPasswordNew);
			}
			sysOperatorService.updateOrdinaryOperator(ooRequest);
			return BaseResponse.getSuccessResponse();
		}catch (RmisException e) {
			log.error("更新普通用户信息失败!", e);
			return BaseResponse.getFailResponse(e.getMessage());
		}catch (Exception e) {
			log.error("更新普通用户信息失败!", e);
			return BaseResponse.getFailResponse("更新普通用户信息失败!");
		}
	}
	
	@ApiOperation(value = "查询用户信息", notes = "查询用户信息，这个接口用于用户列表展示")
	@RequestMapping(path="/all",method = RequestMethod.POST)
	public BaseResponse queryOperator(@RequestBody BasicPageEntity basic) {
		try {
			PageInfo<OperatorResponse> sysOpera = sysOperatorService.queryOperator(basic.getPageNum(), basic.getPageSize());
			return PageResponse.getSuccessPage(sysOpera);
		}catch (Exception e) {
			log.error("查询用户信息失败!", e);
			return BaseResponse.getFailResponse("查询用户信息失败!");
		}
	}
	
	@ApiOperation(value = "查询单个用户信息", notes = "查询单个用户信息，这个接口用于用户列表展示")
	@RequestMapping(path="/{logincode}",method = RequestMethod.GET)
	public BaseResponse queryOperator(@PathVariable String logincode) {
		try {
			OperatorResponse sysOpera = sysOperatorService.queryOperatorByLogincode(logincode);
			return BaseResponse.getSuccessResponse(sysOpera);
		}catch (Exception e) {
			log.error("查询单个用户信息失败!", e);
			return BaseResponse.getFailResponse("查询单个用户信息失败!");
		}
	}
	
	@ApiOperation(value = "条件查询用户信息", notes = "条件查询，这个接口用于用户列表展示")
	@RequestMapping(path="/one",method = RequestMethod.POST)
	public BaseResponse queryOperator(@RequestBody SysOperatorQueryRequest sysOqr) {
		try {
			PageInfo<OperatorResponse> operatorbp = sysOperatorService.queryOperatorByParameter(sysOqr);
			return PageResponse.getSuccessPage(operatorbp);
		}catch (Exception e) {
			log.error("条件查询用户信息失败!", e);
			return BaseResponse.getFailResponse("条件查询用户信息失败!");
		}
	}
	
	/**
	 * 上传签名图像
	 * 
	 * @return
	 */
	@ApiOperation(value = "上传签名图像", notes = "上传签名图像")
	@RequestMapping(value = "/signImage", method = RequestMethod.POST)
	public BaseResponse uploadSignImage(
			@RequestParam(value = "signImage", required = false) MultipartFile signImageFile,
			@ApiIgnore @LoginUser LoginUserEntity loginUserEntity) {
		try {
			if(signImageFile !=null){
				SysOperator sysOperator = loginUserEntity.getSysOperator();
				String signature = sysOperator.getSignature();
				
				String formatName="jpg";
				String originalFilename = signImageFile.getOriginalFilename();
				if(StringUtils.isNotEmpty(originalFilename)){
					String[] split = originalFilename.split("\\.");
					if(ArrayUtils.isNotEmpty(split)) {
						formatName = split[split.length-1];
					}
				}
				String sigpicpath=sigpicPath+DateUtils.dateToString(new Date(), DateUtils.patternB)+"_"+sysOperator.getLogincode()+"."+formatName;
				File file = new File(sysPsth+sigpicpath);
				signImageFile.transferTo(file);
				if(file.exists() && FileUtils.sizeOf(file)>0){
					sysOperator.setSignature(sigpicpath);
					sysOperatorMapper.updateByPrimaryKey(sysOperator);
					redisUtils.delete(RedisEnum.LOGINCODE.getValue()+":"+sysOperator.getLogincode());
					if(StringUtils.isNotEmpty(signature)) {
						FileUtils.deleteQuietly(new File(sysPsth+signature));
					}
				}else{
					return BaseResponse.getFailResponse("签名上传失败！");
				}
			}
		} catch (Exception e) {
			log.error("签名更新失败！", e);
			return BaseResponse.getFailResponse("签名更新失败！");
		}
		return BaseResponse.getSuccessResponse("签名更新成功！");
	}
	/**
     * 获取加密后的密码
     * @param password 明文密码
     * @param accountName 账户名称
     * @return
     */
    private  String getEncryptPassword(String accountName,String password){
			 return new SimpleHash(EncryptionEnum.ALGORITHMNAME.getValue(),password,
		    			ByteSource.Util.bytes(accountName + EncryptionEnum.SALT.getValue()),Integer.parseInt(EncryptionEnum.HASHITERATIONS.getValue())).toHex();
	 }
    
}
