package com.example.cult_of_tim.cultoftim.models;

import com.cult_of_tim.auth.cultoftimauth.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToMany
    private List<User> users;

    private boolean globalPromotion;

    public Long getId() {
        return id;
    }

    public List<User> getUserIDs() {
        return users;
    }

    public void setUserIDs(List<User> users) {
        this.users = users;
    }

    public boolean isGlobalPromotion() {
        return globalPromotion;
    }

    public void setGlobalPromotion(boolean globalPromotion) {
        this.globalPromotion = globalPromotion;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }


}
