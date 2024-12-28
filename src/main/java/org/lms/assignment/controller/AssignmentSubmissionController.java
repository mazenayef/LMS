package org.lms.assignment.controller;




import java.util.List;

import javax.mail.Multipart;

import org.lms.assignment.dtos.AssignmentSubmissionDto;
import org.lms.assignment.models.AssignmentSubmission;
import org.lms.assignment.services.AssignmentSubmissionServices;
import org.lms.authentication.interceptors.CurrentUser;
import org.lms.authentication.interceptors.HasRole;
import org.lms.mediafiles.models.MediaFile;
import org.lms.shared.exceptions.HttpBadRequestException;
import org.lms.shared.interceptors.ExcludeFromCommonResponse;
import org.lms.user.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RestController
@RequestMapping("course/{courseID}/assignment/{assignmentID}")
public class AssignmentSubmissionController {
    private AssignmentSubmissionServices assignmentSubmissionServices;
    public AssignmentSubmissionController(AssignmentSubmissionServices assignmentSubmissionServices){
        this.assignmentSubmissionServices=assignmentSubmissionServices;
    }


@GetMapping("/{submissionID}/attachments")
public ResponseEntity<List<MediaFile>> getAttachments(@PathVariable ("submissionID")String submissionID) throws Exception{
    return ResponseEntity.ok(assignmentSubmissionServices.getAttachments(Integer.parseInt(submissionID)));
}

@ExcludeFromCommonResponse
@HasRole({"STUDENT"})
@PostMapping("/{submissionID}/attachments")
public ResponseEntity<MediaFile> addAttachment(@PathVariable ("submissionID")String submissionID,@RequestParam ("file")MultipartFile file) throws Exception {
    return ResponseEntity.ok(assignmentSubmissionServices.addAttachment(Integer.parseInt(submissionID), file));
}

@HasRole({"INSTRUCTOR"})
@GetMapping("/submissions")
public ResponseEntity<List <AssignmentSubmissionDto>> getAllSubmission(@PathVariable("assignmentID") String id ,@CurrentUser User currentUser) throws Exception {
        List <AssignmentSubmission> assignmentSubmission=assignmentSubmissionServices.getAssignmentSubmissionList(Integer.parseInt(id));
        List <AssignmentSubmissionDto> assignmentSubmissionDtos=assignmentSubmissionServices.convetToDtoList(assignmentSubmission);
        return ResponseEntity.ok(assignmentSubmissionDtos);
}


@HasRole({"STUDENT"})
@GetMapping("/")
public ResponseEntity<AssignmentSubmissionDto> getStudentSubmission(@CurrentUser User currentUser) throws Exception{
        AssignmentSubmission assignmentSubmissions= assignmentSubmissionServices.getAssignmentSubmission(currentUser.getId());
        AssignmentSubmissionDto assignmentSubmissionDtos=assignmentSubmissionServices.convetToDto(assignmentSubmissions);
        return ResponseEntity.ok(assignmentSubmissionDtos);
}

@HasRole({"STUDENT"})
@DeleteMapping("/{id}")
public ResponseEntity<AssignmentSubmission>deleteAssignmentSubmission(@PathVariable Integer id,@CurrentUser User currentUser) throws Exception{
        assignmentSubmissionServices.deleteAssignmentsubmission(id);
        return ResponseEntity.ok().build();
}

@HasRole({"STUDENT"})
@PostMapping("/")
public ResponseEntity<AssignmentSubmissionDto> postAssignmetSubmission(@RequestBody AssignmentSubmissionDto newAssignmentSubmission,@CurrentUser User currentUser,@PathVariable("courseID") String courseID,@PathVariable ("assignmentID")String assignmentID) throws Exception{

        assignmentSubmissionServices.createSubmission(newAssignmentSubmission,currentUser,Integer.parseInt(assignmentID));
        return ResponseEntity.ok().build();
}

@HasRole({"INSTRUCTOR"})
@PutMapping("/{id}/marked")
public ResponseEntity<AssignmentSubmissionDto> correctAssignmentSubmission(@PathVariable("id") String id,@PathVariable ("courseID") String courseID,@RequestBody AssignmentSubmissionDto newAssignmentSubmission,@CurrentUser User currentUser) throws Exception {
        AssignmentSubmissionDto assignmentSubmissiondDto= assignmentSubmissionServices.markAssignmentSubmission(Integer.parseInt(id), newAssignmentSubmission,currentUser,Integer.parseInt(courseID));
        if(assignmentSubmissiondDto.isCorrected()==true){
            return ResponseEntity.ok(assignmentSubmissiondDto);
        }
        else
            throw new HttpBadRequestException("invalid data");
    
}

@HasRole({"STUDENT"})
@PutMapping("/{id}")
public ResponseEntity<AssignmentSubmissionDto> putAssignmentSubmission(@PathVariable Integer id, @RequestBody AssignmentSubmissionDto newAssignmentSubmission,@CurrentUser User currentUser) throws Exception {
        if(newAssignmentSubmission.getGrade()==0 && !newAssignmentSubmission.isCorrected()){
        AssignmentSubmissionDto assignmentSubmissionDto=assignmentSubmissionServices.updateAssignmentSubmission(id, newAssignmentSubmission,currentUser);
        return ResponseEntity.ok(assignmentSubmissionDto);
        }
        else
            throw new HttpBadRequestException("invalid");
}

}
