package com.client.source;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import com.alibaba.fastjson.JSON;
import com.client.utils.DisConfUtils;

public class DisPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer
		implements InitializingBean{

	private Resource[] locations;
	
	@Override
	public void setLocations(Resource... locations) {
		this.locations = locations;
		super.setLocations(locations);
	}
	@Override
	public void setLocation(Resource location) {
		this.locations = new Resource[] {location};
	}
	
	public void afterPropertiesSet() throws Exception {
	}

	/**
	 * 自定义配置文件加载方式，这里将会加载远程的配置文件，
	 * 
	 * <pre>
	 * 		<bean id="propertyPlaceholderConfigurer" class="com.xunheyun.util.DisPropertyPlaceholderConfigurer">  
	 *	    <property name="locations">  
	 *	        <list>  
	 *	            <value>classpath:*.properties</value>
	 *	        </list>  
	 *	    </property>
	 * </pre>
	 * 
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Properties mergeProperties() throws IOException {

		Properties props = new Properties();
		
		for (Resource location : this.locations) {
			
			HttpResponse response = DisConfUtils.getHttpClient(location.getURI().toString());
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	
                String strResult = EntityUtils.toString(response.getEntity());
                
                Map<String, Object> map = JSON.parseObject(strResult, Map.class);
                
                for (String key : map.keySet()) {
                	props.put(key, map.get(key));
				}
            }
		}
		return props;
	}

}
