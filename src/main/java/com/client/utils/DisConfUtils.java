package com.client.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;

public class DisConfUtils {

	public static HttpResponse getHttpClient(String url,String username,String password,List<String> files) throws ClientProtocolException, IOException{
		
		CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_name", username);
        map.put("pass_word", password);
        map.put("files", files);
        
        String json = JSON.toJSONString(map);
        
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        
        HttpResponse response = client.execute(request);
        
        return response;
	}
	/**
	 *  查找 jar 包内的配置文件
	 *	ClassLoader classLoader = DisResourcePropertySrouce.class.getClassLoader();
     *  InputStream inputStream = classLoader.getResourceAsStream("application.properties");
     *  
	 * @return Properties
	 * @throws IOException
	 */
	public static Properties getProps(String filePath) throws IOException{
		
		Properties properties = new Properties();
		
		File file = ResourceUtils.getFile(filePath);
		
        properties.load(new FileInputStream(file));
        
        return properties;
	}
}
