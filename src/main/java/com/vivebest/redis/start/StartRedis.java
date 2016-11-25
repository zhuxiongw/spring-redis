package com.vivebest.redis.start;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartRedis {
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
		context.start();
	}
}
