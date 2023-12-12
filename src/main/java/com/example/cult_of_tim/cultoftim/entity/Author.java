package com.example.cult_of_tim.cultoftim.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    //@Column(length = 100)
    //private String firstName;

    //@Column(length = 100)
    //private String lastName;


    private String fullName;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "authors")
    private List<Book> books;

    @Column(length = 20)
    private String openLibraryId;

}
