package com.jpadata.sb.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
	
	@Bean
	public DozerBeanMapper dozermapper()
	{
		return new DozerBeanMapper();
	}

}
