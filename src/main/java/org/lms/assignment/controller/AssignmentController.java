package org.lms.assignment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import org.lms.assignment.dtos.AssignmentDto;
import org.lms.assignment.models.Assignment;
import org.lms.assignment.services.AssignmentServices;
import org.lms.authentication.interceptors.CurrentUser;
import org.lms.authentication.interceptors.HasRole;
import org.lms.mediafiles.models.MediaFile;
import org.lms.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;







@Controller
@RestController
@RequestMapping("courses/{courseID}/assignment")
public class AssignmentController {
    private AssignmentServices assignmentService;
    public AssignmentController(AssignmentServices assignmentServices){
        this.assignmentService=assignmentServices;
    }

    @GetMapping("/")
    public ResponseEntity<List<AssignmentDto>> getAllAssignment(@PathVariable("courseID") String courseID,@CurrentUser User currentUser) throws Exception{
        List<Assignment> assignments=new ArrayList<>();
        List<AssignmentDto>assignmentDtos= new ArrayList<>();
            assignments=assignmentService.getAssignmentlList(Integer.parseInt(courseID));
            assignmentDtos=assignmentService.convetToDtoList(assignments);
            return ResponseEntity.ok(assignmentDtos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDto> getMethodName(@PathVariable("courseID") String courseID,@PathVariable("id") String id) throws Exception {
            Assignment assignment= assignmentService.getAssignment(Integer.parseInt(courseID),Integer.parseInt(id));
            AssignmentDto assignmentDto=assignmentService.convetToDto(assignment);
            return ResponseEntity.ok(assignmentDto);
    }

    @HasRole({"INSTRUCTOR"})
    @PostMapping("/") 
    public ResponseEntity<Assignment> addAssignment(@PathVariable("courseID") String courseID,@RequestBody AssignmentDto assignment ,@CurrentUser User currentUser ) throws Exception{
            assignmentService.addAssignment(assignment, Integer.parseInt(courseID));
            return ResponseEntity.ok().build();
    }

    @HasRole({"INSTRUCTOR"})
    @PutMapping("/{id}")
    public ResponseEntity<AssignmentDto> updateAssignment(@PathVariable("id") String id, @RequestBody AssignmentDto assignment,@CurrentUser User currentUser,@PathVariable("courseID")String CourseID) throws Exception {
            AssignmentDto updatedassignment=assignmentService.updateAssignment(assignment,Integer.parseInt(id), Integer.parseInt(CourseID));
            return ResponseEntity.ok(updatedassignment);
        
    }

    @HasRole({"INSTRUCTOR"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Assignment> deleteAssignment(@PathVariable("id") String id,@CurrentUser User currentUser) throws Exception{
            assignmentService.deleteAssignment(Integer.parseInt(id));
            return ResponseEntity.ok().build();
        
    }

    @GetMapping("{id}/attachments")
    public ResponseEntity<List<MediaFile>> getAttachment(@PathVariable ("id") String id) throws Exception {
        return ResponseEntity.ok(assignmentService.getAttachment(Integer.parseInt(id)));
    }
    
    @PostMapping("{id}/attachments")
    public ResponseEntity<MediaFile> addAttachment(@PathVariable ("id")String id,@RequestParam ("file")MultipartFile file) throws Exception, Exception {
        return ResponseEntity.ok(assignmentService.addAttachment(Integer.parseInt(id), file));
    }
    
}
