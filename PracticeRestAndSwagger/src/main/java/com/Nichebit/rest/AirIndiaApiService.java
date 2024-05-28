package com.Nichebit.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Nichebit.Request.PassengerInfo;
import com.Nichebit.Response.TicketInfo;

@RestController
public class AirIndiaApiService {
	
	
	@PostMapping(value="/BookFlightTicket",consumes= {"application/json"},
			produces= {"application/json"})
	public ResponseEntity<TicketInfo> bookTiccket(@RequestBody PassengerInfo pinfo)
	{
		
		System.out.println(pinfo);
		
		//booking logic
		
		
		TicketInfo tinfo=new TicketInfo();
		
		tinfo.setTclass("Econemy");
		tinfo.setTid("AirIn84736");
		tinfo.setTPrice(8499.00);
		tinfo.setTststus("CONFIRMED");
		
		return new ResponseEntity<>(tinfo,HttpStatus.OK);
	}

}
