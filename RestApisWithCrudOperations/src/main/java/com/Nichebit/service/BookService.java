package com.Nichebit.service;

import java.util.List;

import com.Nichebit.Entity.Book;

public interface BookService {
	
	public String upSertBook(Book book); //it will update and insert the book
	
	public List<Book> getAllBooks();
	
	public String getBook(Integer book);
	
	/* public String updateBook(Book book); */
	
	public String deleteBook(Integer book);

}
