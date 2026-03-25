package com.wiss.quizbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object für Registration Requests.
 *
 * Zweck:
 * - Definiert was der Client senden muss
 * - Validation Rules für Input
 * - Keine internen Felder (id, version, createdAt)
 *
 * Bean Validation Annotations:
 * - @NotBlank: Nicht null, nicht leer, nicht nur Whitespace
 * - @Size: Min/Max Länge
 * - @Email: Valide Email-Adresse
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

    // Default Constructor für JSON Deserialization
    public RegisterRequestDTO() {}

    // Constructor mit allen Feldern (für Tests)
    public RegisterRequestDTO(String username, String email,
                              String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters und Setters


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

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
