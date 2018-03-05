package com.jfhealthcare;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "com.jfhealthcare.*.*.mapper")
public class RmisApplication {

	public static void main(String[] args) {
//		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Environment env = SpringApplication.run(RmisApplication.class, args).getEnvironment();
		log.info("\n----------------------------------------------------------\n\t" +
				"Application '{}' is running! Access URLs:\n\t" +
				"Local: \t\thttp://localhost:{}\n----------------------------------------------------------",
		env.getProperty("spring.application.name"),
		env.getProperty("server.port"));
		
	}
}
