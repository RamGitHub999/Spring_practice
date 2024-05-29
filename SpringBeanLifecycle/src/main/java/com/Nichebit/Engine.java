package com.Nichebit;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class Engine {
	
	/* this is the annotation based approach to handle the Bean Lifecycle
	 * the annotation based approach will be the beat way to manage the spring bean lifecycle*/

	public Engine() {
		System.out.println("Engine construtor is called");
	}

	@PostConstruct
	public void start()
	{
		System.out.println("Engine started.......");
	}
	
	@PreDestroy
	public void stop()
	{
		System.out.println("Engine stopedd-------------");
	}
}
