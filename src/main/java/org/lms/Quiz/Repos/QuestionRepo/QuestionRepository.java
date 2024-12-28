package org.lms.Quiz.Repos.QuestionRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.lms.Quiz.DTOs.QuestionsDTOs.QuestionGet;
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

    public QuestionGet getQuestionQuizzable(Integer id) {
        Question question = questions.stream().filter(q -> q.getId() == id).findFirst().orElse(null);
        if (question == null) {
            return null;
        }
        return QuestionGet.fromQuestion(question);
    }
    
    public static ArrayList<Question> questions = new ArrayList<Question>();
}
