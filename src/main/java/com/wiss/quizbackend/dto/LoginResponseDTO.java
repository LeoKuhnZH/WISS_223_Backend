package com.wiss.quizbackend.dto;

/**
 * The type Login response dto.
 */
public class LoginResponseDTO {

    private final String token;
    private static final String TOKEN_TYPE = "Bearer";
    private final Long userId;
    private final String username;
    private final String email;
    private final String role;
    private final long expiresIn;  // in Millisekunden

    /**
     * Instantiates a new Login response dto.
     *
     * @param token     the token
     * @param userId    the user id
     * @param username  the username
     * @param email     the email
     * @param role      the role
     * @param expiresIn the expires in
     */
    public LoginResponseDTO(String token, Long userId, String username,
                            String email, String role, long expiresIn) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.expiresIn = expiresIn;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
// Nur Getters (Immutable!)
    public String getToken() {
        return token;
    }

    /**
     * Gets token type.
     *
     * @return the token type
     */
    public String getTokenType() {
        return TOKEN_TYPE;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Long getUserId() {
        return userId;
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
     * Gets expires in.
     *
     * @return the expires in
     */
    public long getExpiresIn() {
        return expiresIn;
    }






    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "token='[HIDDEN]'" +
                ", tokenType='" + TOKEN_TYPE + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}