package com.wiss.quizbackend.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * The type Question.
 */
@Entity                                     // ← "Das wird eine Datenbank-Tabelle"
@Table(name = "questions")                  // ← "Tabelle soll 'questions' heissen (Optional)"
public class Question {

    @Id                                    // ← "Das ist der Primary Key"
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ← "PostgreSQL macht Auto-Increment"
    private Long id;

    @Column(nullable = false, length = 128)  // ← "Spalte darf nicht NULL sein, max 128 Zeichen"
    private String question;

    @Column(name = "correct_answer", nullable = false)  // ← "Spalte heisst 'correct_answer'"
    private String correctAnswer;

    @ElementCollection                     // ← "Liste wird als separate Tabelle gespeichert"
    @CollectionTable(name = "question_incorrect_answers", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "incorrect_answer")
    private List<String> incorrectAnswers;

    @Column(nullable = false, length = 64)
    private String category;

    @Column(nullable = false, length = 32)
    private String difficulty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private AppUser createdBy;

    /**
     * Instantiates a new Question.
     */
// ✅ DEFAULT CONSTRUCTOR hinzufügen (für JPA/Hibernate):
    public Question() {
    }

    /**
     * Instantiates a new Question.
     *
     * @param question         the question
     * @param correctAnswer    the correct answer
     * @param incorrectAnswers the incorrect answers
     * @param category         the category
     * @param difficulty       the difficulty
     * @param createdBy        the created by
     */
    public Question(String question, String correctAnswer,
                    List<String> incorrectAnswers, String category,
                    String difficulty, AppUser createdBy) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
        this.category = category;
        this.difficulty = difficulty;
        this.createdBy = createdBy;
    }

    /**
     * Instantiates a new Question.
     *
     * @param id               the id
     * @param question         the question
     * @param correctAnswer    the correct answer
     * @param incorrectAnswers the incorrect answers
     * @param category         the category
     * @param difficulty       the difficulty
     */
    public Question(Long id, String question, String correctAnswer,
                    List<String> incorrectAnswers, String category,
                    String difficulty) {
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
        this.category = category;
        this.difficulty = difficulty;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public AppUser getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(AppUser createdBy) {
        this.createdBy = createdBy;
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
}
