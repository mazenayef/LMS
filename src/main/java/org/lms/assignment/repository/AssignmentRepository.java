package org.lms.assignment.repository;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.lms.assignment.models.Assignment;
import org.lms.mediafiles.dtos.MediaFileResourceDto;
import org.lms.mediafiles.models.MediaFile;
import org.springframework.stereotype.Repository;

@Repository
public class AssignmentRepository {
    static public List<Assignment> assignmentDB= new ArrayList<>();
    Assignment assignment1=new Assignment(
        "Software",
        "blah", 
        "25/12",
        1
        );
        Assignment assignment2=new Assignment(
            "Software",
            "blah", 
            "25/12",
            2
            );
    public AssignmentRepository(){
        assignmentDB.add(assignment1);
        assignmentDB.add(assignment2);
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
            throw new Exception("No assignment with this id ");
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
            throw new Exception("this course does not have assignment yet");
        }
        return assignment;
    }
    public void create(String title, String description,String date,Integer courseID,MediaFileResourceDto media ){
        Assignment assignment= new Assignment(title, description, date,media, courseID);
        assignmentDB.add(assignment);
    }
    public void deleteAssignment(Integer id) throws Exception{
        for (Assignment assignment : assignmentDB) {
            if(assignment.getId()==id){
                assignmentDB.remove(assignment);
                return;
            }
        }
        throw new Exception("this course does not have assignment yet");
    }
    public Assignment updateExistingAssignment(Assignment existAssignment,Assignment updatedAssignment){
        existAssignment.setCourseID(updatedAssignment.getCourseID());
        existAssignment.setDescription(updatedAssignment.getDescription());
        existAssignment.setDueDate(updatedAssignment.getDueDate());
        existAssignment.setMedia(updatedAssignment.getMedia());
        existAssignment.setTitle(updatedAssignment.getTitle());
        return existAssignment;
    }
}  