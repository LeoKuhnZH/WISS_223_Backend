package com.wiss.quizbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * The type Question form dto.
 */
@Schema(description = "DTO für Frontend-Formulare (Create/Update)")
public class QuestionFormDTO {

    @Schema(description = "Eindeutige ID der Frage", example = "1")
    private Long id;

    @Schema(description = "Der Frage-Text", example = "Was ist die Hauptstadt der Schweiz?")
    @NotBlank(message = "Frage Text ist erforderlich")
    @Size(min = 5, max = 500, message = "Frage muss zwischen 5 und 500 Zeichen sein")
    private String question;

    @Schema(description = "Die korrekte Antwort", example = "Bern")
    @NotBlank(message = "Richtige Antwort ist erforderlich")
    @Size(max = 100, message = "Antwort darf maximal 100 Zeichen haben")
    private String correctAnswer;

    @Schema(description = "Die 3 falschen Antworten",
            example = "[\"Zürich\", \"Basel\", \"Genf\"]")
    @NotEmpty(message = "Falsche Antworten sind erforderlich")
    @Size(min = 3, max = 3, message = "Es müssen genau 3 falsche Antworten vorhanden sein")
    private List<@NotBlank(message = "Falsche Antworten dürfen nicht leer sein")
    @Size(max = 100, message = "Antwort darf maximal 100 Zeichen haben") String> incorrectAnswers;

    @Schema(description = "Die Kategorie der Frage", example = "geography")
    @NotBlank(message = "Kategorie ist erforderlich")
    @Pattern(regexp = "sports|games|movies|geography|science|history|biology|mathematics",
            message = "Kategorie muss eine der folgenden sein: sports, games, movies, geography, science, history, biology, mathematics")
    private String category;

    @Schema(description = "Der Schwierigkeitsgrad", example = "medium")
    @NotBlank(message = "Schwierigkeitsgrad ist erforderlich")
    @Pattern(regexp = "easy|medium|hard",
            message = "Schwierigkeitsgrad muss einer der folgenden sein: easy, medium, hard")
    private String difficulty;

    @Schema(description = "Benutzername des Erstellers", example = "john_doe")
    private String creatorUsername;

    @Schema(description = "ID des Erstellers", example = "42")
    private Long creatorId;

    /**
     * Instantiates a new Question form dto.
     */
    public QuestionFormDTO() {
    }

    /**
     * Instantiates a new Question form dto.
     *
     * @param question         the question
     * @param correctAnswer    the correct answer
     * @param incorrectAnswers the incorrect answers
     * @param category         the category
     * @param difficulty       the difficulty
     * @param creatorUsername  the creator username
     * @param creatorId        the creator id
     */
    public QuestionFormDTO(String question, String correctAnswer,
                           List<String> incorrectAnswers, String category,
                           String difficulty, String creatorUsername, Long creatorId) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
        this.category = category;
        this.difficulty = difficulty;
        this.creatorUsername = creatorUsername;
        this.creatorId = creatorId;
    }

    /**
     * Instantiates a new Question form dto.
     *
     * @param id               the id
     * @param question         the question
     * @param correctAnswer    the correct answer
     * @param incorrectAnswers the incorrect answers
     * @param category         the category
     * @param difficulty       the difficulty
     * @param creatorUsername  the creator username
     * @param creatorId        the creator id
     */
    public QuestionFormDTO(Long id, String question, String correctAnswer,
                           List<String> incorrectAnswers, String category,
                           String difficulty, String creatorUsername, Long creatorId) {
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
        this.category = category;
        this.difficulty = difficulty;
        this.creatorUsername = creatorUsername;
        this.creatorId = creatorId;
    }

    // Getter und Setter

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
     * Gets incorrect answers.
     *
     * @return the incorrect answers
     */
    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    /**
     * Sets incorrect answers.
     *
     * @param incorrectAnswers the incorrect answers
     */
    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
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

    /**
     * Gets creator username.
     *
     * @return the creator username
     */
    public String getCreatorUsername() {
        return creatorUsername;
    }

    /**
     * Sets creator username.
     *
     * @param creatorUsername the creator username
     */
    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    /**
     * Gets creator id.
     *
     * @return the creator id
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * Sets creator id.
     *
     * @param creatorId the creator id
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * To question dto question dto.
     *
     * @param id the id
     * @return the question dto
     */
    public QuestionDTO toQuestionDTO(Long id) {
        List<String> allAnswers = new java.util.ArrayList<>(this.incorrectAnswers);
        allAnswers.add(this.correctAnswer);
        java.util.Collections.shuffle(allAnswers);

        return new QuestionDTO(id, this.question, this.correctAnswer,
                allAnswers, this.category, this.difficulty);
    }


    @Override
    public String toString() {
        return "QuestionFormDTO{" +
                "question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", incorrectAnswers=" + incorrectAnswers +
                ", category='" + category + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", creatorUsername=" + creatorUsername + '\'' +
                ", creatorId=" + creatorId.toString() +
                '}';
    }
}