package com.wiss.quizbackend.exception;

/**
 * The type Difficulty not found exception.
 */
public class DifficultyNotFoundException extends RuntimeException {

    private final String diffculty;

    /**
     * Instantiates a new Difficulty not found exception.
     *
     * @param diffculty the diffculty
     */
    public DifficultyNotFoundException(String diffculty) {
        super("Diffculty '" + diffculty + "' not found");
        this.diffculty = diffculty;
    }

    /**
     * Gets difficulty.
     *
     * @return the difficulty
     */
    public String getDifficulty() {
        return diffculty;
    }
}
