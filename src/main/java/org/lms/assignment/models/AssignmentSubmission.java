package org.lms.assignment.models;

import java.sql.Time;

import javax.print.attribute.standard.Media;

import org.lms.assignment.dtos.AssignmentDto;
import org.lms.mediafiles.dtos.MediaFileResourceDto;
import org.lms.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
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
    }
    public AssignmentSubmission(String createdAt, MediaFileResourceDto media ,User student,Integer assignmentID,double grade,boolean corrected){
        this.createdAt=createdAt;
        this.media=media;
        this.student=student;
        this.assignmentID=assignmentID;
        id=count+1;
        this.corrected=corrected;
        this.grade=grade;
    }

    public Integer getId(){
        return id;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public MediaFileResourceDto getMedia(){
        return media;
    }

    public User getStudent(){
        return student;
    }

    public Integer getAssignmentID(){
        return assignmentID;
    }

    public double getGrade(){
        return grade;
    }

    public boolean isCorrected(){
        return corrected;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt=createdAt;
    }

    public void setMedia(MediaFileResourceDto media){
        this.media=media;
    }

    public void setStudent(User student){
        this.student=student;
    }

    public void setAssignmentID(Integer assignmentID){
        this.assignmentID=assignmentID;
    }

    public void setGrade(double grade){
        this.grade=grade;
    }

    public void setCorrected(boolean corrected){
        this.corrected=corrected;
    }
}
