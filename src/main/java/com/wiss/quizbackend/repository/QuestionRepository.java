package com.wiss.quizbackend.repository;

import com.wiss.quizbackend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Question repository.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    // Spring generiert automatisch diese Standard-Methoden:
    // - Question save(Question question)           ← CREATE/UPDATE automatisch
    // - Optional<Question> findById(Long id)       ← READ by ID
    // - List<Question> findAll()                   ← READ all
    // - void deleteById(Long id)                   ← DELETE by ID
    // - boolean existsById(Long id)                ← EXISTS check
    // - long count()                               ← COUNT all
    // Plus noch viele mehr: saveAll, findAllById, deleteAll... +20 Methoden

    /**
     * Find by category list.
     *
     * @param category the category
     * @return the list
     */
// Custom Query Methods (basierend auf Methoden-Namen):
    List<Question> findByCategory(String category);

    /**
     * Find by difficulty list.
     *
     * @param difficulty the difficulty
     * @return the list
     */
    List<Question> findByDifficulty(String difficulty);

    // Spring übersetzt automatisch:
    // findByCategory → SELECT * FROM questions WHERE category = ?
    // findByDifficulty → SELECT * FROM questions WHERE difficulty = ?

    /**
     * Find by category and difficulty list.
     *
     * @param category   the category
     * @param difficulty the difficulty
     * @return the list
     */
// Kombinierte Queries:
    List<Question> findByCategoryAndDifficulty(String category, String difficulty);

    /**
     * Find by category or difficulty list.
     *
     * @param category   the category
     * @param difficulty the difficulty
     * @return the list
     */
    List<Question> findByCategoryOrDifficulty(String category, String difficulty);

    /**
     * Find by question containing list.
     *
     * @param keyword the keyword
     * @return the list
     */
// Text-Suche:
    List<Question> findByQuestionContaining(String keyword);

    /**
     * Find by question containing ignore case list.
     *
     * @param keyword the keyword
     * @return the list
     */
    List<Question> findByQuestionContainingIgnoreCase(String keyword);

    /**
     * Count by category long.
     *
     * @param category the category
     * @return the long
     */
// Counting:
    long countByCategory(String category);

    /**
     * Count by difficulty long.
     *
     * @param difficulty the difficulty
     * @return the long
     */
    long countByDifficulty(String difficulty);

    /**
     * Count by category and difficulty long.
     *
     * @param category   the category
     * @param difficulty the difficulty
     * @return the long
     */
    long countByCategoryAndDifficulty(String category, String difficulty);

    /**
     * Find by category order by question asc list.
     *
     * @param category the category
     * @return the list
     */
// Sortierung:
    List<Question> findByCategoryOrderByQuestionAsc(String category);

    /**
     * Find by difficulty order by id desc list.
     *
     * @param difficulty the difficulty
     * @return the list
     */
    List<Question> findByDifficultyOrderByIdDesc(String difficulty);

    /**
     * Exists by question and category boolean.
     *
     * @param question the question
     * @param category the category
     * @return the boolean
     */
// Existenz prüfen:
    boolean existsByQuestionAndCategory(String question, String category);

    /**
     * Find top 5 by category list.
     *
     * @param category the category
     * @return the list
     */
// Top N Results:
    List<Question> findTop5ByCategory(String category);

    /**
     * Find first 3 by difficulty order by id asc list.
     *
     * @param difficulty the difficulty
     * @return the list
     */
    List<Question> findFirst3ByDifficultyOrderByIdAsc(String difficulty);

    /**
     * Find random by category list.
     *
     * @param category the category
     * @param limit    the limit
     * @return the list
     */
// Random
    @Query(value = "SELECT * FROM questions WHERE category = :category ORDER BY RANDOM() LIMIT :limit",
            nativeQuery = true)
    List<Question> findRandomByCategory(@Param("category") String category, @Param("limit") int limit);

    /**
     * Find random questions list.
     *
     * @param limit the limit
     * @return the list
     */
    @Query(value = "SELECT * FROM questions ORDER BY RANDOM() LIMIT :limit",
            nativeQuery = true)
    List<Question> findRandomQuestions(@Param("limit") int limit);

}
