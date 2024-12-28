package org.lms.Quiz.DTOs.QuizDTOs;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Range;
import org.lms.Quiz.Models.Difficulty;
import org.lms.Quiz.Models.Quiz;

import jakarta.validation.constraints.NotNull;

public class QuizSet {
    @NotNull
    public int courseId;
    @NotNull
    public int weight;

    @NotNull
    @Range(min = 5, max = 15)
    public Difficulty[] difficulties;

    @NotNull
    @Range(min = 10, max = 60)
    public int duration;
    
    @NotNull
    public LocalDateTime startTime;

    public Quiz toObject(){
        return new Quiz(courseId,weight,difficulties,duration,startTime);
    }
}
