package org.lms.assignment.dtos;




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
