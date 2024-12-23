package org.lms.assignment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.resolve;

import java.util.ArrayList;
import java.util.List;
import org.lms.assignment.dtos.AssignmentDto;
import org.lms.assignment.models.Assignment;
import org.lms.assignment.services.AssignmentServices;
import org.lms.authentication.interceptors.CurrentUser;
import org.lms.authentication.interceptors.HasRole;
import org.lms.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;






@Controller
@RestController
@RequestMapping("courses/{courseID}/assignment")
public class AssignmentController {
    private AssignmentServices assignmentService;
    public AssignmentController(AssignmentServices assignmentServices){
        this.assignmentService=assignmentServices;
    }
    @GetMapping("/")
    public ResponseEntity<List<AssignmentDto>> getAllAssignment(@PathVariable("courseID") String courseID,@CurrentUser User currentUser){
        List<Assignment> assignments=new ArrayList<>();
        List<AssignmentDto>assignmentDtos= new ArrayList<>();
        try {
            assignments=assignmentService.getAssignmentlList(Integer.parseInt(courseID));
            assignmentDtos=assignmentService.convetToDtoList(assignments);
            return ResponseEntity.ok(assignmentDtos);
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(null);
        }        
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDto> getMethodName(@PathVariable("courseID") String courseID,@PathVariable("id") String id) {
        try {
            Assignment assignment= assignmentService.getAssignment(Integer.parseInt(courseID),Integer.parseInt(id));
            AssignmentDto assignmentDto=assignmentService.convetToDto(assignment);
            return ResponseEntity.ok(assignmentDto);
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(null);
        }
    }
    @HasRole({"INSTRUCTOR"})
    @PostMapping("/") 
    public ResponseEntity<Assignment> addAssignment(@PathVariable("courseID") String courseID,@RequestBody AssignmentDto assignment ,@CurrentUser User currentUser ) {
        try {
            assignmentService.addAssignment(assignment, Integer.parseInt(courseID));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @HasRole({"INSTRUCTOR"})
    @PutMapping("/{id}")
    public ResponseEntity<AssignmentDto> updateAssignment(@PathVariable("id") String id, @RequestBody AssignmentDto assignment,@CurrentUser User currentUser,@PathVariable("courseID")String CourseID) {
        try {
            AssignmentDto updatedassignment=assignmentService.updateAssignment(assignment,Integer.parseInt(id), Integer.parseInt(CourseID));
            return ResponseEntity.ok(updatedassignment);
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(null);
        }
    }
    @HasRole({"INSTRUCTOR"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Assignment> deleteAssignment(@PathVariable("id") String id,@CurrentUser User currentUser){
        try {
            assignmentService.deleteAssignment(Integer.parseInt(id));
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(null);
        }
    }

}
