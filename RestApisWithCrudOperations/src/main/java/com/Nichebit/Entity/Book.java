package com.Nichebit.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Book_Details")
public class Book {

	 @Id
	 @GeneratedValue(generator="Book_details_seq")
	 @SequenceGenerator(name = "Book_details_seq",sequenceName = "Book_details_seq",allocationSize = 1)
	 @Column(name="Book_id")
	 private Integer bookId;
	 
	 @Column(name="Book_Name",unique=true)
	 private String bookName;
	 
	 @Column(name="Book_Price")
	 private Double bookPrice;
	 
	 
	 @Column(name="Book_Author")
	 private String bookAuthor;
	 
	 @Column(name="Book_Author_Contact")
	 private String authorContact;
}
