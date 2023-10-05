package com.example.cult_of_tim.cultoftim.repositories;

import com.example.cult_of_tim.cultoftim.models.Author;
import com.example.cult_of_tim.cultoftim.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorsId(Long authorId);

    List<Book> findByCategoriesId(Long categoryId);

    //List<Book>  findBooksByAuthorsContaining(List<Long> author);
    // List<Book> findBookByByAuthorId(long authorId);

    List<Book> findByAvailable(boolean available);

}
