package com.cult_of_tim.auth.cultoftimauth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "userTable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID userId;

    @Column(length = 100)
    private String username;

    //@Column(name = "user_password", nullable = false)
    private String password;

    //@Column(name = "user_email", nullable = false)
    private String email;

    @Column(nullable = false)
    private String role;

    private Integer balance = 0;


}
