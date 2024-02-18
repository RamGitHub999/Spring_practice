package com.Nichebit.Main;



import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.Nichebit.Car;

public class Driver { 
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		 * 1.for auto wiring we have autowire property in the bean file. 
		 * 2.bean file we can use autowire will have t values byName,byType 
		 * 3.when we use byName it will checks the reference variable in the dependent class if that variable
		 * not available the execution issue 
		 * 4.bean class have unique ids for the every
		 * bean
		 *  5.when we use autowire parent class as 2 beans based on custom requiremnt it will gives you runtime error 
       when we run the our dependent class of it so we for resolve that error while using autowire as byType ,then we have anyone make as 
       autowire-candidate="false"
       6.when we configure autowiring with byName or byType it is performing setter injection by default and setter method
		 */
        ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfiguration.xml");
        
        Car car=context.getBean("car", Car.class);
        car.drive();
		

	}

}
