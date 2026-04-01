package com.wiss.quizbackend.exception;

/**
 * The type Category not found exception.
 */
public class CategoryNotFoundException extends RuntimeException {

    private final String category;

    /**
     * Instantiates a new Category not found exception.
     *
     * @param category the category
     */
    public CategoryNotFoundException(String category) {
        super("Categroy '" + category + "' not found");
        this.category = category;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }
}
