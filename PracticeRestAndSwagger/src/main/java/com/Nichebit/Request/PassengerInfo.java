package com.Nichebit.Request;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class PassengerInfo {
	
	private String fname;
	private String lastname;
	private String Dateofjour;
	private Long pmobileno;
	private String flightName;
	private String from;
	private String destination;

}
