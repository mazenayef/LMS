package org.lms.Quiz.Repos.QuizRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.lms.Quiz.DTOs.QuizDTOs.QuizSet;
import org.lms.Quiz.Models.Difficulty;
import org.lms.Quiz.Models.Quiz;
import org.springframework.stereotype.Repository;

@Repository
public class QuizRepository {

    public Quiz create(Quiz quiz) {
        quizzes.add(quiz);
        return quiz;
    }

    public Collection<Quiz> filter(Map<String, Object> criteria) {
        return quizzes.stream()
                .filter(p -> criteria.entrySet().stream()
                .allMatch(entry -> p.getAttributeValue(entry.getKey()).equals(entry.getValue())))
                .collect(Collectors.toList());
    }

    public ArrayList<Quiz> getAll() {
        return quizzes;
    }

    public Quiz update(Quiz quiz, QuizSet quizSet) {
        quiz.setCourseId(quizSet.courseId);
        quiz.setDifficulty(quizSet.difficulties);
        quiz.setDuration(quizSet.duration);
        quiz.setNumberOfQuestions(quizSet.difficulties.length);
        quiz.setStartTime(quizSet.startTime);
        quiz.setWeight(quizSet.weight);
        return quiz;
    }

    public void delete(Quiz quiz) {
        quizzes.remove(quiz);
    }
    
    public static ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
}