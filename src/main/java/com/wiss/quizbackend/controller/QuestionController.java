package com.wiss.quizbackend.controller;

import com.wiss.quizbackend.dto.QuestionDTO;
import com.wiss.quizbackend.dto.QuestionFormDTO;
import com.wiss.quizbackend.entity.Question;
import com.wiss.quizbackend.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Question controller.
 */
@RestController
@RequestMapping("/api/questions")
@Tag(name="Questions", description = "CRUD Operations für Quiz-Fragen")
public class QuestionController {
    private final QuestionService service;

    /**
     * Instantiates a new Question controller.
     *
     * @param service the service
     */
    public QuestionController(QuestionService service) {
        this.service = service;
    }

    /**
     * Gets all questions.
     *
     * @return the all questions
     */
    @GetMapping
    @Operation(
            summary = "Alle Fragen abrufen",
            description = "Gibt alle verfügbaren Quiz-Fragen zurück"
    )
    @ApiResponse(responseCode = "200", description = "Liste erfolgreich abgerufen")
    @PreAuthorize("hasAnyRole('ADMIN', 'PLAYER')")
    public List<QuestionDTO> getAllQuestions() {
        return service.getAllQuestionsAsDTO();
    }

    /**
     * Gets all form questions.
     *
     * @return the all form questions
     */
    @GetMapping("/all")
    @Operation(
            summary = "Alle Fragen abrufen",
            description = "Gibt alle verfügbaren Quiz-Fragen zurück"
    )
    @ApiResponse(responseCode = "200", description = "Liste erfolgreich abgerufen")
    @PreAuthorize("hasAnyRole('ADMIN', 'PLAYER')")
    public List<QuestionFormDTO> getAllFormQuestions() {
        return service.getAllQuestionsAsFormDTO();
    }

    /**
     * Gets question by id.
     *
     * @param id the id
     * @return the question by id
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Frage nach ID abrufen",
            description = "Gibt eine spezifische Frage basierend auf der ID zurück"
    )
    @ApiResponse(responseCode = "200", description = "Frage gefunden")
    @ApiResponse(responseCode = "404", description = "Frage nicht gefunden")
    @ApiResponse(responseCode = "400", description = "Ungültige ID übergeben")
    @PreAuthorize("hasAnyRole('ADMIN', 'PLAYER')")
    public QuestionDTO getQuestionById(
            @Parameter(
                    description = "Die eindeutige ID der gewünschten Frage",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {
        return service.getQuestionByIdAsDTO(id);
    }

    /**
     * Gets question by id for edit.
     *
     * @param id the id
     * @return the question by id for edit
     */
    @GetMapping("/{id}/edit")
    @Operation(
            summary = "Frage nach ID abrufen Formular",
            description = "Gibt eine spezifische Frage basierend auf der ID zurück, optimiert für das Frontend-Formular"
    )
    @ApiResponse(responseCode = "200", description = "Frage gefunden")
    @ApiResponse(responseCode = "404", description = "Frage nicht gefunden")
    @ApiResponse(responseCode = "400", description = "Ungültige ID übergeben")
    @PreAuthorize("hasRole('ADMIN')") // ← NEU! Nur ADMIN sieht Edit-Form
    public QuestionFormDTO getQuestionByIdForEdit(@PathVariable Long id) {
        return service.getQuestionByIdAsFormDTO(id);
    }

    /**
     * Gets questions by category.
     *
     * @param category the category
     * @return the questions by category
     */
    @GetMapping("/category/{category}")
    @Operation(
            summary = "Fragen nach Kategorie",
            description = "Gibt alle Fragen einer bestimmten Kategorie zurück"
    )
    @ApiResponse(responseCode = "200", description = "Ergebnisse nach Kategorie zurückgegeben")
    @PreAuthorize("hasAnyRole('ADMIN', 'PLAYER')")
    public List<QuestionDTO> getQuestionsByCategory(
            @Parameter(description = "Kategorie", example = "sports", required = true)
            @PathVariable String category) {
        return service.getQuestionsByCategoryAsDTO(category);
    }

    /**
     * Gets questions by difficulty.
     *
     * @param difficulty the difficulty
     * @return the questions by difficulty
     */
    @GetMapping("/difficulty/{difficulty}")
    @Operation(
            summary = "Fragen nach Schwierigkeit",
            description = "Gibt alle Fragen einer bestimmten Schwierigkeit zurück"
    )
    @ApiResponse(responseCode = "200", description = "Ergebnisse nach Schwierigkeit zurückgegeben")
    @PreAuthorize("hasAnyRole('ADMIN', 'PLAYER')")
    public List<QuestionDTO> getQuestionsByDifficulty(
            @Parameter(description = "Schwierigkeit", example = "easy", required = true)
            @PathVariable String difficulty) {
        return service.getQuestionsByDifficultyAsDTO(difficulty);
    }

    /**
     * Create question question dto.
     *
     * @param questionDTO the question dto
     * @return the question dto
     */
    @PostMapping
    @Operation(
            summary = "Neue Frage erstellen",
            description = "Erstellt eine neue Quiz-Frage"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "Frage erfolgreich erstellt")
    @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten")
    @PreAuthorize("hasRole('ADMIN')") // ← NEU! Nur Admins dürfen Fragen erstellen
    public QuestionDTO createQuestion(
            @Parameter(description = "Frage-Daten", required = true)
            @Valid @RequestBody QuestionDTO questionDTO){ // ← @Valid hinzufügen!
        return service.createQuestion(questionDTO);
    }

