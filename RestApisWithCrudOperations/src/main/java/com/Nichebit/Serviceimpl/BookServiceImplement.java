package com.Nichebit.Serviceimpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.Nichebit.Dao.BookDao;
import com.Nichebit.Entity.Book;
import com.Nichebit.service.BookService;

@SuppressWarnings("serial")
@Service
@Scope("singleton")
public class BookServiceImplement implements BookService,Serializable {

	
	private BookDao repo;
	
	public BookServiceImplement(BookDao repo) {  //injecting bookDao class obj to this class instructor
		
		this.repo=repo;
	}
	
	@Override
	public String upSertBook(Book book) {
		
		Integer cbookid= book.getBookId();
		if(cbookid ==null)
		{
			repo.save(book);
		}
		
		
		
		if(cbookid==null)
		{
			return "book Inserted";
		}else {
			return "Book Updated";
		}
	}
	

	@Override
	public List<Book> getAllBooks() {
		
		return repo.findAll();
	}

	@Override
	public String getBook(Integer book) {
		repo.findById(book);
		if(repo.findById(book) != null)
		return "Book Details Fetched Sucessfully";
		else
			return"Book Data Not Avilable";
	}

	

	@Override
	public String deleteBook(Integer book) {
		repo.deleteById(book);
		return "Deleted Successfully";
	}


}
