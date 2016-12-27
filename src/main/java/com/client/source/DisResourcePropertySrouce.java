package com.client.source;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.client.utils.DisConfUtils;
import com.client.utils.DisEnum;

public class DisResourcePropertySrouce extends PropertiesPropertySource{

	static Logger logger = Logger.getLogger(DisResourcePropertySrouce.class);
	/**
	 * Create a PropertySource based on Properties loaded from the given resource.
	 * The name of the PropertySource will be generated based on the
	 * {@link Resource#getDescription() description} of the given resource.
	 */
	public DisResourcePropertySrouce(String name,EncodedResource resource,DisEnum disEnum) throws IOException {
		super(getNameForResource(resource.getResource()), loadProperties(resource,disEnum));
	}
	/**
	 * Return the description for the given Resource; if the description is
	 * empty, return the class name of the resource plus its identity hash code.
	 * @see org.springframework.core.io.Resource#getDescription()
	 */
	private static String getNameForResource(Resource resource) {
		String name = resource.getDescription();
		if (!StringUtils.hasText(name)) {
			name = resource.getClass().getSimpleName() + "@" + System.identityHashCode(resource);
		}
		return name;
	}
	
	private static Properties loadProperties(EncodedResource resource,DisEnum disEnum){
		
		Properties props = new Properties();
		
		String filename = resource.getResource().getFilename();
		
		String type = disEnum.TYPE();
		
		if (type.equals(DisEnum.HTTP.TYPE())) { // 通过 HTTP 方式获取配置文件信息
			loadFromHttp(filename,props);
		} else if(type.equals(DisEnum.HTTP.TYPE())) {	// 通过 DUBBO 方式获取配置文件信息
			loadFromDubbo(filename,props);	
		}else if(type.equals(DisEnum.REDIS.TYPE())){	// 直接连接 Redis 获取数据
			loadFromRedis(filename,props);
		}
		return props;
	}
	
	/**
	 * //http://localhost:8080/api/property/
	 * @param project
	 * @param filename
	 * @param props
	 */
	@SuppressWarnings("unchecked")
	private static void loadFromHttp(String filename,Properties props){
		
        try {
            
            Properties properties = DisConfUtils.getProps("classpath:application.properties");
            
            String path = properties.getProperty("dis_conf_path");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            
            String url = path + filename;
            
            logger.info("url - " + url);
            
            HttpResponse response = DisConfUtils.getHttpClient(url,username,password,null);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	
                String strResult = EntityUtils.toString(response.getEntity());
                
                Map<String, Object> map = JSON.parseObject(strResult, Map.class);
                
                for (String key : map.keySet()) {
                	props.put(key, map.get(key));
				}
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
	
	private static void loadFromDubbo(String filename,Properties props){
		
	}
	
	private static void loadFromRedis(String filename,Properties props){
		
		
	}
	
	
}
