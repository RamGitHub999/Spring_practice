package com.Nichebit.Serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Nichebit.Dao.BookDao;
import com.Nichebit.Entity.Book;



@Service
public class ExcelServiceimpl {
    
	
	  @Autowired 
	  private BookDao repoo;
	  
	  @Autowired
	   private BookServiceImplement bsi;
	 
	
	public static ByteArrayInputStream generateExcelReport(List<Book> reportDataList) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Book_Report");

        //ByteArrayOutputStream out=new ByteArrayOutputStream();
        // Create header row
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("BookId");
        headerRow.createCell(1).setCellValue("BookName");
        headerRow.createCell(2).setCellValue("BookPrice");
        headerRow.createCell(3).setCellValue("BookAuthor");
        headerRow.createCell(4).setCellValue("AuthorContact");
        // Add headers for other fields as needed

        // Create data rows
        int rowNum = 1;
        for (Book data : reportDataList) {
            XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(data.getBookId());
            row.createCell(1).setCellValue(data.getBookName());
            row.createCell(2).setCellValue(data.getBookPrice());
            row.createCell(3).setCellValue(data.getBookAuthor());
            row.createCell(4).setCellValue(data.getAuthorContact());
            // Add cells for other fields as needed
        }

        // Write workbook to ByteArrayOutputStream
       
        workbook.write(outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
       

        
        }
        catch(IOException e){
        	e.printStackTrace();
        	System.out.println("failed");
        	//return new ByteArrayInputStream();
        }
        
        finally {
        	 workbook.close();
        	 //out.close();
        }
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
	
	
	  public ByteArrayInputStream actualData() throws IOException{ 
		  List<Book> all=null;
		  List<Book> getAll=null;
		  try {
			  
			  getAll=bsi.getAllBooks();
			  System.out.println(getAll);
			   //all=repoo.findAll(); 
			  System.out.println(all);
			  //ByteArrayInputStream byteinput;
			 
				  return  generateExcelReport(getAll);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("=======================excel file report failed==================================");
		}
		  
		  
		
		  return  generateExcelReport(getAll);
		  }


	
	 

}
