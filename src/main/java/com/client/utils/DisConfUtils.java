package com.client.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.util.ResourceUtils;

public class DisConfUtils {

	public static HttpResponse getHttpClient(String url) throws ClientProtocolException, IOException{
		
		CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
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
