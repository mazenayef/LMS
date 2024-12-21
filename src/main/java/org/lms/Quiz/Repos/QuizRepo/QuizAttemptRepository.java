package org.lms.Quiz.Repos.QuizRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.lms.Quiz.DTOs.Pair;
import org.lms.Quiz.Models.QuizAttempt;
import org.springframework.stereotype.Repository;

@Repository
public class QuizAttemptRepository {
    public static ArrayList<QuizAttempt> attempts = new ArrayList<QuizAttempt>(Arrays.asList(
        new QuizAttempt(1, 1, -1, new ArrayList<Pair<Integer,Integer>>(Arrays.asList(new Pair<Integer,Integer>(1, 1))), LocalDateTime.now(), false),
        new QuizAttempt(2, 1, 80, new ArrayList<Pair<Integer,Integer>>(Arrays.asList(new Pair<Integer,Integer>(1, 1))), LocalDateTime.now(), true),
        new QuizAttempt(3, 1, 100, new ArrayList<Pair<Integer,Integer>>(Arrays.asList(new Pair<Integer,Integer>(1, 1))), LocalDateTime.now(), true)
    ));;

    public QuizAttempt create(QuizAttempt quizAttempt){
        attempts.add(quizAttempt);
        return quizAttempt;
    }

    public Collection<QuizAttempt> filter(Map<String,Object> criteria){
        return attempts.stream()
        .filter(p -> criteria.entrySet().stream()
        .allMatch(entry -> p.getAttributeValue(entry.getKey()).equals(entry.getValue())))
        .collect(Collectors.toList());
    }

    public void delete(QuizAttempt attempt){
        attempts.remove(attempt);
    }
}