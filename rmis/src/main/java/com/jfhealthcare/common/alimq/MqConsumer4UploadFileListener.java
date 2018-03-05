package com.jfhealthcare.common.alimq;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.modules.apply.service.ApplyMqInitService;
import com.jfhealthcare.modules.basics.AliMq;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MqConsumer4UploadFileListener implements MessageListener {
	
	@Autowired
	private ApplyMqInitService applyMqInitService;
	
	@Override
	public Action consume(Message message, ConsumeContext context) {
		String logMsg="MQ监听("+RandomStringUtils.randomNumeric(5)+"):";
		try {
			log.info(logMsg+"***************************mq接收到的信息开始***************************");
			byte[] body = message.getBody();
			if(!ObjectUtils.isEmpty(body)){
				AliMq aliMqMessage = JSON.parseObject(new String(body), AliMq.class);
				//dcm 解析成功
				String code = aliMqMessage.getCode();
				Assert.isBlank(code, "mq返回的code为空");
				String mesgeString = aliMqMessage.getMessage();
				Assert.isBlank(mesgeString, "mq返回的消息体为空");
				log.info(logMsg+"mq接收的消息体：{}",mesgeString);
				Map<String,Object> mesage = JSON.parseObject(mesgeString , Map.class);
				Assert.isNull(mesage, "mq返回的消息体json转对象为空");
				log.info(logMsg+"mq接收的code:{},message:{}",code,mesgeString);
				//code返回成功
				if("fileArcSuccess".equals(code)){
					String studyUID=Objects.toString(mesage.get("studyUID"),"");
					String seriesUID=Objects.toString(mesage.get("seriesUID"),"");
					String sopUID=Objects.toString(mesage.get("sopUID"),"");
					String userId=Objects.toString(mesage.get("userId"),"");
					Assert.isAnyBlank("mq返回成功，但sopUID,seriesUID,studyUID,userId有空值", sopUID,seriesUID,studyUID,userId);
					log.info(logMsg+"mq接收成功的信息调用现成处理开始，sopUID:{},seriesUID:{},studyUID:{},userId:{}",sopUID,seriesUID,studyUID,userId);
					applyMqInitService.initApply(sopUID,seriesUID,studyUID,userId);
					
				}else if("fileArcFailed".equals(code)){
				//code返回失败
					applyMqInitService.initErrorApply(mesage);
				}
			}else{
				log.error(logMsg+"接收到的信息失败,消息体为空");
				log.info(logMsg+"***************************接收到的信息结束***************************");
				return Action.ReconsumeLater;
			}
		} catch (RmisException e) {
			log.error(logMsg+"接收到的信息失败:{}",e.getMessage());
			log.info(logMsg+"***************************接收到的信息结束***************************");
			return Action.ReconsumeLater;
		}catch (Exception e) {
			log.error(logMsg+"接收到的信息失败",e);
			log.info(logMsg+"***************************接收到的信息结束***************************");
			return Action.ReconsumeLater;
		}
		log.info(logMsg+"***************************接收到的信息结束***************************");
		return Action.CommitMessage;
	}
	
}
