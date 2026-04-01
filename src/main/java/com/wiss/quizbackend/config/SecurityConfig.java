package com.wiss.quizbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Spring Security Konfiguration - Der "Bauplan" für unser Sicherheitssystem
 *
 * Analogie: Das ist wie der Sicherheitsplan eines Bürogebäudes, der festlegt:
 * - Welche Bereiche sind öffentlich? (Empfangshalle)
 * - Welche Bereiche brauchen einen Ausweis? (Büros)
 * - Wo werden die Ausweis-Lesegeräte installiert? (JWT Filter)
 */
@Configuration              // <- Spring: "Das ist eine Konfigurationsklasse"
@EnableWebSecurity          // <- Spring: "Aktiviere Web Security"
@EnableMethodSecurity       // <- Spring: "Erlaube @PreAuthorize auf Methoden"
public class SecurityConfig {

    /**
     * Bean #1: Password Encoder
     *
     * Analogie: Der Tresor, der Passwörter verschlüsselt
     * - Niemand kann das Original-Passwort lesen
     * - BCrypt ist der Verschlüsselungsalgorithmus (sehr sicher!)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean #2: Authentication Manager (OPTIONAL - NUR ZUR INFORMATION!)
     *
     * ⚠️ WICHTIG: Wir fügen diesen Bean NICHT zu unserem Code hinzu!
     *
     * Warum zeigen wir ihn trotzdem?
     * - Um zu verstehen, was Spring Security automatisch machen könnte
     * - Wir haben die Login-Logik bereits MANUELL implementiert
     * (siehe AppUserService.authenticateUser())
     *
     * Was macht AuthenticationManager?
     * Analogie: Der Empfangsmitarbeiter, der Ausweise prüft
     * - Lädt den User aus der Datenbank (via UserDetailsService)
     * - Vergleicht das eingegebene Passwort mit dem gespeicherten Hash (via PasswordEncoder)
     * - Wirft eine Exception, wenn Login fehlschlägt
     * - Gibt bei Erfolg ein Authentication-Objekt zurück
     *
     * Unsere manuelle Implementation:
     * - AppUserService.authenticateUser() macht GENAU das Gleiche
     * - Zeile 107-121: User suchen + Passwort prüfen
     * - Warum manuell? Um zu verstehen, was im Hintergrund passiert!
     *
     * Wenn wir diesen Bean verwenden würden:
     * - In AuthController würden wir authenticationManager.authenticate() aufrufen
     * - Spring würde automatisch User laden + Passwort prüfen
     * - Weniger Code, aber weniger Verständnis für Anfänger!
     *
     * Fazit: NICHT hinzufügen! Wir bleiben bei unserer manuellen Lösung.
     */
    // @Bean  // <- Auskommentiert, weil wir es NICHT verwenden!
    // public AuthenticationManager authenticationManager(
    //         AuthenticationConfiguration config) throws Exception {
    //     return config.getAuthenticationManager();
    // }



    /**
     * Bean #3: Security Filter Chain WICHTIGSTER TEIL!
     *
     * Analogie: Der Gebäudeplan mit allen Sicherheitsregeln
     * - Definiert öffentliche Bereiche (Empfangshalle)
     * - Definiert geschützte Bereiche (Büros)
     * - Konfiguriert wie Ausweise geprüft werden (JWT Filter)
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
                // SCHRITT 1: CSRF deaktivieren
                // Warum? Bei JWT nicht nötig, da stateless (kein Cookie)
                .csrf(csrf -> csrf.disable())

                // SCHRITT 2: CORS konfigurieren
                // Warum? Damit unser React Frontend das Backend aufrufen kann
                .cors(cors -> cors.configure(http))

                // SCHRITT 3: Authorization Rules - WER DARF WO REIN? 🚪
                .authorizeHttpRequests(auth -> auth
                        // 🌍 ÖFFENTLICHE Bereiche (wie die Empfangshalle)
                        .requestMatchers(
                                "/api/auth/**",      // Login & Register - jeder darf rein
                                "/swagger-ui/**",    // API Dokumentation - öffentlich
                                "/v3/api-docs/**"    // OpenAPI Docs - öffentlich
                        ).permitAll()

                        // 🔒 ALLE anderen Endpoints benötigen
                        // einen gültigen Ausweis (JWT)
                        .anyRequest().authenticated()
                )

                // SCHRITT 4: Session Management auf STATELESS setzen
                // Warum? Wir benutzen JWT, nicht Sessions/Cookies
                // Analogie: Keine Besucherliste führen, nur Ausweise prüfen
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}