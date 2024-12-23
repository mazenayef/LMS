package org.lms.assignment.dtos;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentSubmissionDto {
    private Integer id;
    private String createdAt;
    private double grade;
    private boolean corrected;
    public AssignmentSubmissionDto(Integer id,String createdAt,double grade,boolean corrected){
        this.createdAt=createdAt;
        this.grade=grade;
        this.corrected=corrected;
        this.id=id;
    }

    // public String getCreatedAt() {
    //     return createdAt;
    // }

    // public void setCreatedAt(String createdAt) {
    //     this.createdAt = createdAt;
    // }

    // public MediaFileResourceDto getMedia() {
    //     return media;
    // }

    // public void setMedia(MediaFileResourceDto media) {
    //     this.media = media;
    // }

    // public double getGrade() {
    //     return grade;
    // }

    // public void setGrade(double grade) {
    //     this.grade = grade;
    // }

    // public boolean isCorrected() {
    //     return corrected;
    // }

    // public void setCorrected(boolean corrected) {
    //     this.corrected = corrected;
    // }
    // public void setId(Integer id){
    //     this.id=id;
    // }
}
