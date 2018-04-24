package com.jfhealthcare.common.utils;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 
 *
 */
@Component
@Slf4j
public class HttpClientUtils {
	 /** 
     * 最大线程池 
     */  
    public static final int THREAD_POOL_SIZE = 5; 
    
    
    public static int One_Second = 1000;  
    
    public static int One_Minute = 1000*60;  
  
    public interface HttpClientDownLoadProgress {  
        public void onProgress(int progress);  
    }  
  
    private static HttpClientUtils httpClientDownload;  
  
    private ExecutorService downloadExcutorService;  
  
    private HttpClientUtils() {  
        downloadExcutorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);  
    }  
  
    public static HttpClientUtils getInstance() {  
        if (httpClientDownload == null) {  
            httpClientDownload = new HttpClientUtils();  
        }  
        return httpClientDownload;  
    }  
  
    /** 
     * 下载文件 
     *  
     * @param url 
     * @param filePath 
     */  
    public void download(final String url, final String filePath) {  
        downloadExcutorService.execute(new Runnable() {  
  
            @Override  
            public void run() {  
                httpDownloadFile(url, filePath, null, null);  
            }  
        });  
    }  
  
    /** 
     * 下载文件 
     *  
     * @param url 
     * @param filePath 
     * @param progress 
     *            进度回调 
     */  
    public void download(final String url, final String filePath,  
            final HttpClientDownLoadProgress progress) {  
        downloadExcutorService.execute(new Runnable() {  
  
            @Override  
            public void run() {  
                httpDownloadFile(url, filePath, progress, null);  
            }  
        });  
    }  
    
    
    private void httpDownloadFile(String url, String filePath,  
            HttpClientDownLoadProgress progress, Map<String, String> headMap) {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            HttpGet httpGet = new HttpGet(url);  
            setGetHead(httpGet, headMap);  
            CloseableHttpResponse response1 = httpclient.execute(httpGet);  
            try {  
                System.out.println(response1.getStatusLine());  
                HttpEntity httpEntity = response1.getEntity();  
                long contentLength = httpEntity.getContentLength();  
                InputStream is = httpEntity.getContent();  
                // 根据InputStream 下载文件  
                ByteArrayOutputStream output = new ByteArrayOutputStream();  
                byte[] buffer = new byte[4096];  
                int r = 0;  
                long totalRead = 0;  
                while ((r = is.read(buffer)) > 0) {  
                    output.write(buffer, 0, r);  
                    totalRead += r;  
                    if (progress != null) {// 回调进度  
                        progress.onProgress((int) (totalRead * 100 / contentLength));  
                    }  
                }  
                FileOutputStream fos = new FileOutputStream(filePath);  
                output.writeTo(fos);  
                output.flush();  
                output.close();  
                fos.close();  
                EntityUtils.consume(httpEntity);  
            } finally {  
                response1.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    /**
 	 * 根据路径获取真实的图像名称
 	 * @param sopPath
 	 * @return
 	 */
 	private static String getSopName(String sopPath){
 		if(StringUtils.isEmpty(sopPath)) {
 			return null;
 		} 
 		String[] strs = sopPath.split("/");
 		return strs[strs.length-1];
 	}
    
    
    public  boolean down(String host,String sopUrl,HttpServletRequest request,HttpServletResponse response){
    	    CloseableHttpClient httpclient = HttpClients.createDefault();  
  	  try{
//  		     URL url = new URL(host + sopUrl);  
//		      HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();  
//			  // 设置 User-Agent  
//			  httpConnection.setRequestProperty("User-Agent","NetFox");  
//		      // 设置断点续传的开始位置  
//		      httpConnection.setRequestProperty("RANGE","bytes=2000070");  
//		      httpConnection.setRequestProperty("Accept-Encoding", "dcm");
//		      httpConnection.setConnectTimeout(20000);
//		      httpConnection.setReadTimeout(20000);
//		      httpConnection.setRequestMethod("GET"); 
  		     HttpGet httpGet = new HttpGet(host+sopUrl);  
  		     Map<String,String> map = new HashMap<String, String>();
//  		     map.put("User-Agent", "NetFox");
//  		     map.put("RANGE","bytes=2000070");
             setGetHead(httpGet, map);
             CloseableHttpResponse response1 = httpclient.execute(httpGet);  
             
             HttpEntity httpEntity = response1.getEntity();  
             long contentLength = httpEntity.getContentLength();  
             InputStream input = httpEntity.getContent();  
		      // 获得输入流  
//		      InputStream input = httpConnection.getInputStream(); 
		      RandomAccessFile oSavedFile = new RandomAccessFile("D:\\DicomViewer V1.3\\tmp\\" + getSopName(sopUrl),"rw");  
		      long nPos = 0;  
		      // 定位文件指针到 nPos 位置  
		      oSavedFile.seek(nPos);  
		      byte[] b = new byte[1024];  
		      int nRead;  
		      // 从输入流中读入字节流，然后写到文件中  
		      while((nRead=input.read(b,0,1024)) > 0) {
		    	  oSavedFile.write(b,0,nRead);  
		      }
		      oSavedFile.close();
		      input.close();
  	  } catch(Exception e){
  		  e.printStackTrace();
  		  return false;
  	  } 
  	    return true;
   } 
    
    public boolean downFile(String url, String filePath,Map<String, String> headMap) {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        long percent = 0L;
        try {  
            HttpGet httpGet = new HttpGet(url);  
            setGetHead(httpGet, headMap);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);  
            try {  
                System.out.println(response1.getStatusLine());  
                HttpEntity httpEntity = response1.getEntity();  
                long contentLength = httpEntity.getContentLength();  
                InputStream is = httpEntity.getContent();  
                // 根据InputStream 下载文件  
                ByteArrayOutputStream output = new ByteArrayOutputStream();  
                byte[] buffer = new byte[4096];  
                int r = 0;  
                long totalRead = 0;  
                while ((r = is.read(buffer)) > 0) {  
                    output.write(buffer, 0, r);  
                    totalRead += r;  
                    percent = totalRead * 100 / contentLength;  
                }  
                FileOutputStream fos = new FileOutputStream(filePath);  
                output.writeTo(fos);  
                output.flush();  
                output.close();  
                fos.close();  
                EntityUtils.consume(httpEntity); 
            } finally {  
                response1.close();  
            }  
            return true;
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    
    /** 
     * 同步下载文件 
     * @param url 
     * @param filePath 
     */  
    public boolean syncHttpDownloadFile(String url, String filePath,Map<String, String> headMap) {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        long percent = 0L;
        try {  
            HttpGet httpGet = new HttpGet(url);  
            setGetHead(httpGet, headMap);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);  
            try {  
                System.out.println(response1.getStatusLine());  
                HttpEntity httpEntity = response1.getEntity();  
                long contentLength = httpEntity.getContentLength();  
                InputStream is = httpEntity.getContent();  
                // 根据InputStream 下载文件  
                ByteArrayOutputStream output = new ByteArrayOutputStream();  
                byte[] buffer = new byte[4096];  
                int r = 0;  
                long totalRead = 0;  
                while ((r = is.read(buffer)) > 0) {  
                    output.write(buffer, 0, r);  
                    totalRead += r;  
                    percent = totalRead * 100 / contentLength;  
                }  
                FileOutputStream fos = new FileOutputStream(filePath);  
                output.writeTo(fos);  
                output.flush();  
                output.close();  
                fos.close();  
                EntityUtils.consume(httpEntity); 
            } finally {  
                response1.close();  
            }  
            return true;
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /** 
     * get请求 
     *  
     * @param url 
     * @return 
     */  
    public String httpGet(String url) {  
        return httpGet(url, null);  
    }  
  
    /** 
     * http get请求 
     * @param url 
     * @return 
     */  
    public String httpGet(String url, Map<String, String> headMap) {  
        String responseContent = null;  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            HttpGet httpGet = new HttpGet(url);  
            CloseableHttpResponse response1 = httpclient.execute(httpGet);  
            setGetHead(httpGet, headMap);  
            try {  
                System.out.println(response1.getStatusLine());  
                HttpEntity entity = response1.getEntity();  
                responseContent = getRespString(entity);  
                System.out.println("debug:" + responseContent);  
                EntityUtils.consume(entity);  
            } finally {  
                response1.close();  
            }  
        } catch (Exception e) {  
            log.error("===========请求异常：{}===========",url);
        } finally {  
        	 try {  
             	if(httpclient!=null) {
             		httpclient.close(); 
             	}
             } catch (IOException e) {  
             	log.error("关闭失败", e);
             }  
        }  
        return responseContent;  
    }  
  
    public String httpPost(String url, Map<String, String> paramsMap) {  
        return httpPost(url, paramsMap, null);  
    }  
  
    public String httpGetByWaitTime(String url,int connectTimeout,int socketTimeout) {  
        String responseContent = null;  
        CloseableHttpResponse response1 =null;
        CloseableHttpClient httpclient = null;  
        try {  
            HttpGet httpGet = new HttpGet(url); 
            RequestConfig requestConfig = RequestConfig.custom()    
                    .setConnectTimeout(connectTimeout)    
                    .setSocketTimeout(socketTimeout).build();    
            httpGet.setConfig(requestConfig); 
            httpclient = HttpClients.createDefault();  
            response1 = httpclient.execute(httpGet);
            setGetHead(httpGet, null);  
            HttpEntity entity = response1.getEntity();  
            responseContent = getRespString(entity);  
            EntityUtils.consume(entity);  
        } catch (Exception e) {  
        	log.error("===========请求连接超时或处理超时===========");
        	responseContent=null;
        } finally {  
            try {  
            	if(httpclient!=null) {
            		httpclient.close(); 
            	}
                if(response1!=null) {
                	response1.close();
                }
            } catch (IOException e) {  
            	log.error("关闭失败", e);
            }  
        }  
        return responseContent;  
    } 
    
    /** 
     * http的post请求 
     *  
     * @param url 
     * @param paramsMap 
     * @return 
     */  
    public String httpPost(String url, Map<String, String> paramsMap,  
            Map<String, String> headMap) {  
        String responseContent = null;  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            HttpPost httpPost = new HttpPost(url);  
            setPostHead(httpPost, headMap);  
            setPostParams(httpPost, paramsMap);  
            CloseableHttpResponse response = httpclient.execute(httpPost);  
            try {  
                System.out.println(response.getStatusLine());  
                HttpEntity entity = response.getEntity();  
                responseContent = getRespString(entity);  
                EntityUtils.consume(entity);  
            } finally {  
                response.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        System.out.println("responseContent = " + responseContent);  
        return responseContent;  
    }  
  
    /** 
     * 设置POST的参数 
     *  
     * @param httpPost 
     * @param paramsMap 
     * @throws Exception 
     */  
    private void setPostParams(HttpPost httpPost, Map<String, String> paramsMap)  
            throws Exception {  
        if (paramsMap != null && paramsMap.size() > 0) {  
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
            Set<String> keySet = paramsMap.keySet();  
            for (String key : keySet) {  
                nvps.add(new BasicNameValuePair(key, paramsMap.get(key)));  
            }  
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));  
        }  
    }  
  
    /** 
     * 设置http的HEAD 
     *  
     * @param httpPost 
     * @param headMap 
     */  
    private void setPostHead(HttpPost httpPost, Map<String, String> headMap) {  
        if (headMap != null && headMap.size() > 0) {  
            Set<String> keySet = headMap.keySet();  
            for (String key : keySet) {  
                httpPost.addHeader(key, headMap.get(key));  
            }  
        }  
    }  
  
    /** 
     * 设置http的HEAD 
     *  
     * @param httpGet 
     * @param headMap 
     */  
    private void setGetHead(HttpGet httpGet, Map<String, String> headMap) {  
        if (headMap != null && headMap.size() > 0) {  
            Set<String> keySet = headMap.keySet();  
            for (String key : keySet) {  
                httpGet.addHeader(key, headMap.get(key));  
            }  
        }  
    }  
  
    /** 
     * 将返回结果转化为String 
     *  
     * @param entity 
     * @return 
     * @throws Exception 
     */  
    private String getRespString(HttpEntity entity) throws Exception {  
        if (entity == null) {  
            return null;  
        }  
        InputStream is = entity.getContent();  
        StringBuffer strBuf = new StringBuffer();  
        byte[] buffer = new byte[4096];  
        int r = 0;  
        while ((r = is.read(buffer)) > 0) {  
            strBuf.append(new String(buffer, 0, r, "UTF-8"));  
        }  
        return strBuf.toString();  
    }  
}
