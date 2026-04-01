package com.wiss.quizbackend.service;

import com.wiss.quizbackend.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService Implementation - lädt User aus der Datenbank
 *
 * Spring Security ruft diese Klasse auf, um User zu laden.
 * Analogie: Die Zentrale, die nachschaut ob ein User existiert
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepository;

    public AppUserDetailsService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Lädt einen User anhand des Usernames aus der Datenbank
     *
     * @param username Der Username
     * @return UserDetails Object (AppUser implementiert UserDetails!)
     * @throws UsernameNotFoundException wenn User nicht existiert
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User nicht gefunden: " + username));
    }
}