package com.dicom.test;  

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;  
import org.springframework.http.HttpHeaders;  
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.jfhealthcare.common.utils.DateUtils;
import com.jfhealthcare.common.utils.HttpClientUtils;  
/** 
 * Spring RestTemplate工具类 
 *  
 * @packge com.wlyd.fmcgwms.util.api.RestTemplateUtils 
 * @date 2016年6月13日 下午4:14:23 
 * @author pengjunlin 
 * @comment 
 * @update 
 */  
public class RestTemplateUtils {  

    private static class SingletonRestTemplate {  
        /** 
         * 单例对象实例 
         */  
        static final RestTemplate INSTANCE = new RestTemplate();  
    }  

    private RestTemplateUtils() {  

    }  
      
    /** 
     * 单例实例 
     */  
    public static RestTemplate getInstance() {  
        return SingletonRestTemplate.INSTANCE;  
    }  

    /** 
     *  
     * 发送post请求 
     *  
     * @param url 
     *            请求URL地址 
     * @param data 
     *            json数据 
     * @param token 
     *            RSA加密token【RSA({PlatformCode+TenantCode+DateTime.Now()})//平台代码 
     *            +会员/租户代码+当前时间，然后进行RSA加密】 
     */  
    public static String post(String url, String data, String token)  
            throws Exception {  
        HttpHeaders headers = new HttpHeaders();  
        /*headers.add(HttpHeadersImpl.ACCEPT, "application/json"); 
        headers.add(HttpHeadersImpl.ACCEPT_ENCODING, "gzip"); 
        headers.add(HttpHeadersImpl.CONTENT_ENCODING, "UTF-8"); 
        headers.add(HttpHeadersImpl.CONTENT_TYPE, 
                "application/json; charset=UTF-8"); 
        headers.add(HttpHeadersImpl.COOKIE, token); 
        headers.add("Token", token);*/  
        headers.add("Accept", "application/json");  
        headers.add("Accpet-Encoding", "gzip");  
        headers.add("Content-Encoding", "UTF-8");  
        headers.add("Content-Type", "application/json; charset=UTF-8");  
        headers.add("Token", token);  

        HttpEntity<String> formEntity = new HttpEntity<String>(data, headers);  
        return RestTemplateUtils.getInstance().postForObject(url, formEntity, String.class);  
    }  
      
    /** 
     *  
     * 发送post请求 
     *  
     * @param url 
     *            请求URL地址 
     * @param data 
     *            json数据 
     * @param token 
     *            RSA加密token【RSA({PlatformCode+TenantCode+DateTime.Now()})//平台代码 
     *            +会员/租户代码+当前时间，然后进行RSA加密】 
     * @param platformCode 
     *            平台编码{平台分配} 
     * @param tenantCode 
     *            租户号{平台分配} 
     */  
    public static String post(String url, String data, String token,String platformCode,String tenantCode)  
            throws Exception {  
        HttpHeaders headers = new HttpHeaders();  
        /*headers.add(HttpHeadersImpl.ACCEPT, "application/json"); 
        headers.add(HttpHeadersImpl.ACCEPT_ENCODING, "gzip"); 
        headers.add(HttpHeadersImpl.CONTENT_ENCODING, "UTF-8"); 
        headers.add(HttpHeadersImpl.CONTENT_TYPE, 
                "application/json; charset=UTF-8"); 
        headers.add(HttpHeadersImpl.COOKIE, token); 
        headers.add("Token", token);*/  
        headers.add("Accept", "application/json");  
        headers.add("Accpet-Encoding", "gzip");  
        headers.add("Content-Encoding", "UTF-8");  
        headers.add("Content-Type", "application/json; charset=UTF-8");  
        headers.add("Token", token);  
        headers.add("PlatformCode", platformCode);  
        headers.add("TenantCode", tenantCode);  

        HttpEntity<String> formEntity = new HttpEntity<String>(data, headers);  
        return RestTemplateUtils.getInstance().postForObject(url, formEntity, String.class);  
    }  
      
    /** 
     * get根据url获取对象 
     */  
    public String get(String url) {  
        return RestTemplateUtils.getInstance().getForObject(url, String.class,  
                new Object[] {});  
    }  
    
    /** 
     * getById根据ID获取对象 
     */  
    public String getById(String url, String id) {  
        return RestTemplateUtils.getInstance().getForObject(url, String.class,  
                id);  
    }  

    /** 
     * post提交对象 
     */  
    public String post(String url, String data) {  
        return RestTemplateUtils.getInstance().postForObject(url, null,  
                String.class, data);  
    }  

    /** 
     * put修改对象 
     */  
    public void put(String url, String data) {  
        RestTemplateUtils.getInstance().put(url, null, data);  
    }  

    /** 
     * delete根据ID删除对象 
     */  
    public void delete(String url, String id) {  
        RestTemplateUtils.getInstance().delete(url, id);  
    }  
      
    public static void main(String[] args) {  
    	String url ="http://192.168.10.97:8080/dcm4chee-arc/aets/piclarc/rs/studies/1.2.826.0.1.3680043.2.461.6908835.3207647828/series/1.2.826.0.1.3680043.2.461.6908831.2750626085/instances/1.2.826.0.1.3680043.2.461.6908832.1617364220/metadata";  
    	RestTemplateUtils rt=new RestTemplateUtils();
    	String string = rt.get(url);
    	List<Map> parseArray = JSON.parseArray(string,Map.class);
    	
    	System.out.println(parseArray.get(0).toString());
    	System.out.println("=============");
    	System.out.println(((Map)parseArray.get(0).get("00101020")).toString());
    	
    	Map dv= (Map) parseArray.get(0).get("00101020");
    	System.out.println(dv.get("vr"));
//    	System.out.println(dv.getValue());
    	
    	
    	
    	
    	//    	List<Long> l1=new ArrayList<Long>();
//    	List<Long> l2=new ArrayList<Long>();
//    	for (int i = 0; i < 10; i++) {
//    		Date date1 = new Date();
//        	RestTemplateUtils rt=new RestTemplateUtils();
//        	String string = rt.get(url);
//        	Date date2 = new Date();
//        	l1.add(DateUtils.getTimes(date1,date2));
//        	System.out.println(string);
//		}
//    	System.out.println("=========================================");
//    	for (int i = 0; i < 10; i++) {
//    	Date date3 = new Date();
//    	HttpClientUtils instance = HttpClientUtils.getInstance();
//		String httpGet = instance.httpGetByWaitTime(url, 10000, 20000);
//		Date date4 = new Date();
//		System.out.println(httpGet);
//		System.out.println(DateUtils.getTimes(date3,date4));
//		l2.add(DateUtils.getTimes(date3,date4));
//    	}
		
//    	String jsonString = JSON.toJSONString(l1);
//    	String jsonString2 = JSON.toJSONString(l2);
//    	System.out.println(jsonString);
//    	System.out.println(jsonString2);
    }  

}  