package com.example.cult_of_tim.cultoftim.validator;

import com.example.cult_of_tim.cultoftim.entity.Author;

import java.util.List;

public interface AuthorValidator extends CommonValidator<Author> {

    List<String> validate(Author author);
}
