package org.lms.assignment.services;

import static org.springframework.http.HttpStatus.resolve;

import java.util.ArrayList;
import java.util.List;

import org.lms.assignment.dtos.AssignmentDto;
import org.lms.assignment.dtos.AssignmentSubmissionDto;
import org.lms.assignment.models.Assignment;
import org.lms.assignment.models.AssignmentSubmission;
import org.lms.assignment.repository.AssignmentSubmissionRepository;
import org.lms.user.User;
import org.springframework.stereotype.Service;


@Service
public class AssignmentSubmissionServices  {
    private AssignmentSubmissionRepository assignmentSubmissionRepository;
    // private NotificationService notificationService;

    public AssignmentSubmissionServices(AssignmentSubmissionRepository assignmentSubmissionRepository){
        this.assignmentSubmissionRepository=assignmentSubmissionRepository;
        
    }
    
    public void createSubmission(AssignmentSubmissionDto assignmentSubmissionDto,User student,Integer assignmentID) throws Exception {
        if(assignmentID!=null&&student!=null){
            assignmentSubmissionRepository.create(assignmentSubmissionDto.getCreatedAt(), assignmentSubmissionDto.getMedia(), student,assignmentID);
        }
        else 
            throw new Exception("invalid data");
    }

    
    public AssignmentSubmission getAssignmentSubmission(Integer id) throws Exception {
        return assignmentSubmissionRepository.findAssignmentSubmission(id);
    }


    public List<AssignmentSubmission> getAssignmentSubmissionList(Integer id) throws Exception {
        return assignmentSubmissionRepository.findAssignmentSubmissions(id);
    }
   
    public AssignmentSubmissionDto updateAssignmentSubmission(Integer id,AssignmentSubmissionDto updatedAssignmentSubmission,User student)throws Exception {
        AssignmentSubmission oldAssignmentSubmission=assignmentSubmissionRepository.findAssignmentSubmission(id);
        if (oldAssignmentSubmission!=null) {
            assignmentSubmissionRepository.updateAssignmentSubmission(oldAssignmentSubmission, updatedAssignmentSubmission);
            updatedAssignmentSubmission.setId(id);
            return updatedAssignmentSubmission;
        }
        else
            throw new Exception("invalid data");
        
    }

    public AssignmentSubmissionDto markAssignmentSubmission(Integer id,AssignmentSubmissionDto updatedAssignmentSubmission,User student)throws Exception {
        AssignmentSubmission oldAssignmentSubmission=assignmentSubmissionRepository.findAssignmentSubmission(id);
        if (oldAssignmentSubmission!=null) {
            assignmentSubmissionRepository.updateAssignmentSubmission(oldAssignmentSubmission, updatedAssignmentSubmission);
            updatedAssignmentSubmission.setId(id);
            return updatedAssignmentSubmission;
        }
        else
            throw new Exception("invalid data");
        
    }
    public void deleteAssignmentsubmission(Integer id) throws Exception {
        this.assignmentSubmissionRepository.deleteAssignmentSubmission(id);
    }

    
    public AssignmentSubmissionDto convetToDto(AssignmentSubmission assignmentSubmission){
        AssignmentSubmissionDto assignmentSubmissionDto= new AssignmentSubmissionDto(assignmentSubmission.getId(),assignmentSubmission.getCreatedAt(), assignmentSubmission.getMedia(), assignmentSubmission.getGrade(), assignmentSubmission.isCorrected());
        return assignmentSubmissionDto;
    }

    public List<AssignmentSubmissionDto> convetToDtoList(List<AssignmentSubmission> assignmentSubmissions){
        List<AssignmentSubmissionDto>assignmentSubmissionDtos=new ArrayList<>();
        for (AssignmentSubmission assignmentSubmission: assignmentSubmissions) {
            AssignmentSubmissionDto assignmentSubmissionDto= new AssignmentSubmissionDto(assignmentSubmission.getId(),assignmentSubmission.getCreatedAt(), assignmentSubmission.getMedia(), assignmentSubmission.getGrade(), assignmentSubmission.isCorrected());
            assignmentSubmissionDtos.add(assignmentSubmissionDto);
        }
        return assignmentSubmissionDtos;
    }
}
