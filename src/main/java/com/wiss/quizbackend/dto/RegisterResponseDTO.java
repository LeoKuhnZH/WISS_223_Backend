package com.wiss.quizbackend.dto;

/**
 * Data Transfer Object für Registration Response.
 *
 * Wichtige Regeln:
 * - NIEMALS das Passwort zurückgeben
 * - Nur Informationen die der Client braucht
 * - Immutable (finale Felder) für Sicherheit
 *
 * Diese DTO ist immutable (unveränderlich):
 * - Alle Felder sind final
 * - Nur Getter, keine Setter
 * - Thread-safe
 */
public class RegisterResponseDTO {

    private final Long id;
    private final String username;
    private final String email;
    private final String role;
    private final String message;

    /**
     * Constructor für erfolgreiche Registration.
     *
     * @param id Die generierte User ID
     * @param username Der registrierte Username
     * @param email Die registrierte Email
     * @param role Die zugewiesene Rolle
     */
    public RegisterResponseDTO(Long id, String username,
                               String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.message = "Registrierung erfolgreich! Willkommen "
                + username + "!";
    }

    // Nur Getters, keine Setters (Immutable!)


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "RegisterResponseDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

