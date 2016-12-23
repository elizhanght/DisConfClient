package com.client.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.util.ResourceUtils;

public class DisConfUtils {

	public static HttpResponse getHttpClient(String url,String username,String password) throws ClientProtocolException, IOException{
		
		CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        
        String json ="{'user_name':'"+username+"','pass_word':'"+password+"'}";
        
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
