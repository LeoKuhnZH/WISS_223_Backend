package com.wiss.quizbackend.entity;

// Welche Imports brauchen wir?
// Tipp: jakarta.persistence.* für JPA Annotationen
// Tipp: Denk an @Entity, @Table, @Id, @GeneratedValue, @Column, @Enumerated

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;


@Entity
@Table(name = "app_users")
public class AppUser {
    public AppUser() {
    }

    public AppUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Nonnull
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nonnull
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Nonnull
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Nonnull
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Nonnull
    public Role getRole() {
        return role;
    }



    public void setRole(Role role) {
        this.role = role;
    }
}