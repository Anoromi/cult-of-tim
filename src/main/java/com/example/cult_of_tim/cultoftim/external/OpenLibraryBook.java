package com.example.cult_of_tim.cultoftim.external;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenLibraryBook(String title, List<AuthorData> authors) {

    //@JsonIgnoreProperties(ignoreUnknown = true)
    public static record AuthorData(String key) {

    }
}
