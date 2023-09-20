package com.example.cult_of_tim.cultoftim.validator;

import java.util.List;

public interface CommonValidator<T> {

    List<String> validate(T value);
}
