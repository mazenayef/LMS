package org.lms.assignment.services;

import java.util.ArrayList;
import java.util.List;

import org.lms.assignment.dtos.AssignmentDto;
import org.lms.assignment.models.Assignment;
import org.lms.assignment.repository.AssignmentRepository;
import org.lms.mediafiles.models.MediaFile;
import org.lms.mediafiles.services.MediaFilesService;
import org.lms.shared.exceptions.HttpBadRequestException;
import org.lms.shared.exceptions.HttpNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AssignmentServices  {
    private AssignmentRepository assignmentRepository;
    private MediaFilesService mediaFilesService;
    public AssignmentServices(MediaFilesService mediaFilesService,AssignmentRepository assignmentRepository){
        this.assignmentRepository=assignmentRepository;
        this.mediaFilesService=mediaFilesService;
    }
    public void addAssignment(AssignmentDto assignmentDto,Integer courseID ) throws Exception {
        if(assignmentDto.getDueDate()!=null && assignmentDto.getTitle()!=null&&courseID!=null){
            assignmentRepository.create(assignmentDto.getTitle(),assignmentDto.getDescription(), assignmentDto.getDueDate(),courseID);
        }
        else
            throw new HttpBadRequestException("invalid data");
    }

    public Assignment getAssignment(Integer courseID,Integer id) throws Exception {
        Assignment assignment= assignmentRepository.findByID(id);
        if(assignment.getCourseID()==courseID){
            return assignment;
        }
        else 
        throw new HttpNotFoundException("there is not exist");
    }

    public List<Assignment> getAssignmentlList(Integer courseID) throws Exception {
        return this.assignmentRepository.findBycourseID(courseID);
    }

    public AssignmentDto updateAssignment(AssignmentDto assignmentDto,Integer id, Integer courseID) throws Exception {
        Assignment oldAssignment=assignmentRepository.findByID(id);
        if(oldAssignment!=null){
            assignmentRepository.updateExistingAssignment(oldAssignment, assignmentDto);
            assignmentDto.setId(id);
            return assignmentDto;
        }else 
            throw new HttpBadRequestException("invalid data");
    }

    public void deleteAssignment(Integer id) throws Exception {
        this.assignmentRepository.deleteAssignment(id);
    }

    public AssignmentDto convetToDto(Assignment assignment) {
        AssignmentDto assignmentDto= new AssignmentDto(assignment.getId(),assignment.getTitle(),assignment.getDescription(),assignment.getDueDate());
        return assignmentDto;
    }

    public List<AssignmentDto>convetToDtoList(List<Assignment> assignments) {
        List<AssignmentDto> assignmentDtos= new ArrayList<>();
        for (Assignment assignment : assignments) {
            AssignmentDto assignmentdDto= new AssignmentDto(assignment.getId(),assignment.getTitle(),assignment.getDescription(),assignment.getDueDate());
            assignmentDtos.add(assignmentdDto);
        }
        return assignmentDtos;
    }
    public List <MediaFile> getAttachment(Integer assignmentID) throws Exception{
        Assignment assignment =assignmentRepository.findByID(assignmentID);
        List <MediaFile> mediaFiles=new ArrayList<>();
        List<Integer>mediaID= assignment.getMedia();
        for (Integer id : mediaID) {
            mediaFiles.add(this.mediaFilesService.getMedia(id));
        }
        return mediaFiles;
    }
    public MediaFile addAttachment(Integer id ,MultipartFile file) throws Exception{
        Assignment assignment =assignmentRepository.findByID(id);
        MediaFile mediaFile= mediaFilesService.createMedia(file);
        assignmentRepository.addAttachment(id,mediaFile.getId());
        return mediaFile;
    }
}
