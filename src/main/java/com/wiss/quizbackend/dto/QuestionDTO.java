package com.wiss.quizbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * The type Question dto.
 */
public class QuestionDTO {

    @Schema(description = "Eindeutige ID der Frage", example = "1")
    private Long id;

    @Schema(description = "Der Frage-Text", example = "Was ist die Hauptstadt der Schweiz?")
    @NotBlank(message = "Frage Text ist erforderlich")
    @Size(min = 5, max = 128, message = "Frage muss zwischen 5 und 128 Zeichen sein")
    private String question;

    @Schema(description = "Die korrekte Antwort", example = "Bern")
    @NotBlank(message = "Richtige Antwort ist erforderlich")
    @Size(max = 32, message = "Antwort darf maximal 32 Zeichen haben")
    private String correctAnswer;

    @Schema(description = "Alle Antwortmöglichkeiten (inkl. korrekte)",
            example = "[\"Bern\", \"Zürich\", \"Basel\", \"Genf\"]")
    @NotEmpty(message = "Antworten-Liste darf nicht leer sein")
    @Size(min = 4, max = 4, message = "Es müssen genau 4 Antworten vorhanden sein")
    private List<@NotBlank(message = "Antworten dürfen nicht leer sein") String> answers;

    @Schema(description = "Die Kategorie der Frage", example = "sports")
    @NotBlank(message = "Kategorie ist erforderlich")
    @Pattern(regexp = "sports|games|movies|geography|science|history|biology|mathemathics",
            message = "Kategorie muss eine der folgenden sein: sports, games, movies, geography, science, history")
    private String category;

    @Schema(description = "Der Schwierigkeitsgrad der Frage", example = "hard")
    @NotBlank(message = "Schwierigkeitsgrad ist erforderlich")
    @Pattern(regexp = "easy|medium|hard",
            message = "Schwierigkeitsgrad muss einer der folgenden sein: easy, medium, hard")
    private String difficulty;

    /**
     * Instantiates a new Question dto.
     */
    public QuestionDTO() {
    }

    /**
     * Instantiates a new Question dto.
     *
     * @param id            the id
     * @param question      the question
     * @param correctAnswer the correct answer
     * @param answers       the answers
     * @param category      the category
     * @param difficulty    the difficulty
     */
    public QuestionDTO(Long id, String question, String correctAnswer, List<String> answers,
                       String category, String difficulty) {
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
        this.category = category;
        this.difficulty = difficulty;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets question.
     *
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets question.
     *
     * @param question the question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets correct answer.
     *
     * @return the correct answer
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Sets correct answer.
     *
     * @param correctAnswer the correct answer
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * Gets answers.
     *
     * @return the answers
     */
    public List<String> getAnswers() {
        return answers;
    }

    /**
     * Sets answers.
     *
     * @param answers the answers
     */
    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets difficulty.
     *
     * @return the difficulty
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Sets difficulty.
     *
     * @param difficulty the difficulty
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}