    /**
     * Create question from form question form dto.
     *
     * @param question the question
     * @return the question form dto
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Neue Frage erstellen über Frontend-Form")
    @ApiResponse(responseCode = "201", description = "Frage erfolgreich erstellt")
    @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten")
    @PreAuthorize("hasRole('ADMIN')") // ← NEU! Nur Admins dürfen Fragen erstellen
    public QuestionFormDTO createQuestionFromForm(
            @Parameter(description = "Frage-Daten", required = true)
            @Valid @RequestBody Question question) {
        return service.createQuestionFromForm(question);
    }


    /**
     * Update question question dto.
     *
     * @param id          the id
     * @param questionDTO the question dto
     * @return the question dto
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Frage aktualisieren",
            description = "Aktualisiert eine bestehende Frage"
    )
    @ApiResponse(responseCode = "200", description = "Frage erfolgreich editiert")
    @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten")
    @PreAuthorize("hasRole('ADMIN')") // ← NEU! Nur Admins dürfen Fragen editieren
    public QuestionDTO updateQuestion(
            @Parameter(description = "ID der zu aktualisierenden Frage", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody QuestionDTO questionDTO){
        return service.updateQuestion(id, questionDTO);
    }

    /**
     * Update question from form question form dto.
     *
     * @param id       the id
     * @param question the question
     * @return the question form dto
     */
    @PutMapping("/{id}/update")
    @Operation(
            summary = "Frage aktualisieren über Formular",
            description = "Aktualisiert eine bestehende Frage über das Formular im Frontend"
    )
    @ApiResponse(responseCode = "200", description = "Frage erfolgreich editiert")
    @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten")
    @PreAuthorize("hasRole('ADMIN')") // ← NEU! Nur Admins dürfen Fragen editieren
    public QuestionFormDTO updateQuestionFromForm(
            @Parameter(description = "ID der zu aktualisierenden Frage", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody Question question) {
        return service.updateQuestionFromForm(id, question);
    }

    /**
     * Delete question.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Frage löschen",
            description = "Löscht eine Frage permanent. Diese Aktion kann nicht rückgängig gemacht werden!"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "200", description = "Frage erfolgreich gelöscht")
    @ApiResponse(responseCode = "404", description = "Frage nicht gefunden")
    @ApiResponse(responseCode = "409", description = "Frage wird noch in aktiven Quiz verwendet")
    @PreAuthorize("hasRole('ADMIN')") // ← NEU! Nur Admins dürfen Fragen löschen
    public void deleteQuestion(
            @Parameter(description = "ID der zu löschenden Frage", example = "1")
            @PathVariable Long id) {
        service.deleteQuestion(id);
    }

    /**
     * Gets questions by filter.
     *
     * @param category   the category
     * @param difficulty the difficulty
     * @return the questions by filter
     */
    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN', 'PLAYER')")
    public List<QuestionDTO> getQuestionsByFilter(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficulty) {

        if (category != null && difficulty != null) {
            return service.getQuestionsByCategoryAndDifficulty(category, difficulty);
        } else if (category != null) {
            return service.getQuestionsByCategoryAsDTO(category);
        } else if (difficulty != null) {
            return service.getQuestionsByDifficultyAsDTO(difficulty);
        } else {
            return service.getAllQuestionsAsDTO();
        }
    }

    /**
     * Search questions list.
     *
     * @param q the q
     * @return the list
     */
    @GetMapping("/search")
    @Operation(
            summary = "Fragen durchsuchen",
            description = "Sucht Fragen basierend auf einem Suchbegriff"
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PLAYER')")
    public List<QuestionDTO> searchQuestions(
            @Parameter(description = "Suchbegriff", example = "Schweiz")
            @RequestParam String q) {
        return service.searchQuestions(q);
    }

    /**
     * Gets question count by category.
     *
     * @param category the category
     * @return the question count by category
     */
    @GetMapping("/stats/category/{category}")
    @Operation(
            summary = "Anzahl Fragen nach Kategorie",
            description = "Zählt die Anzahl Fragen einer bestimmten Kategorie zusammen"
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PLAYER')")
    public long getQuestionCountByCategory(
            @Parameter(description = "Kategorie", example = "history")
            @PathVariable String category) {
        return service.getQuestionCountByCategory(category);
    }

    /**
     * Gets random questions.
     *
     * @param category the category
     * @param limit    the limit
     * @return the random questions
     */
    @GetMapping("/random")
    @Operation(
            summary = "Zufällige Anzahl Fragen nach Kategorie",
            description = "Gibt eine zufällige Anzahl an Fragen zurück"
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PLAYER')")
    public List<QuestionDTO> getRandomQuestions(
            @Parameter(description = "Kategorie", example = "movies")
            @RequestParam String category,
            @Parameter(description = "Anzahl", example = "3")
            @RequestParam(defaultValue = "5") int limit) {
        if(category != null){
            return service.getRandomQuestionsByCategory(category, limit);
        } else {
            return service.getRandomQuestions(limit);
        }
    }

    /**
     * Gets questions count.
     *
     * @return the questions count
     */
    @GetMapping("/count")
    @PreAuthorize("hasAnyRole('ADMIN', 'PLAYER')")
    @Operation(
            summary = "Anzahl aller Fragen",
            description = "Gibt die Anzahl aller verfügbaen Fragen zurück"
    )
    public long getQuestionsCount() {
        return service.getTotalQuestionsCount();
    }
}