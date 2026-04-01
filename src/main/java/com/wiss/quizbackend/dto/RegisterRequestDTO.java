package com.wiss.quizbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * The type Register request dto.
 */
public class RegisterRequestDTO {

    @NotBlank(message = "Username ist erforderlich")
    @Size(min = 3, max = 50, message = "Username muss zwischen 3 und 50 Zeichen haben")
    private String username;

    @NotBlank(message = "Email ist erforderlich")
    @Email(message = "Email muss ein gültiges Format haben")
    @Size(max = 100, message = "Email darf maximal 100 Zeichen haben")
    private String email;

    @NotBlank(message = "Passwort ist erforderlich")
    @Size(min = 6, message = "Passwort muss mindestens 6 Zeichen haben")
    private String password;

    /**
     * Instantiates a new Register request dto.
     */
// Default Constructor für JSON Deserialization
    public RegisterRequestDTO() {}

    /**
     * Instantiates a new Register request dto.
     *
     * @param username the username
     * @param email    the email
     * @param password the password
     */
// Constructor mit allen Feldern (für Tests)
    public RegisterRequestDTO(String username, String email,
                              String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters und Setters


    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
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
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    // toString ohne Passwort (Security!)
    @Override
    public String toString() {
        return "RegisterRequestDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='[HIDDEN]'" +
                '}';
    }
}
