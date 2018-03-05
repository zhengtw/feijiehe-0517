package com.jfhealthcare;
import java.net.URLEncoder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jfhealthcare.common.utils.HttpClientUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestAiDemo {
	
	public static void main(String[] args) throws Exception {
//		String url ="http://180.167.46.105/rlm/check_api?html=%E5%8F%B3%E4%B8%8A%E8%82%BA%E5%B0%8F%E5%A7%90%E5%A7%90";
		
		String html="<p>1、片示<span style='color: rgb(0, 0, 255);'>髌骨多为二分髌骨，请结合临床以除外骨折可能，必要时CT检查；</span></p><p><span style='color: rgb(0, 0, 255);'>2、</span> 片示足部跖、趾骨未见明显骨折及脱位，请短期（7天）内复查，如症状持续或加重，行CT进一步检查以除外不全或隐匿性骨折。</p>";
		String encode = URLEncoder.encode(html,"UTF-8");
		String url ="http://180.167.46.105/rlm/check_api?html="+encode;
		System.out.println("===================");
		System.out.println(url);
		System.out.println("===================");
//		HttpClientUtils instance = HttpClientUtils.getInstance();
//		String httpGet = instance.httpGetByWaitTime(url, 10000, 20000);
//		System.out.println(httpGet);
		
//		StringHttpMessageConverter m = new StringHttpMessageConverter(Charset.forName("UTF-8"));  
//        RestTemplate restTemplate = new RestTemplateBuilder().additionalMessageConverters(m).build();  
//        HttpHeaders headers = new HttpHeaders();  
//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");  
//        headers.setContentType(type);  
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString()); 
        
		
		HttpClientUtils instance = HttpClientUtils.getInstance();
		String httpGet = instance.httpGetByWaitTime(url, 10000, 20000);
		System.out.println(httpGet);
		
//		RestTemplate rt=new RestTemplate();
//		rt.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("UTF-8")));
//		ResponseEntity<String> forEntity = rt.getForEntity(url, String.class);
//		String body = forEntity.getBody();
//		System.out.println(body);
	}
}
