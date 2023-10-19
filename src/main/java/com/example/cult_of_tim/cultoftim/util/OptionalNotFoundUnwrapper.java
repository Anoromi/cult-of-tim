package com.example.cult_of_tim.cultoftim.util;

import com.example.cult_of_tim.cultoftim.util.exceptions.NotFoundException;

import java.util.Optional;

public class OptionalNotFoundUnwrapper {

    public static <T> T unwrap(Optional<T> t) throws NotFoundException {
        if(t.isEmpty())
            throw new NotFoundException();
       return t.get();
    }
}
