package com.cult_of_tim.auth.cultoftimauth.model;

import jakarta.persistence.*;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Entity
@ToString
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID tokenId;
    private Date expiresAt;


    @ManyToOne
    private User user;


    public UUID getTokenId() {
        return tokenId;
    }

    public void setTokenId(UUID tokenId) {
        this.tokenId = tokenId;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
