package com.example.cult_of_tim.cultoftim.repositories;

import com.example.cult_of_tim.cultoftim.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByName(String name);
}
