package com.example.cult_of_tim.cultoftim.repositories;

import com.example.cult_of_tim.cultoftim.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    //Optional<Author> findById(Long id);

    //List<Author> findAll();

    List<Author> findByFirstName(String firstName);

    List<Author> findByLastName(String lastName);

    //Author save(Author author);

   // void deleteById(Long id);
}
