package com.jpadata.sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

// enable Eureka Client works only Eureka server 
// but EnableDiscoveryClient works with any of the server like zookeeper, consul, eureka

//@EnableEurekaClient

@EnableHystrix
@EnableSwagger2
@EnableDiscoveryClient
@SpringBootApplication
public class SpringPostgresHibernateProjectApplication  extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SpringPostgresHibernateProjectApplication.class, args);
	}
	
	 protected SpringApplicationBuilder configurer(SpringApplicationBuilder application)
	 {
		 return application.sources(SpringPostgresHibernateProjectApplication.class);
	 }

}
