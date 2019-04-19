package com.cyb.resttemplate.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

	@Bean
	public RestTemplate restTemplate() {
		// 修正PATCH请求不支持的问题
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();		
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
		
		return restTemplate;
	}
}
