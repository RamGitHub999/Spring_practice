package com.Nichebit.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Nichebit.Entity.Book;

public interface BookDao extends JpaRepository<Book, Integer> {

}
