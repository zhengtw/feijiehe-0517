package com.jfhealthcare.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class Swagger2Config {

    @Bean
    public Docket createRestSystemApi() {
    	ParameterBuilder aParameterBuilder = new ParameterBuilder();
    	aParameterBuilder.name("token").defaultValue("320a3416067f4e654e26ef19d997f517")
    	.description("token测试用").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	ParameterBuilder bParameterBuilder = new ParameterBuilder();
    	bParameterBuilder.name("version").defaultValue("3.0.1.20180509")
    	.description("版本号").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	
    	Parameter parameter = aParameterBuilder.build();
    	Parameter parameterb = bParameterBuilder.build();
    	 List<Parameter> aParameters = new ArrayList<Parameter>();
    	 aParameters.add(parameter);
    	 aParameters.add(parameterb);
        return new Docket(DocumentationType.SWAGGER_2).groupName("system")
                .apiInfo(apiSysInfo())
                .globalOperationParameters(aParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jfhealthcare.modules.system.controller"))
                .paths(PathSelectors.any()) //过滤的接口
                .build();
    }
    
    @Bean
    public Docket createRestBusinessApi() {
    	ParameterBuilder aParameterBuilder = new ParameterBuilder();
    	aParameterBuilder.name("token").defaultValue("320a3416067f4e654e26ef19d997f517")
    	.description("token测试用").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	ParameterBuilder bParameterBuilder = new ParameterBuilder();
    	bParameterBuilder.name("version").defaultValue("3.0.1.20180509")
    	.description("版本号").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	
    	Parameter parameter = aParameterBuilder.build();
    	Parameter parameterb = bParameterBuilder.build();
    	 List<Parameter> aParameters = new ArrayList<Parameter>();
    	 aParameters.add(parameter);
    	 aParameters.add(parameterb);
        return new Docket(DocumentationType.SWAGGER_2).groupName("business")
                .apiInfo(apiBusInfo())
                .globalOperationParameters(aParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jfhealthcare.modules.business.controller"))
                .paths(PathSelectors.any()) //过滤的接口
                .build();
    }
    
    
    @Bean
    public Docket createRestApply() {
    	ParameterBuilder aParameterBuilder = new ParameterBuilder();
    	aParameterBuilder.name("token").defaultValue("320a3416067f4e654e26ef19d997f517")
    	.description("token测试用").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	ParameterBuilder bParameterBuilder = new ParameterBuilder();
    	bParameterBuilder.name("version").defaultValue("3.0.1.20180509")
    	.description("版本号").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	
    	Parameter parameter = aParameterBuilder.build();
    	Parameter parameterb = bParameterBuilder.build();
    	 List<Parameter> aParameters = new ArrayList<Parameter>();
    	 aParameters.add(parameter);
    	 aParameters.add(parameterb);
        return new Docket(DocumentationType.SWAGGER_2).groupName("apply")
                .apiInfo(apiApplyInfo())
                .globalOperationParameters(aParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jfhealthcare.modules.apply.controller"))
                .paths(PathSelectors.any()) //过滤的接口
                .build();
    }
    
    
    @Bean
    public Docket createRestBI() {
    	ParameterBuilder aParameterBuilder = new ParameterBuilder();
    	aParameterBuilder.name("token").defaultValue("164685c16f51fd9fa74052548b3da755")
    	.description("token测试用").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	Parameter parameter = aParameterBuilder.build();
    	 List<Parameter> aParameters = new ArrayList<Parameter>();
    	 aParameters.add(parameter);
        return new Docket(DocumentationType.SWAGGER_2).groupName("BI")
                .apiInfo(apiApplyInfo())
                .globalOperationParameters(aParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jfhealthcare.modules.BI.controller"))
                .paths(PathSelectors.any()) //过滤的接口
                .build();
    }
    
    @Bean
    public Docket createLabelApi() {
    	ParameterBuilder aParameterBuilder = new ParameterBuilder();
    	aParameterBuilder.name("token").defaultValue("320a3416067f4e654e26ef19d997f517")
    	.description("token测试用").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	ParameterBuilder bParameterBuilder = new ParameterBuilder();
    	bParameterBuilder.name("version").defaultValue("3.0.1.20180509")
    	.description("版本号").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	
    	Parameter parameter = aParameterBuilder.build();
    	Parameter parameterb = bParameterBuilder.build();
    	 List<Parameter> aParameters = new ArrayList<Parameter>();
    	 aParameters.add(parameter);
    	 aParameters.add(parameterb);
        return new Docket(DocumentationType.SWAGGER_2).groupName("label")
                .apiInfo(apiLabelInfo())
                .globalOperationParameters(aParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jfhealthcare.modules.label.controller"))
                .paths(PathSelectors.any()) //过滤的接口
                .build();
    }
    
    

    private ApiInfo apiLabelInfo() {
    	 return new ApiInfoBuilder()
                 .title("JF RESTful API/九峰开发接口文档 --系统API") //大标题
                 .description("HTTP状态码大全：http://tool.oschina.net/commons?type=5") //详细描述
//                 .termsOfServiceUrl("http://tool.oschina.net/commons?type=5")
                 .contact("程序员  GG") //作者
                 .version("1.0")
                 .build();
	}

	private ApiInfo apiSysInfo() {
        return new ApiInfoBuilder()
                .title("JF RESTful API/九峰开发接口文档 --系统API") //大标题
                .description("HTTP状态码大全：http://tool.oschina.net/commons?type=5") //详细描述
//                .termsOfServiceUrl("http://tool.oschina.net/commons?type=5")
                .contact("程序员  GG") //作者
                .version("1.0")
                .build();
    }

    private ApiInfo apiBusInfo() {
        return new ApiInfoBuilder()
                .title("JF RESTful API/九峰开发接口文档 --业务API") //大标题
                .description("HTTP状态码大全：http://tool.oschina.net/commons?type=5") //详细描述
//                .termsOfServiceUrl("http://tool.oschina.net/commons?type=5")
                .contact("程序员  GG") //作者
                .version("1.0")
                .build();
    }
    
    private ApiInfo apiApplyInfo() {
        return new ApiInfoBuilder()
                .title("JF RESTful API/九峰开发接口文档 --对基层web接口API") //大标题
                .description("HTTP状态码大全：http://tool.oschina.net/commons?type=5") //详细描述
//                .termsOfServiceUrl("http://tool.oschina.net/commons?type=5")
                .contact("程序员  GG") //作者
                .version("1.0")
                .build();
    }
    
    private ApiInfo apiBiInfo() {
        return new ApiInfoBuilder()
                .title("JF RESTful API/九峰开发接口文档 --BI统计API") //大标题
                .description("HTTP状态码大全：http://tool.oschina.net/commons?type=5") //详细描述
//                .termsOfServiceUrl("http://tool.oschina.net/commons?type=5")
                .contact("程序员  GG") //作者
                .version("1.0")
                .build();
    }
    
    
    
}