package org.lms.assignment.services;

import java.util.ArrayList;
import java.util.List;

import org.lms.Notificiation.DTOs.EmailObject;
import org.lms.Notificiation.Services.NotificationService;
import org.lms.assignment.dtos.AssignmentDto;
import org.lms.assignment.dtos.AssignmentSubmissionDto;
import org.lms.assignment.models.Assignment;
import org.lms.assignment.models.AssignmentSubmission;
import org.lms.assignment.repository.AssignmentSubmissionRepository;
import org.lms.mediafiles.models.MediaFile;
import org.lms.mediafiles.services.MediaFilesService;
import org.lms.shared.exceptions.HttpBadRequestException;
import org.lms.user.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class AssignmentSubmissionServices  {
    private AssignmentSubmissionRepository assignmentSubmissionRepository;
    private NotificationService notificationService;
    private AssignmentServices assignmentServices;
    private MediaFilesService mediaFilesService;

    public AssignmentSubmissionServices(MediaFilesService mediaFilesService,AssignmentSubmissionRepository assignmentSubmissionRepository,NotificationService notificationService){
        this.assignmentSubmissionRepository=assignmentSubmissionRepository;
        this.notificationService=notificationService;
        this.mediaFilesService=mediaFilesService;
    }
    
    public void createSubmission(AssignmentSubmissionDto assignmentSubmissionDto,User student,Integer assignmentID) throws Exception {
        if(assignmentID!=null&&student!=null){
            assignmentSubmissionRepository.create(assignmentSubmissionDto.getCreatedAt(), student,assignmentID);
        }
        else 
            throw new HttpBadRequestException("invalid data");
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
            throw new HttpBadRequestException("invalid data");
        
    }

    public AssignmentSubmissionDto markAssignmentSubmission(Integer id,AssignmentSubmissionDto updatedAssignmentSubmission,User student,Integer courseId)throws Exception {
        AssignmentSubmission oldAssignmentSubmission=assignmentSubmissionRepository.findAssignmentSubmission(id);
        if (oldAssignmentSubmission!=null) {
            assignmentSubmissionRepository.updateAssignmentSubmission(oldAssignmentSubmission, updatedAssignmentSubmission);
            updatedAssignmentSubmission.setId(id);
            notificationService.notifiy(
                new EmailObject(oldAssignmentSubmission.getStudent().getEmail(),"marks of assignment" ,"you got  "+updatedAssignmentSubmission.getGrade(), oldAssignmentSubmission.getStudent().getId())
            );
            return updatedAssignmentSubmission;
        }
        else
            throw new HttpBadRequestException("invalid data");
        
    }
    public void deleteAssignmentsubmission(Integer id) throws Exception {
        this.assignmentSubmissionRepository.deleteAssignmentSubmission(id);
    }

    
    public AssignmentSubmissionDto convetToDto(AssignmentSubmission assignmentSubmission){
        AssignmentSubmissionDto assignmentSubmissionDto= new AssignmentSubmissionDto(assignmentSubmission.getId(),assignmentSubmission.getCreatedAt(), assignmentSubmission.getGrade(), assignmentSubmission.isCorrected());
        return assignmentSubmissionDto;
    }

    public List<AssignmentSubmissionDto> convetToDtoList(List<AssignmentSubmission> assignmentSubmissions){
        List<AssignmentSubmissionDto>assignmentSubmissionDtos=new ArrayList<>();
        for (AssignmentSubmission assignmentSubmission: assignmentSubmissions) {
            AssignmentSubmissionDto assignmentSubmissionDto= new AssignmentSubmissionDto(assignmentSubmission.getId(),assignmentSubmission.getCreatedAt(), assignmentSubmission.getGrade(), assignmentSubmission.isCorrected());
            assignmentSubmissionDtos.add(assignmentSubmissionDto);
        }
        return assignmentSubmissionDtos;
    }

    public List<MediaFile> getAttachments(Integer submissionId ) throws Exception{
        AssignmentSubmission assignmentSubmission=assignmentSubmissionRepository.findAssignmentSubmission(submissionId);
        List <MediaFile> mediaFiles=new ArrayList<>();
        List<Integer> mediaFileID=assignmentSubmission.getMedia();
        for (int i = 0; i < mediaFileID.size(); i++) {
            mediaFiles.add(this.mediaFilesService.getMedia(mediaFileID.get(i)));
        }
        return mediaFiles;
    }
    public MediaFile addAttachment(Integer submissionID,MultipartFile file) throws Exception{
        AssignmentSubmission assignmentSubmission=assignmentSubmissionRepository.findAssignmentSubmission(submissionID);
        MediaFile media =mediaFilesService.createMedia(file);
        assignmentSubmissionRepository.addAttachment(submissionID, media.getId());
        return media;
    }
}
