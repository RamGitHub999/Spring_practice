package com.Nichebit;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Motor implements InitializingBean,DisposableBean {

	
	/* ------------this is the interfaces approcah to manage the Bean life cycle-------------*/
	public Motor() {
		System.out.println("Motor constructor :: is called----");
	}
	
	@Override
	public void afterPropertiesSet()throws Exception
	{
		System.out.println("afterPropertiesSet() method is called it is predefined mathod of InitializingBean interface");
	}
	
	@Override
	public void destroy()throws Exception{
		System.out.println("Destroy() Method is called it is the pedefined class of Disposable Interface");
	}

}
