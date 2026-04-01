package com.wiss.quizbackend.exception;

/**
 * The type Question not found exception.
 */
public class QuestionNotFoundException extends RuntimeException {

    private final Long questionId;

    /**
     * Instantiates a new Question not found exception.
     *
     * @param questionId the question id
     */
    public QuestionNotFoundException(Long questionId) {
        super("Question with ID: " + questionId + " not found");
        this.questionId = questionId;
    }

    /**
     * Gets question id.
     *
     * @return the question id
     */
    public Long getQuestionId() {
        return questionId;
    }
}
