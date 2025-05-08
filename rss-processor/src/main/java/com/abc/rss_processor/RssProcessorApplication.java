package com.abc.rss_processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RssProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RssProcessorApplication.class, args);
	}

}
