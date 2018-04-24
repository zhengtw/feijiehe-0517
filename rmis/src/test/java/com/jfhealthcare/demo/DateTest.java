package com.jfhealthcare.demo;

import java.util.Date;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.jfhealthcare.common.properties.BtnPropertiesConfig;
import com.jfhealthcare.common.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DateTest {

	@Autowired
	private BtnPropertiesConfig btnPropertiesConfig;
	
	@Test
	public void configurationProperties() {
//		System.out.println("-----------------kaishi------------------");
//		long a=new Date().getTime();
//		Map<String, String> btnsMap = btnPropertiesConfig.getBtnsMap();
//		long b=new Date().getTime();
//        System.out.println(JSON.toJSONString(btnsMap));
//        long c=new Date().getTime();
//        System.out.println(b-a);
//        System.out.println(c-a);
//        System.out.println(c-b);
//        System.out.println("-----------------kaishi------------------");
    }
	

}
