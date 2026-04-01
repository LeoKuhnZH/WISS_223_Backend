package com.wiss.quizbackend.mapper;

import com.wiss.quizbackend.dto.QuestionDTO;
import com.wiss.quizbackend.dto.QuestionFormDTO;
import com.wiss.quizbackend.entity.AppUser;
import com.wiss.quizbackend.entity.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Question mapper.
 */
public class QuestionMapper {

    /**
     * To dto question dto.
     *
     * @param entity the entity
     * @return the question dto
     */
    public static QuestionDTO toDTO(Question entity) {
        if (entity == null) {
            return null;
        }

        // Alle Antworten sammeln
        List<String> allAnswers = new ArrayList<>();
        allAnswers.addAll(entity.getIncorrectAnswers());
        allAnswers.add(entity.getCorrectAnswer());

        // Antworten mischen - wichtig für Frontend!
        Collections.shuffle(allAnswers);

        return new QuestionDTO(
                entity.getId(),               // ← ID hinzufügen!
                entity.getQuestion(),
                entity.getCorrectAnswer(),
                allAnswers,
                entity.getCategory(),
                entity.getDifficulty()
        );
    }

    /**
     * To form dto question form dto.
     *
     * @param entity the entity
     * @return the question form dto
     */
    public static QuestionFormDTO toFormDTO(Question entity) {
        if (entity == null) {
            return null;
        }

        AppUser creator = entity.getCreatedBy();
        String createdUsername = (creator != null)
                ? creator.getUsername() : "Unknown";
        Long creatorId = (creator != null) ? creator.getId() : null;

        return new QuestionFormDTO(
                entity.getId(),
                entity.getQuestion(),
                entity.getCorrectAnswer(),
                entity.getIncorrectAnswers(),
                entity.getCategory(),
                entity.getDifficulty(),
                createdUsername,
                creatorId
        );
    }

    /**
     * To entity question.
     *
     * @param dto the dto
     * @return the question
     */
    public static Question toEntity(QuestionDTO dto) {
        if (dto == null) {
            return null;
        }

        // correctAnswer aus answers herausfiltern
        List<String> incorrectAnswers = dto.getAnswers().stream()
                .filter(answer -> !answer.equals(dto.getCorrectAnswer()))
                .toList();

        return new Question(
                dto.getQuestion(),
                dto.getCorrectAnswer(),
                incorrectAnswers,
                dto.getCategory(),
                dto.getDifficulty(),
                null
        );
    }

    /**
     * To dto list list.
     *
     * @param entities the entities
     * @return the list
     */
    public static List<QuestionDTO> toDTOList(List<Question> entities) {
        return entities.stream()
                .map(QuestionMapper::toDTO)
                .toList();
    }

    /**
     * To form dto list list.
     *
     * @param entities the entities
     * @return the list
     */
    public static List<QuestionFormDTO> toFormDTOList(List<Question> entities) {
        return entities.stream()
                .map(QuestionMapper::toFormDTO)
                .toList();
    }
}
