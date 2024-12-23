package org.lms.assignment.dtos;
import java.util.ArrayList;
import java.util.List;
import org.lms.assignment.models.AssignmentSubmission;
import org.lms.mediafiles.dtos.MediaFileResourceDto;
import lombok.Getter;
import lombok.Setter;


public class AssignmentDto {
    private Integer id;
    private String title;
    private String description;
    private String dueDate;
    private MediaFileResourceDto media;
    private List <AssignmentSubmission> assignmentSubmissition=new ArrayList<>();
    public AssignmentDto (Integer id,String title , String description ,String dueDate,MediaFileResourceDto media){
        this.id=id;
        this.title=title;
        this.description=description;
        this.dueDate=dueDate;
        this.media=media;
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

    public List<AssignmentSubmission> getAssignmentSubmation() {
        return assignmentSubmation;
    }

    public void setAssignmentSubmation(List<AssignmentSubmission> assignmentSubmation) {
        this.assignmentSubmation = assignmentSubmation;
    }
}

