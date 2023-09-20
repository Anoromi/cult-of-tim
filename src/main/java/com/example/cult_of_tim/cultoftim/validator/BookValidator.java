package com.example.cult_of_tim.cultoftim.validator;

import com.example.cult_of_tim.cultoftim.models.Book;

import java.util.List;

public interface BookValidator extends  CommonValidator<Book> {

    List<String> validate(Book value);
}
