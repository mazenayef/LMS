package org.lms.assignment.dtos;

import org.lms.assignment.models.Assignment;
import org.lms.mediafiles.dtos.MediaFileResourceDto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AssignmentSubmissionDto {
    private Integer id;
    private String createdAt;
    private MediaFileResourceDto media;
    private double grade;
    private boolean corrected;
    public AssignmentSubmissionDto(Integer id,String createdAt,MediaFileResourceDto media,double grade,boolean corrected){
        this.createdAt=createdAt;
        this.media=media;
        this.grade=grade;
        this.corrected=corrected;
        this.id=id;
    }
}
