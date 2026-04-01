package com.wiss.quizbackend.controller;

import com.wiss.quizbackend.dto.LoginRequestDTO;
import com.wiss.quizbackend.dto.LoginResponseDTO;
import com.wiss.quizbackend.dto.RegisterRequestDTO;
import com.wiss.quizbackend.dto.RegisterResponseDTO;
import com.wiss.quizbackend.entity.AppUser;
import com.wiss.quizbackend.entity.Role;
import com.wiss.quizbackend.service.AppUserService;
import com.wiss.quizbackend.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller für Authentication.
 * <p>
 * Controller Layer Verantwortungen:
 * - HTTP Request/Response Handling
 * - Input Validation (@Valid)
 * - Status Codes
 * - Exception Handling
 * </p>
 *
 * @RestController = @Controller + @ResponseBody
 *                   Alle Methoden returnieren JSON
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // Für React Frontend
public class AuthController {

    public static final String ERROR_TEXT_BEGINNING = "error";
    // Neue Version (AppUserService + JwtService)
    private final AppUserService appUserService;
    private final JwtService jwtService;

    public AuthController(AppUserService appUserService,
                          JwtService jwtService) {
        this.appUserService = appUserService;
        this.jwtService = jwtService;
    }

    /**
     * POST /api/auth/register
     *
     * @Valid triggert Bean Validation (siehe RegisterRequest)
     * @RequestBody parsed JSON zu Java Object
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        try {
            // Service Layer aufrufen
            AppUser newUser = appUserService.registerUser(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword(),
                    Role.PLAYER  // Default Role für neue User
            );

            // Response DTO erstellen
            RegisterResponseDTO response = new RegisterResponseDTO(
                    newUser.getId(),
                    newUser.getUsername(),
                    newUser.getEmail(),
                    newUser.getRole().name()
            );

            // HTTP 200 OK mit Response Body
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // HTTP 400 Bad Request bei Validation Errors
            Map<String, String> error = new HashMap<>();
            error.put(ERROR_TEXT_BEGINNING, e.getMessage());
            return ResponseEntity.badRequest().body(error);

        } catch (Exception e) {
            // HTTP 500 bei unerwarteten Fehlern
            Map<String, String> error = new HashMap<>();
            error.put(ERROR_TEXT_BEGINNING, "Registrierung fehlgeschlagen");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * GET /api/auth/test
     * Simpler Test-Endpoint
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth Controller funktioniert!");
    }

    /**
     * POST /api/auth/login
     *
     * Authentifiziert einen User und gibt JWT Token zurück.
     *
     * Request Body Example:
     * {
     *   "usernameOrEmail": "maxmuster",
     *   "password": "test123"
     * }
     *
     * Success Response (200):
     * {
     *   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
     *   "tokenType": "Bearer",
     *   "userId": 1,
     *   "username": "maxmuster",
     *   "email": "max@example.com",
     *   "role": "PLAYER",
     *   "expiresIn": 86400000
     * }
     *
     * Error Response (401):
     * {
     *   "error": "Ungültige Anmeldedaten"
     * }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequestDTO request) {
        try {
            // 1. User finden (Username oder Email)
            Optional<AppUser> userOpt;

            // Prüfen ob Email oder Username
            if (request.getUsernameOrEmail().contains("@")) {
                // Hat @? → Email
                userOpt = appUserService
                        .findByEmail(request.getUsernameOrEmail());
            } else {
                // Kein @? → Username
                userOpt = appUserService
                        .findByUsername(request.getUsernameOrEmail());
            }

            // User existiert nicht
            if (userOpt.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(ERROR_TEXT_BEGINNING, "Ungültige Anmeldedaten"));
            }

            AppUser user = userOpt.get();

            // 2. Passwort prüfen mit authenticateUser
            Optional<AppUser> authenticatedUser =
                    appUserService.authenticateUser(user.getUsername(),
                            request.getPassword());

            if (authenticatedUser.isEmpty()) {
                // Passwort falsch
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(ERROR_TEXT_BEGINNING, "Ungültige Anmeldedaten"));
            }

            // 3. JWT Token generieren
            String token = jwtService.generateToken(
                    user.getUsername(),
                    user.getRole().name()
            );




            // 4. Response DTO erstellen
            LoginResponseDTO response = new LoginResponseDTO(
                    token,
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole().name(),
                    86400000L  // 24 Stunden in ms
            );

            // 5. Success Response
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Unerwartete Fehler
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(ERROR_TEXT_BEGINNING,
                            "Ein Fehler ist aufgetreten: " + e.getMessage()));
        }
    }
}