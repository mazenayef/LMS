package org.lms.assignment.dtos;

import org.lms.assignment.models.Assignment;
import org.lms.mediafiles.dtos.MediaFileResourceDto;

import lombok.Getter;
import lombok.Setter;
public class AssignmentSubmissionDto {
    private String createdAt;
    private MediaFileResourceDto media;
    private double grade;
    private boolean corrected;
    public AssignmentSubmissionDto(String createdAt,MediaFileResourceDto media,double grade,boolean corrected){
        this.createdAt=createdAt;
        this.media=media;
        this.grade=grade;
        this.corrected=corrected;
    }
    public String getCreatedAt(){
        return createdAt;
    }
    public MediaFileResourceDto getMedia(){
        return media;
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
    public void setGrade(double grade){
        this.grade=grade;
    }
    public void setCorrected(boolean corrected){
        this.corrected=corrected;
    }

}
