package org.lms.assignment.dtos;
import java.util.ArrayList;
import java.util.List;
import org.lms.assignment.models.AssignmentSubmission;


import lombok.Getter;
import lombok.Setter;


public class AssignmentDto {
    private String title;
    private String description;
    private String dueDate;
    private List <AssignmentSubmission> assignmentSubmissition=new ArrayList<>();
    private Integer id;
    public AssignmentDto (Integer id,String title , String description ,String dueDate){
        this.title=title;
        this.description=description;
        this.dueDate=dueDate;
        this.id=id;
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

    public List<AssignmentSubmission> getAssignmentSubmissition() {
        return assignmentSubmissition;
    }

    public void setAssignmentSubmissition(List<AssignmentSubmission> assignmentSubmissition) {
        this.assignmentSubmissition = assignmentSubmissition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

