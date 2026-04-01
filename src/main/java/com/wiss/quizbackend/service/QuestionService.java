package com.wiss.quizbackend.service;

import com.wiss.quizbackend.dto.QuestionDTO;
import com.wiss.quizbackend.dto.QuestionFormDTO;
import com.wiss.quizbackend.entity.Question;
import com.wiss.quizbackend.exception.CategoryNotFoundException;
import com.wiss.quizbackend.exception.DifficultyNotFoundException;
import com.wiss.quizbackend.exception.QuestionNotFoundException;
import com.wiss.quizbackend.mapper.QuestionMapper;
import com.wiss.quizbackend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Question service.
 */
@Service
public class QuestionService {
    private final QuestionRepository repository;

    /**
     * Instantiates a new Question service.
     *
     * @param repository the repository
     */
    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    /**
     * Gets all questions as dto.
     *
     * @return the all questions as dto
     */
// Neue DTO-basierte Methoden
    public List<QuestionDTO> getAllQuestionsAsDTO() {
        List<Question> entities = repository.findAll();
        return QuestionMapper.toDTOList(entities);
    }

    /**
     * Gets all questions as form dto.
     *
     * @return the all questions as form dto
     */
    public List<QuestionFormDTO> getAllQuestionsAsFormDTO() {
        List<Question> entities = repository.findAll();
        return QuestionMapper.toFormDTOList(entities);
    }

    /**
     * Gets question by id as dto.
     *
     * @param id the id
     * @return the question by id as dto
     */
    public QuestionDTO getQuestionByIdAsDTO(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Question ID cannot be null");
        }

        if (!repository.existsById(id)) {
            throw new QuestionNotFoundException(id);  // ✅ Exception werfen
        }

