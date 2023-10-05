package com.example.cult_of_tim.cultoftim.repositories;

import com.example.cult_of_tim.cultoftim.models.Author;
import com.example.cult_of_tim.cultoftim.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorId(Long authorId);

    List<Book> findByCategoryId(Long categoryId);

    List<Book> findByAvailable(boolean available);

}
