package org.lms.Quiz.DTOs.QuestionsDTOs;

import org.lms.Quiz.Models.Difficulty;

public class QuestionSet {
    public String body;
    public int correctChoice;
    public int courseId;
    public String[] choices;
    public Difficulty difficulty;
}
