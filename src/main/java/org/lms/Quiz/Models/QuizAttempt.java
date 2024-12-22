package org.lms.Quiz.Models;

import java.time.LocalDateTime;
import java.util.List;

import org.lms.Quiz.DTOs.Pair;


public class QuizAttempt {
    private int id;
    private int studentId;
    private int quizId;
    private int grade;
    private List<Pair<Integer,Integer>> answers;    //two foriegn keys one for the question the second for the answer
    private LocalDateTime createdAt;
    private boolean submitted = false;
    
    public QuizAttempt(){ }
    
    public QuizAttempt(int studentId, int quizId, int grade, List<Pair<Integer, Integer>> answers,
            LocalDateTime createdAt, boolean submitted) {
        this.studentId = studentId;
        this.quizId = quizId;
        this.grade = grade;
        this.answers = answers;
        this.createdAt = createdAt;
        this.submitted = submitted;
    }
    public QuizAttempt(int id, int studentId, int quizId, int grade, List<Pair<Integer, Integer>> answers,
            LocalDateTime createdAt, boolean submitted) {
        this.id = id;
        this.studentId = studentId;
        this.quizId = quizId;
        this.grade = grade;
        this.answers = answers;
        this.createdAt = createdAt;
        this.submitted = submitted;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public int getQuizId() {
        return quizId;
    }
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }
    public int getGrade() {
        return grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }
    public List<Pair<Integer, Integer>> getAnswers() {
        return answers;
    }
    public void setAnswers(List<Pair<Integer, Integer>> answers) {
        this.answers = answers;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public boolean getSubmitted(){
        return this.submitted;
    }
    public void setSubmitted(boolean submitted){
        this.submitted = submitted;
    }
    public Object getAttributeValue(String key) {
        switch (key) {
            case "id":
                return id;
            case "studentId":
                return studentId;
            case "quizId":
                return quizId;
            case "grade":
                return grade;
            case "answers":
                return answers;
            case "createdAt":
                return createdAt;
            case "submitted":
                return submitted;
            default:
                return null; 
        }
    }
}
