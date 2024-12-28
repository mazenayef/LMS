package org.lms.assignment.repository;

import java.util.ArrayList;
import java.util.List;

import org.lms.assignment.dtos.AssignmentDto;
import org.lms.assignment.models.Assignment;
import org.lms.course.Course;
import org.lms.course.CourseRepository;
import org.lms.mediafiles.dtos.MediaFileResourceDto;

import org.lms.shared.exceptions.HttpBadRequestException;
import org.lms.shared.exceptions.HttpNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class AssignmentRepository {
    static public List<Assignment> assignmentDB= new ArrayList<>();
    private CourseRepository courseRepository;

    public AssignmentRepository(CourseRepository courseRepository){
        this.courseRepository=courseRepository;
    }
    public Assignment findByID(Integer id) throws Exception{
        Assignment assignment = null;
        for (Assignment it : assignmentDB) {
            if(it.getId()==id){
                assignment=it;
                break;
            }
        }
        if(assignment==null){
            throw new HttpNotFoundException("No assignment with this id ");
        }
        return assignment;
    }
    public List<Assignment>findBycourseID(Integer id) throws Exception{
        List<Assignment> assignment = new ArrayList<>();
        for (Assignment it : assignmentDB) {
            if(it.getCourseID()==id){
                assignment.add(it);
            }
        }
        if(assignment.size()==0){
            throw new HttpBadRequestException("this course does not have assignment yet");
        }
        return assignment;
    }
    public void create(String title, String description,String date,Integer courseID ) throws Exception{
        Course course=courseRepository.getCourseById(courseID);
        if(course!=null){
        Assignment assignment= new Assignment(title, description, date, courseID);
        assignment.setMedia(new ArrayList<>());
        assignmentDB.add(assignment);
        }
    }
    public void deleteAssignment(Integer id) throws Exception{
        for (Assignment assignment : assignmentDB) {
            if(assignment.getId()==id){
                assignmentDB.remove(assignment);
                return;
            }
        }
        throw new HttpBadRequestException("this course does not have assignment yet");
    }
    public Assignment updateExistingAssignment(Assignment existAssignment,AssignmentDto updatedAssignment){
        existAssignment.setDescription(updatedAssignment.getDescription());
        existAssignment.setDueDate(updatedAssignment.getDueDate());
        existAssignment.setTitle(updatedAssignment.getTitle());
        return existAssignment;
    }

    public void addAttachment(Integer id ,Integer mediaID){
        for (Assignment assignment : assignmentDB) {
            if(assignment.getId()==id){
                assignment.addMedia(mediaID);
            }
        }
    }
}  