package com.Nichebit.Controller;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Nichebit.Dao.BookDao;
import com.Nichebit.Entity.Book;
import com.Nichebit.Serviceimpl.BookServiceImplement;
import com.Nichebit.Serviceimpl.ExcelServiceimpl;



@RestController
public class ExcelReportController {

	@Autowired
	private   BookDao repaaa;
	
	@Autowired
	private BookServiceImplement bsi;
	
	@Autowired
	private  ExcelServiceimpl excel;
	
	 

	private static  String headerValues;
	 
	 //@CrossOrigin(origins = "http://localhost:4200")
		/*
		 * @GetMapping(value="/generate-excel-report",produces="application/json")
		 * 
		 * @ResponseBody public static String RoportExcel() { try { List<Book>
		 * reportDataList=bsi.getAllBooks(); generateExcelReport(reportDataList); return
		 * "Success";
		 * 
		 * } catch (Exception e) { // TODO: handle exception return
		 * "ReportGenerate Failed"; } }
		 */
	 
	 @CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping("/generate-excel-report")
    public   ResponseEntity<InputStreamResource> generateExcelReport()throws IOException {
		 
		 String filename="BookDetails.xlsx";
		 ResponseEntity<InputStreamResource> body=null;
		 try {
			
		
		 List<Book> all=bsi.getAllBooks();
		 
		 
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    ObjectOutputStream oos = new ObjectOutputStream(bos);
		    oos.writeObject(all);
		    byte[] bytes=bos.toByteArray();

		 ByteArrayInputStream inputstream = new ByteArrayInputStream(bytes);
		 InputStreamResource file=new InputStreamResource(inputstream);
		 body =
		  ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename"+filename)
		 .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
		 
		 } catch (Exception e) {
				// TODO: handle exception
			 e.printStackTrace();
			}
		 return body;
    }
	 
	 public  List<Book> getAllData()
	 {
		  
				 List<Book> all=bsi.getAllBooks();
				 return all;
	 }
}
 