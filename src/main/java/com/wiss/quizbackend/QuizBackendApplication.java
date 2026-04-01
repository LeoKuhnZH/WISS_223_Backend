package com.wiss.quizbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Quiz backend application.
 */
@SpringBootApplication // ← Diese Annotation macht die "Magie"
public class QuizBackendApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(QuizBackendApplication.class, args);
    }

}
