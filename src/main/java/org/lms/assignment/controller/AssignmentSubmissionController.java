package org.lms.assignment.controller;


import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.resolve;

import java.util.List;

import org.lms.assignment.dtos.AssignmentSubmissionDto;
import org.lms.assignment.models.AssignmentSubmission;
import org.lms.assignment.services.AssignmentSubmissionServices;
import org.lms.authentication.interceptors.CurrentUser;
import org.lms.authentication.interceptors.HasRole;
import org.lms.user.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RestController
@RequestMapping("course/{courseID}/assignment/{assignmentID}")
public class AssignmentSubmissionController {
    private AssignmentSubmissionServices assignmentSubmissionServices;
    public AssignmentSubmissionController(AssignmentSubmissionServices assignmentSubmissionServices){
        this.assignmentSubmissionServices=assignmentSubmissionServices;
    }

@HasRole({"INSTRUCTOR"})
@GetMapping("/submissions")
public ResponseEntity<List <AssignmentSubmissionDto>> getAllSubmission(@PathVariable("assignmentID") String id ,@CurrentUser User currentUser) {
    try {
        List <AssignmentSubmission> assignmentSubmission=assignmentSubmissionServices.getAssignmentSubmissionList(Integer.parseInt(id));
        List <AssignmentSubmissionDto> assignmentSubmissionDtos=assignmentSubmissionServices.convetToDtoList(assignmentSubmission);
        return ResponseEntity.ok(assignmentSubmissionDtos);
    } catch (Exception e) {
        return ResponseEntity.status(NOT_FOUND).body(null);
    }
}
@HasRole({"STUDENT"})
@GetMapping("/")
public ResponseEntity<AssignmentSubmissionDto> getStudentSubmission(@CurrentUser User currentUser){
    try {
        AssignmentSubmission assignmentSubmissions= assignmentSubmissionServices.getAssignmentSubmission(currentUser.getId());
        AssignmentSubmissionDto assignmentSubmissionDtos=assignmentSubmissionServices.convetToDto(assignmentSubmissions);
        return ResponseEntity.ok(assignmentSubmissionDtos);
    } catch (Exception e) {
        return ResponseEntity.status(NOT_FOUND).body(null);
    }
}

@HasRole({"STUDENT"})
@DeleteMapping("/{id}")
public ResponseEntity<AssignmentSubmission>deleteAssignmentSubmission(@PathVariable Integer id,@CurrentUser User currentUser){
    try {
        assignmentSubmissionServices.deleteAssignmentsubmission(id);
        return ResponseEntity.ok().body(null);
    } catch (Exception e) {
        return ResponseEntity.status(NOT_FOUND).body(null);
    }
}

@HasRole({"STUDENT"})
@PostMapping("/")
public ResponseEntity<AssignmentSubmissionDto> postAssignmetSubmission(@RequestBody AssignmentSubmissionDto newAssignmentSubmission,@CurrentUser User currentUser,@PathVariable("courseID") String courseID,@PathVariable ("assignmentID")String assignmentID) {
    try {
        assignmentSubmissionServices.createSubmission(newAssignmentSubmission,currentUser,Integer.parseInt(assignmentID));
        return ResponseEntity.ok().body(null);
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }
}

@HasRole({"INSTRUCTOR"})
@PutMapping("/{id}/correct")
public ResponseEntity<AssignmentSubmissionDto> correctAssignmentSubmission(@PathVariable("id") String id,@RequestBody AssignmentSubmissionDto newAssignmentSubmission,@CurrentUser User currentUser) {
    try {
        AssignmentSubmissionDto assignmentSubmissiondDto= assignmentSubmissionServices.correctAssignmentSubmission(Integer.parseInt(id), newAssignmentSubmission,currentUser);
        if(assignmentSubmissiondDto.isCorrected()==true){
            // send Notification
        }
        return ResponseEntity.ok(assignmentSubmissiondDto);
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
        }
}

@HasRole({"STUDENT"})
@PutMapping("/{id}")
public ResponseEntity<AssignmentSubmissionDto> putMethodName(@PathVariable Integer id, @RequestBody AssignmentSubmissionDto newAssignmentSubmission,@CurrentUser User currentUser) {
    try {
        AssignmentSubmissionDto assignmentSubmissionDto=assignmentSubmissionServices.updateAssignmentSubmission(id, newAssignmentSubmission,currentUser);
        return ResponseEntity.ok(assignmentSubmissionDto);
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }
}

}
