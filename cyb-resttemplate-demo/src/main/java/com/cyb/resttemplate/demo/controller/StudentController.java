package com.cyb.resttemplate.demo.controller;

import java.net.URI;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("students")
@Controller
public class StudentController {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("**")
	public ResponseEntity<?> proxy(HttpServletRequest request,
			HttpServletResponse response, HttpMethod method,
			@RequestBody(required = false) String body) {
		URI proxyUri = UriComponentsBuilder
				.fromUriString("http://localhost:8081")
				.path(request.getRequestURI()).query(request.getQueryString())
				.build(true).toUri();
		
		HttpHeaders headers = new HttpHeaders();
	    Enumeration<String> headerNames = request.getHeaderNames();
	    while (headerNames.hasMoreElements()) {
	        String headerName = headerNames.nextElement();
	        headers.set(headerName, request.getHeader(headerName));
	    }
	    
	    HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
	    try {
	        return restTemplate.exchange(proxyUri, method, httpEntity, String.class);
	    } catch(HttpStatusCodeException e) {
	        return ResponseEntity.status(e.getRawStatusCode())
	                             .headers(e.getResponseHeaders())
	                             .body(e.getResponseBodyAsString());
	    }
	}
}
