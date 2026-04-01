package com.wiss.quizbackend.dto;

/**
 * The type Register response dto.
 */
public class RegisterResponseDTO {

    private final Long id;
    private final String username;
    private final String email;
    private final String role;
    private final String message;

    /**
     * Instantiates a new Register response dto.
     *
     * @param id       the id
     * @param username the username
     * @param email    the email
     * @param role     the role
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


    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
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
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
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

