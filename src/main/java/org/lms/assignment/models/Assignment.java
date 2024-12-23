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
    }
    public Assignment (String title , String description ,String dueDate,Integer courseID){
        this.title=title;
        this.description=description;
        this.courseID=courseID;
        this.dueDate=dueDate;
        this.id=count+1;
    }

    public static Integer getCount() {
        return count;
    }

    public static void setCount(Integer count) {
        Assignment.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public MediaFileResourceDto getMedia() {
        return media;
    }

    public void setMedia(MediaFileResourceDto media) {
        this.media = media;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public List<AssignmentSubmission> getAssignmentSubmation() {
        return assignmentSubmation;
    }

    public void setAssignmentSubmation(List<AssignmentSubmission> assignmentSubmation) {
        this.assignmentSubmation = assignmentSubmation;
    }
}