        Question entity = getQuestionById(id);
        return QuestionMapper.toDTO(entity);
    }

    /**
     * Gets question by id as form dto.
     *
     * @param id the id
     * @return the question by id as form dto
     */
    public QuestionFormDTO getQuestionByIdAsFormDTO(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Question ID cannot be null");
        }

        if (!repository.existsById(id)) {
            throw new QuestionNotFoundException(id);  // ✅ Exception werfen
        }

        Question entity = getQuestionById(id);
        return QuestionMapper.toFormDTO(entity);
    }

    /**
     * Gets questions by category as dto.
     *
     * @param category the category
     * @return the questions by category as dto
     */
    public List<QuestionDTO> getQuestionsByCategoryAsDTO(String category) {
        List<Question> entities = getQuestionsByCategory(category);
        return QuestionMapper.toDTOList(entities);
    }

    /**
     * Gets questions by difficulty as dto.
     *
     * @param difficulty the difficulty
     * @return the questions by difficulty as dto
     */
    public List<QuestionDTO> getQuestionsByDifficultyAsDTO(String difficulty) {
        List<Question> entities = getQuestionsByDifficulty(difficulty);
        return QuestionMapper.toDTOList(entities);
    }

    /**
     * Gets all questions.
     *
     * @return the all questions
     */
    public List<Question> getAllQuestions() {
        return repository.findAll();
    }

    /**
     * Gets question by id.
     *
     * @param id the id
     * @return the question by id
     */
    public Question getQuestionById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Optional<Question> optionalQuestion = repository.findById(id);
        if (optionalQuestion.isEmpty()) {
            throw new QuestionNotFoundException(id);
        }

        return optionalQuestion.get();
    }

    /**
     * Gets questions by category.
     *
     * @param category the category
     * @return the questions by category
     */
    public List<Question> getQuestionsByCategory(String category) {
        validateCategory(category);
        return repository.findByCategory(category.toLowerCase());
    }

    /**
     * Gets questions by difficulty.
     *
     * @param difficulty the difficulty
     * @return the questions by difficulty
     */
    public List<Question> getQuestionsByDifficulty(String difficulty) {
        validateDifficulty(difficulty);
        return repository.findByDifficulty(difficulty.toLowerCase());
    }

    /**
     * Gets total questions count.
     *
     * @return the total questions count
     */
    public long getTotalQuestionsCount() {
        return repository.count();
    }

    /**
     * Create question question dto.
     *
     * @param questionDTO the question dto
     * @return the question dto
     */
    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
        // Validierung
        if (questionDTO.getQuestion() == null || questionDTO.getQuestion().trim().isEmpty()) {
            throw new IllegalArgumentException("Question text is required");
        }
        if (questionDTO.getCorrectAnswer() == null || questionDTO.getCorrectAnswer().trim().isEmpty()) {
            throw new IllegalArgumentException("Correct answer is required");
        }

        // 1. DTO zu Entity konvertieren (ohne ID - ID bleibt null)
        Question entity = QuestionMapper.toEntity(questionDTO);
        // 2. Repository.save() aufrufen (erkennt automatisch CREATE)
        Question newQuestion = repository.save(entity);
        // 3. Gespeicherte Entity zu DTO konvertieren
        QuestionDTO newDTO = QuestionMapper.toDTO(newQuestion); // <- Java gibt dir hier den Hinweis, dass dies gleich als Return zurückgegeben werden kann
        // 4. DTO zurückgeben
        return newDTO; // aus Demozwecken in 2 Zeilen als ein direktes return statement
    }

    /**
     * Create question from form question form dto.
     *
     * @param question the question
     * @return the question form dto
     */
    public QuestionFormDTO createQuestionFromForm(Question question) {
        Question saved = repository.save(question);
        return QuestionMapper.toFormDTO(saved);
    }

    /**
     * Update question question dto.
     *
     * @param id          the id
     * @param questionDTO the question dto
     * @return the question dto
     */
    public QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO) {
        // 1. Prüfen ob Frage existiert (repository.existsById())
        if (!repository.existsById(id)) {
            throw new QuestionNotFoundException(id);
        }
        // 2. DTO zu Entity konvertieren UND ID setzen
        Question entity = QuestionMapper.toEntity(questionDTO);
        entity.setId(id); // ← Wichtig: ID setzen für UPDATE-Erkennung
        // 3. Repository.save() aufrufen (erkennt automatisch UPDATE)
        Question updatedEntity = repository.save(entity);
        // 4. Aktualisierte Entity zu DTO konvertieren
        return QuestionMapper.toDTO(updatedEntity);
    }

    /**
     * Update question from form question form dto.
     *
     * @param id       the id
     * @param question the question
     * @return the question form dto
     */
    public QuestionFormDTO updateQuestionFromForm(Long id, Question question) {
        // Prüfen ob Frage existiert
        if (!repository.existsById(id)) {
            throw new QuestionNotFoundException(id);
        }
        question.setId(id);

        Question updated = repository.save(question);
        return QuestionMapper.toFormDTO(updated);

    }

    /**
     * Delete question.
     *
     * @param id the id
     */
    public void deleteQuestion(Long id) {
        // 1. Prüfen ob Frage existiert
        if (!repository.existsById(id)) {
            throw new QuestionNotFoundException(id);
        }
        // 2. Repository.deleteById() aufrufen
        // 3. Ergebnis zurückgeben
        repository.deleteById(id);
    }

    /**
     * Gets questions by category and difficulty.
     *
     * @param category   the category
     * @param difficulty the difficulty
     * @return the questions by category and difficulty
     */
    public List<QuestionDTO> getQuestionsByCategoryAndDifficulty(String category, String difficulty) {
        validateCategory(category);
        validateDifficulty(difficulty);

        List<Question> entities = repository.findByCategoryAndDifficulty(category, difficulty);
        return QuestionMapper.toDTOList(entities);
    }

    /**
     * Search questions list.
     *
     * @param keyword the keyword
     * @return the list
     */
    public List<QuestionDTO> searchQuestions(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword cannot be empty");
        }

        List<Question> entities = repository.findByQuestionContainingIgnoreCase(keyword.trim());
        return QuestionMapper.toDTOList(entities);
    }

    /**
     * Gets question count by category.
     *
     * @param category the category
     * @return the question count by category
     */
    public long getQuestionCountByCategory(String category) {
        validateCategory(category);
        return repository.countByCategory(category);
    }

    /**
     * Gets random questions.
     *
     * @param limit the limit
     * @return the random questions
     */
    public List<QuestionDTO> getRandomQuestions(int limit) {
        if (limit <= 0 || limit > 50) {
            throw new IllegalArgumentException("Limit must be between 1 and 50");
        }

        List<Question> entities = repository.findRandomQuestions(limit);
        return QuestionMapper.toDTOList(entities);
    }

    /**
     * Gets random questions by category.
     *
     * @param category the category
     * @param limit    the limit
     * @return the random questions by category
     */
    public List<QuestionDTO> getRandomQuestionsByCategory(String category, int limit) {
        validateCategory(category);
        if (limit <= 0 || limit > 50) {
            throw new IllegalArgumentException("Limit must be between 1 and 50");
        }

        List<Question> entities = repository.findRandomByCategory(category, limit);
        return QuestionMapper.toDTOList(entities);
    }

    private void validateCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }

        List<String> validCategories = List.of("sports", "games", "movies", "geography", "science", "history");
        if (!validCategories.contains(category.toLowerCase())) {
            throw new CategoryNotFoundException(category);
        }
    }

    private void validateDifficulty(String difficulty) {
        if (difficulty == null || difficulty.trim().isEmpty()) {
            throw new IllegalArgumentException("Difficulty cannot be null or empty");
        }

        List<String> validDifficulties = List.of("easy", "medium", "hard");
        if (!validDifficulties.contains(difficulty.toLowerCase())) {
            throw new DifficultyNotFoundException(difficulty);
        }
    }
}
