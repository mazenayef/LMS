package org.lms.Quiz.Repos.QuestionRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import org.lms.Quiz.DTOs.QuestionsDTOs.QuestionSet;
import org.lms.Quiz.Models.Difficulty;
import org.lms.Quiz.Models.Question;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepository{

    public Question create(Question question) {
        QuestionRepository.questions.add(question);
        return question;
    }

    public Collection<Question> filter(Map<String, Object> criteria) {
        return questions.stream()
                .filter(p -> criteria.entrySet().stream()
                .allMatch(entry -> p.getAttributeValue(entry.getKey()).equals(entry.getValue())))
                .collect(Collectors.toList());
    }

    public ArrayList<Question> getAll() {
        return questions;
    }

    public Question update(Question question, QuestionSet questionSet) {
            question.setBody(questionSet.body);
            question.setChoices(questionSet.choices);
            question.setCorrectChoice(questionSet.correctChoice);
            question.setCourseId(questionSet.courseId);
            question.setDifficulty(questionSet.difficulty);
            return question;
    }

    public void delete(Question question) {
        Iterator<Question> iterator = questions.iterator();
        while (iterator.hasNext()) {
            Question q = iterator.next();
            if (q.equals(question)) {
                iterator.remove();
            }
        }
    }
    
    public static ArrayList<Question> questions = new ArrayList<Question>(Arrays.asList(
        new Question(1,0, 1, "What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Madrid"}, Difficulty.MEDIUM),
        new Question(2,0, 1, "Which planet is closest to the Sun?", new String[]{"Mercury", "Venus", "Earth", "Mars"}, Difficulty.EASY),
        new Question(3,0, 1, "What is the largest country in the world by land area?", new String[]{"Russia", "Canada", "China", "United States"}, Difficulty.MEDIUM),
        new Question(4,0, 1, "Who painted the Mona Lisa?", new String[]{"Leonardo da Vinci", "Michelangelo", "Raphael", "Donatello"}, Difficulty.EASY),
        new Question(5, 0, 1, "What is the chemical symbol for gold?", new String[]{"Au", "Ag", "Cu", "Fe"}, Difficulty.HARD)
    ));
}
