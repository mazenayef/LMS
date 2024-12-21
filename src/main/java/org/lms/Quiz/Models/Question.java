package org.lms.Quiz.Models;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Question {
    @NotNull
    private int id;
    @NotEmpty
    private String body;
    @NotNull
    @Range(min = 0, max = 4)
    private int correctChoice;
    @NotNull
    private int courseId;
    private String[] choices;
    @NotNull
    private Difficulty difficulty;
    
    public Question(int correctChoice, int courseId, String body, String[] choices, Difficulty difficulty) {
        this.correctChoice = correctChoice;
        this.body = body;
        this.courseId = courseId;
        this.choices = choices;
        this.difficulty = difficulty;
    }
    public Question(int id,int correctChoice, int courseId, String body, String[] choices, Difficulty difficulty) {
        this.id = id;
        this.correctChoice = correctChoice;
        this.body = body;
        this.courseId = courseId;
        this.choices = choices;
        this.difficulty = difficulty;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public int getCorrectChoice() {
        return correctChoice;
    }
    public void setCorrectChoice(int correctChoice) {
        this.correctChoice = correctChoice;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public String[] getChoices() {
        return choices;
    }
    public void setChoices(String[] choices) {
        this.choices = choices;
    }
    public Difficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Object getAttributeValue(String key) {
        switch (key) {
            case "id":
                return id;
            case "body":
                return body;
            case "correctChoice":
                return correctChoice;
            case "courseId":
                return courseId;
            case "choices":
                return choices;
            case "difficulty":
                return difficulty;
            default:
                throw new IllegalArgumentException("Invalid attribute name: " + key);
        }
    }
}
