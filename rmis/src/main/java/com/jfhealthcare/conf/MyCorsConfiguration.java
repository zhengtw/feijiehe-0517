package com.jfhealthcare.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.net.HttpHeaders;

@Configuration
public class MyCorsConfiguration extends WebMvcConfigurerAdapter{
	@Override
	  public void addCorsMappings(CorsRegistry registry) {
//	    registry.addMapping("/**")
//	    .allowedOrigins("*")
//	    .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
//	    .allowedHeaders("*")
//	    .exposedHeaders("Set-Cookie")
//        .allowCredentials(true).maxAge(3600);
		 this._configCorsParams(registry.addMapping("/**"));
	  }
	
	

	  private void _configCorsParams(CorsRegistration corsRegistration) {
	          corsRegistration
	          .allowedOrigins("*")
	          .allowedMethods(HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),HttpMethod.DELETE.name(),HttpMethod.OPTIONS.name())
	          .allowedHeaders("*")
	          .exposedHeaders(HttpHeaders.SET_COOKIE)
	          .allowCredentials(true)
	          .maxAge(3600);
	  }


}
