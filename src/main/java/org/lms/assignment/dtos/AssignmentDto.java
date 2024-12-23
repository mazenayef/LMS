package org.lms.assignment.dtos;
import java.util.ArrayList;
import java.util.List;
import org.lms.assignment.models.AssignmentSubmission;
import org.lms.mediafiles.dtos.MediaFileResourceDto;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
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
}

