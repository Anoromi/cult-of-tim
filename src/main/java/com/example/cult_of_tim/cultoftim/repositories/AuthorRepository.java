package com.example.cult_of_tim.cultoftim.repositories;

import com.example.cult_of_tim.cultoftim.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByFullName(String fullName);


    Optional<Author> findByOpenLibraryId(String openLibraryId);

}
