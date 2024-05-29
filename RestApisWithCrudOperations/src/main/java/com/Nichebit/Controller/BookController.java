package com.Nichebit.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Nichebit.Dao.BookDao;
import com.Nichebit.Entity.Book;
import com.Nichebit.Serviceimpl.BookServiceImplement;

@RestController
public class BookController {
	
	   @Autowired
	   private BookServiceImplement bsi;
	   
	   @Autowired
	   private BookDao repo;

		 @GetMapping("/getbook/{id}") 
	   public ResponseEntity<String> getBookdet(@PathVariable("id") Integer bid)
	   {
		   String message=bsi.getBook(bid);
			/* Optional<Book> list=repo.findById(bid); */
		   return new ResponseEntity<>(message,HttpStatus.OK);
	   }
		 
		@GetMapping("/getbookById/{bid}") 
		public ResponseEntity<Optional<Book>> getBookdetById(@PathVariable("bid") Integer bid)
		{
			
			Optional<Book> list=repo.findById(bid);
			 return new ResponseEntity<>(list,HttpStatus.OK);
			
		}
	   
		@CrossOrigin(origins = "http://localhost:4200")
	   @GetMapping("/getAllBooks")
	   public ResponseEntity<List<Book>> getAllBookdet()
	   {
		   List<Book> allBooks=bsi.getAllBooks();
		   
		   return new ResponseEntity<>(allBooks,HttpStatus.OK);
	   }
	   
		@CrossOrigin(origins = "http://localhost:4200")
	   @PostMapping("/addBook")
	   public ResponseEntity<String> addtheBook(@RequestBody Book book)
	   {
		    String upsert=bsi.upSertBook(book);
		   return new ResponseEntity<>(upsert,HttpStatus.CREATED);
	   }
	   
	   @PutMapping("/updatBook")
	   public ResponseEntity<String> updatetheBook(@RequestBody Book book)
	   {
		    String upsert=bsi.upSertBook(book);
		   return new ResponseEntity<>(upsert,HttpStatus.OK);
	   }
	   
	   @DeleteMapping("/Delete/{id}")
	   public ResponseEntity<String> DeleteBook(@PathVariable("id") Integer bid)
	   {
		   String Deletemessage=bsi.deleteBook(bid);
		   return new ResponseEntity<>(Deletemessage,HttpStatus.OK);
	   }
	      
}
