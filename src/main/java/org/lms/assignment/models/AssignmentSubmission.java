package org.lms.assignment.models;

import java.sql.Time;

import javax.print.attribute.standard.Media;

import org.lms.assignment.dtos.AssignmentDto;
import org.lms.mediafiles.dtos.MediaFileResourceDto;
import org.lms.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class AssignmentSubmission {
    static private Integer count=0;
    private Integer id;
    private String createdAt;
    private MediaFileResourceDto media;
    private User student;
    private Integer assignmentID;
    private double grade;
    private boolean corrected;
    
    public AssignmentSubmission(String createdAt, MediaFileResourceDto media ,User student,Integer assignmentID){
        this.createdAt=createdAt;
        this.media=media;
        this.student=student;
        this.assignmentID=assignmentID;
        id=count+1;
        corrected=false;
        count++;
    }
    public AssignmentSubmission(String createdAt, MediaFileResourceDto media ,User student,Integer assignmentID,double grade,boolean corrected){
        this.createdAt=createdAt;
        this.media=media;
        this.student=student;
        this.assignmentID=assignmentID;
        id=count+1;
        this.corrected=corrected;
        this.grade=grade;
        count++;
    }

    public static Integer getCount() {
        return count;
    }

    public static void setCount(Integer count) {
        AssignmentSubmission.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public MediaFileResourceDto getMedia() {
        return media;
    }

    public void setMedia(MediaFileResourceDto media) {
        this.media = media;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Integer getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(Integer assignmentID) {
        this.assignmentID = assignmentID;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public boolean isCorrected() {
        return corrected;
    }

    public void setCorrected(boolean corrected) {
        this.corrected = corrected;
    }
}
