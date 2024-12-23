package org.lms.assignment.services;

import java.util.ArrayList;
import java.util.List;

import org.lms.assignment.dtos.AssignmentDto;
import org.lms.assignment.models.Assignment;
import org.lms.assignment.repository.AssignmentRepository;
import org.springframework.stereotype.Service;

@Service
public class AssignmentServices  {
    private AssignmentRepository assignmentRepository;
    public AssignmentServices(AssignmentRepository assignmentRepository){
        this.assignmentRepository=assignmentRepository;
    }
    public void addAssignment(AssignmentDto assignmentDto,Integer courseID ) throws Exception {
        if(assignmentDto.getDueDate()!=null && assignmentDto.getTitle()!=null&&courseID!=null){
            assignmentRepository.create(assignmentDto.getTitle(),assignmentDto.getDescription(), assignmentDto.getDueDate(),courseID, assignmentDto.getMedia());
        }
        else
            throw new Exception("invalid data");
    }

    public Assignment getAssignment(Integer courseID,Integer id) throws Exception {
        Assignment assignment= assignmentRepository.findByID(id);
        if(assignment.getCourseID()==courseID){
            return assignment;
        }
        else 
        throw new Exception("there is not exist");
    }

    public List<Assignment> getAssignmentlList(Integer courseID) throws Exception {
        return this.assignmentRepository.findBycourseID(courseID);
    }

    public AssignmentDto updateAssignment(AssignmentDto assignmentDto,Integer id, Integer courseID) throws Exception {
        Assignment oldAssignment=assignmentRepository.findByID(id);
        if(oldAssignment!=null){
            Assignment newAssignment= new Assignment(assignmentDto.getTitle(),assignmentDto.getDescription(), assignmentDto.getDueDate(),assignmentDto.getMedia(), courseID);
            newAssignment= assignmentRepository.updateExistingAssignment(oldAssignment, newAssignment);
            return convetToDto(newAssignment);
        }else 
            throw new Exception("invalid data");
    }

    public void deleteAssignment(Integer id) throws Exception {
        this.assignmentRepository.deleteAssignment(id);
    }

    public AssignmentDto convetToDto(Assignment assignment) {
        AssignmentDto assignmentDto= new AssignmentDto(assignment.getTitle(),assignment.getDescription(),assignment.getDueDate(),assignment.getMedia());
        return assignmentDto;
    }

    public List<AssignmentDto>convetToDtoList(List<Assignment> assignments) {
        List<AssignmentDto> assignmentDtos= new ArrayList<>();
        for (Assignment assignment : assignments) {
            AssignmentDto assignmentdDto= new AssignmentDto(assignment.getTitle(),assignment.getDescription(),assignment.getDueDate(),assignment.getMedia());
            assignmentDtos.add(assignmentdDto);
        }
        return assignmentDtos;
    }
}
