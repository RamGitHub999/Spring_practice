package com.Nichebit;

public class Car {
	
	private Diselengine dengine;
	
	

	public void setDengine(Diselengine dengine) {
		this.dengine = dengine;
	}



	public Car() {
		System.out.println("Car :: Constructor");
	}
	
	public void drive()
	{
		int start = dengine.start();
		
		if(start >=1)
		{
			System.out.println("journey Started Successfully");
			
		}else {
			
			System.out.println("Engine Start Failed...");
			
		}
	}
}
