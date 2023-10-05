package com.example.cult_of_tim.cultoftim.repositories;

import com.example.cult_of_tim.cultoftim.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
