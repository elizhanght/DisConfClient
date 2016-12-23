package com.client.factory;

import java.io.IOException;

import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import com.client.source.DisResourcePropertySrouce;
import com.client.utils.DisEnum;

public class RedisDisPropertySourceFactory implements PropertySourceFactory {

	public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
		
		return new DisResourcePropertySrouce(name,resource,DisEnum.REDIS);
	}
}
