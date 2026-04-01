package com.wiss.quizbackend.exception;

import com.wiss.quizbackend.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Global exception handler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle question not found response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleQuestionNotFound(
            QuestionNotFoundException ex, WebRequest request) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                "QUESTION_NOT_FOUND",
                "Frage mit ID " + ex.getQuestionId() + " wurde nicht gefunden",
                404,
                extractPath(request)
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle invalid question data response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(InvalidQuestionDataException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidQuestionData(
            InvalidQuestionDataException ex, WebRequest request) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                "INVALID_QUESTION_DATA",
                ex.getMessage(),
                400,
                extractPath(request)
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle access denied response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDenied(
            AuthorizationDeniedException ex, WebRequest request) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                "ACCESS_DENIED",
                "Zugriff verweigert. Sie haben nicht die erforderlichen Berechtigungen.",
                403,
                extractPath(request)
        );

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * Handle category not found response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCategoryNotFound(
            CategoryNotFoundException ex, WebRequest request) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                "INVALID_CATEGORY",
                "Kategorie '" + ex.getCategory() + "' ist nicht gültig. " +
                        "Verfügbare Kategorien: sports, games, movies, geography, science, history",
                400,
                extractPath(request)
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle difficulty not found response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(DifficultyNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleDifficultyNotFound(
            DifficultyNotFoundException ex, WebRequest request) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                "INVALID_DIFFICULTY",
                "Schwierigkeitsgrad '" + ex.getDifficulty() + "' ist nicht gültig. " +
                        "Verfügbare Schwierigkietsgrade: easy, medium, hard",
                400,
                extractPath(request)
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle illegal argument response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(
            IllegalArgumentException ex, WebRequest request) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                "INVALID_INPUT",
                ex.getMessage(),
                400,
                extractPath(request)
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle general exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(
            Exception ex, WebRequest request) {

        // Log the actual exception for debugging (nicht an Frontend senden!)
        System.err.println("Unhandled exception: " + ex.getMessage());
        ex.printStackTrace();

        ErrorResponseDTO error = new ErrorResponseDTO(
                "INTERNAL_SERVER_ERROR",
                "Ein unerwarteter Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.",
                500,
                extractPath(request)
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle validation errors response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(
            MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        StringBuilder message = new StringBuilder("Validierungsfehler: ");
        errors.forEach((field, error) -> message.append(field).append(" - ").append(error).append("; "));

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                "VALIDATION_ERROR",
                message.toString(),
                400,
                extractPath(request)
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    private String extractPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
