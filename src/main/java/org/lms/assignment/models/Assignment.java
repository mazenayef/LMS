package org.lms.assignment.models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.Media;

import org.lms.mediafiles.dtos.MediaFileResourceDto;
import org.lms.mediafiles.models.MediaFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
public class Assignment {
    static private Integer count=0;
    private Integer id;
    private String title;
    private String description;
    private String dueDate;
    private MediaFileResourceDto media;
    private Integer courseID;
    private List <AssignmentSubmission> assignmentSubmation=new ArrayList<>();
    public Assignment (String title , String description ,String dueDate,MediaFileResourceDto media,Integer courseID){
        this.title=title;
        this.description=description;
        this.courseID=courseID;
        this.dueDate=dueDate;
        this.media=media;
        this.id=count+1;
        count++;
    }
    public Assignment (String title , String description ,String dueDate,Integer courseID){
        this.title=title;
        this.description=description;
        this.courseID=courseID;
        this.dueDate=dueDate;
        this.id=count+1;
        count++;
    }
}
