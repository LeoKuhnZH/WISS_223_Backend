package com.wiss.quizbackend.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * The type App user.
 */
@Entity
@Table(name = "app_users")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;  // ACHTUNG: Wird in nächster Lektion gehashed!
    // NIE Passwörter im Klartext speichern!

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * Instantiates a new App user.
     */
// Konstruktoren
    public AppUser() {}

    /**
     * Instantiates a new App user.
     *
     * @param username the username
     * @param email    the email
     * @param password the password
     * @param role     the role
     */
    public AppUser(String username, String email, String password, Role role) {
        // id wird NICHT gesetzt - JPA kümmert sich darum!
        // version wird NICHT gesetzt - JPA kümmert sich darum!
        // Regel, wenn JPA oder die Datenbank die Werte setzen, dann nicht im Konstruktor setzen!
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // ========================================
    // UserDetails Methoden (PFLICHT für Spring Security!)
    // ========================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // "ROLE_" Prefix für Spring Security hinzufügen
        // Aus Role.ADMIN wird "ROLE_ADMIN"
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Keine Ablauflogik implementiert
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Keine Sperrlogik implementiert
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Passwörter laufen nicht ab
    }

    // Getter und Setter

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }
}