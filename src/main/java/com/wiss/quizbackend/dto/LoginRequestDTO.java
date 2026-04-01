package com.wiss.quizbackend.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * The type Login request dto.
 */
public class LoginRequestDTO {

    @NotBlank(message = "Username oder Email ist erforderlich")
    private String usernameOrEmail;

    @NotBlank(message = "Passwort ist erforderlich")
    private String password;

    /**
     * Instantiates a new Login request dto.
     */
// Default Constructor für JSON Deserialization
    public LoginRequestDTO() {}

    /**
     * Instantiates a new Login request dto.
     *
     * @param usernameOrEmail the username or email
     * @param password        the password
     */
// Constructor mit allen Feldern (für Tests)
    public LoginRequestDTO(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    /**
     * Gets username or email.
     *
     * @return the username or email
     */
// Getters und Setters
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }


    /**
     * Sets username or email.
     *
     * @param usernameOrEmail the username or email
     */
    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
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

    @Override
    public String toString() {
        return "LoginRequestDTO{" +
                "usernameOrEmail='" + usernameOrEmail + '\'' +
                ", password='[HIDDEN]'" +
                '}';
    }
}