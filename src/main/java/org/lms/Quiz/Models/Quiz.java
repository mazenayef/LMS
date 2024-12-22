package org.lms.Quiz.Models;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;

public class Quiz {
    @NotNull
    private int id;
    @NotNull
    private int courseId;
    @NotNull
    private int weight;

    @NotNull
    @Range(min = 5, max = 15)
    private int numberOfQuestions;
    private Difficulty[] difficulties;

    @NotNull
    @Range(min = 10, max = 60)
    private int duration;
    
    @NotNull
    private LocalDateTime startTime;

    public Quiz(@NotNull int courseId, @NotNull int weight,
        Difficulty[] difficulties, @NotNull @Range(min = 10, max = 60) int duration,
        @NotNull LocalDateTime statiTime) {
        this.courseId = courseId;
        this.weight = weight;
        this.numberOfQuestions = difficulties.length;
        this.difficulties = difficulties;
        this.duration = duration;
        this.startTime = statiTime;
    }

    public Quiz(@NotNull int id, @NotNull int courseId, @NotNull int weight, Difficulty[] difficulties,
        @NotNull @Range(min = 10, max = 60) int duration, @NotNull LocalDateTime startTime) {
        this.id = id;
        this.courseId = courseId;
        this.weight = weight;
        this.numberOfQuestions = difficulties.length;
        this.difficulties = difficulties;
        this.duration = duration;
        this.startTime = startTime;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }
    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
    public Difficulty[] getDifficulty() {
        return difficulties;
    }
    public void setDifficulty(Difficulty[] difficulties) {
        this.difficulties = difficulties;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime statiTime) {
        this.startTime = statiTime;
    }

    public Object getAttributeValue(String key) {
        switch (key) {
            case "id":
                return id;
            case "courseId":
                return courseId;
            case "weight":
                return weight;
            case "numberOfQuestions":
                return numberOfQuestions;
            case "difficulties":
                return difficulties;
            case "duration":
                return duration;
            case "startTime":
                return startTime;
            default:
                throw new IllegalArgumentException("Invalid attribute name: " + key);
        }
    }
}