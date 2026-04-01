package com.wiss.quizbackend.config;

import com.wiss.quizbackend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Spring Security Konfiguration - Der "Bauplan" für unser Sicherheitssystem
 *
 * Analogie: Das ist wie der Sicherheitsplan eines Bürogebäudes, der festlegt:
 * - Welche Bereiche sind öffentlich? (Empfangshalle)
 * - Welche Bereiche brauchen einen Ausweis? (Büros)
 * - Wo werden die Ausweis-Lesegeräte installiert? (JWT Filter)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;  // ← NEU!

    // Constructor Injection
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }





    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // CORS: Cross-Origin Request erlauben
                // Fronten auf Port 5173 darf Backend auf 8080 ansprechen
                .cors(cors -> cors.configure(http))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/swagger-ui/**",
                                "/v3/api-docs/**").permitAll()
                        // GEÄNDERT: Jetzt brauchen jeder Request einen gültigen Token
                        // Vorher: permitAll() -> Jeder durfte alles
                        // Jetzt: authenticated() -> Nur eingeloggte User
                        .anyRequest().authenticated()
                )
                // Stateless Sessions: Spring speichert KEINE Session-Daten
                // Jeder Request braucht ein Token (Token = Ausweis bei jeder Tür)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // NEU: JWT Filter HINZUFÜGEN
                // Der Filter wird VOR dem
                // UsernamePasswordAuthenticationFilter ausgeführt
                .addFilterBefore(jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}