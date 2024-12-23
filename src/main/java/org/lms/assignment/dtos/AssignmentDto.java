package org.lms.assignment.dtos;
import java.util.ArrayList;
import java.util.List;
import org.lms.assignment.models.AssignmentSubmission;
import org.lms.mediafiles.dtos.MediaFileResourceDto;
import lombok.Getter;
import lombok.Setter;
public class AssignmentDto {
    private String title;
    private String description;
    private String dueDate;
    private MediaFileResourceDto media;
    private List <AssignmentSubmission> assignmentSubmation=new ArrayList<>();
    public AssignmentDto (String title , String description ,String dueDate,MediaFileResourceDto media){
        this.title=title;
        this.description=description;
        this.dueDate=dueDate;
        this.media=media;
    }

    public String getTitle (){
        return title;
    }
    public String getDescription (){
        return description;
    }
    public String getDueDate (){
        return dueDate;
    }
    public MediaFileResourceDto getMedia (){
        return media;
    }
    public List <AssignmentSubmission> getAssignmentSubmation (){
        return assignmentSubmation;
    }
    public void setTitle (String title){
        this.title=title;
    }
    public void setDescription (String description){
        this.description=description;
    }
    public void setDueDate (String dueDate){
        this.dueDate=dueDate;
    }
    public void setMedia (MediaFileResourceDto media){
        this.media=media;
    }
    public void setAssignmentSubmation (List <AssignmentSubmission> assignmentSubmation){
        this.assignmentSubmation=assignmentSubmation;
    }
}

