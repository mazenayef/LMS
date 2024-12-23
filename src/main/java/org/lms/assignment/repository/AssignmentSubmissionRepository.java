package org.lms.assignment.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.management.AttributeList;
import org.lms.assignment.models.Assignment;
import org.hibernate.jdbc.Expectation;
import org.lms.assignment.models.AssignmentSubmission;
import org.lms.mediafiles.dtos.MediaFileResourceDto;
import org.lms.user.User;
import org.springframework.stereotype.Repository;
@Repository
public class AssignmentSubmissionRepository {
    static public List<AssignmentSubmission> assignmentSubmissionDB=new ArrayList<>();

    AssignmentSubmissionRepository(){
        // Assignment  assignment2=new Assignment(
        //     "Software",
        //     "blah", 
        //     "25/12",
        //     2
        //     ); 
        // AssignmentSubmission assignmentSubmission1= new AssignmentSubmission(
        //     "12/5",
        //     null,
        //     1,
        //     assignment2
        //     );
        //     AssignmentSubmission assignmentSubmission2= new AssignmentSubmission(
        //     "12/6",
        //     null,
        //     1,
        //     assignment2            
        //     );
        //     assignmentSubmissionDB.add(assignmentSubmission2);
        //     assignmentSubmissionDB.add(assignmentSubmission1);
    }

    public AssignmentSubmission findAssignmentSubmission(Integer id) throws Exception{
        AssignmentSubmission assignmentSubmission=null;
        for (AssignmentSubmission it : assignmentSubmissionDB) {
            if (it.getId()==id) {
                assignmentSubmission=it;
                break;
            }
        }
        if(assignmentSubmission==null){
            throw new Exception("there is not exist");
        }
        return assignmentSubmission;
    }

    public List<AssignmentSubmission> findAssignmentSubmissions(Integer id) throws Exception{
        List<AssignmentSubmission> assignmentSubmission=new ArrayList<>();
        for (AssignmentSubmission it : assignmentSubmissionDB) {
            if (it.getAssignmentID()==id && !it.isCorrected()) {
                assignmentSubmission.add(it);
            }
        }
        if(assignmentSubmission.size()==0){
            throw new Exception("there is not exist");
        }
        return assignmentSubmission;
    }
    // public List<AssignmentSubmission> findStudentAssignmentSubmissions(Integer studentID) throws Exception{
    //     List<AssignmentSubmission> assignmentSubmission=new ArrayList<>();
    //     for (AssignmentSubmission it : assignmentSubmissionDB) {
    //         if (it.getStudentID()==studentID) {
    //             assignmentSubmission.add(it);
    //         }
    //     }
    //     if(assignmentSubmission.size()==0){
    //         throw new Exception("there is not exist");
    //     }
    //     return assignmentSubmission;
    // }
    public void deleteAssignmentSubmission(Integer id) throws Exception{
        for (AssignmentSubmission it : assignmentSubmissionDB) {
            if (it.getId()==id) {
                assignmentSubmissionDB.remove(it);
                return;
            }
        }
        throw new Exception("there is not exist");
    }
    public void create (String createdAT,MediaFileResourceDto media,User student,Integer assignmentID){
        AssignmentSubmission assignmentSubmission= new AssignmentSubmission(createdAT, media, student,assignmentID);
        assignmentSubmissionDB.add(assignmentSubmission);
    }
    public AssignmentSubmission updateAssignmentSubmission(AssignmentSubmission existAssignmentSubmission,AssignmentSubmission updatedAssignmentSubmission){
        existAssignmentSubmission.setCreatedAt(updatedAssignmentSubmission.getCreatedAt());
        existAssignmentSubmission.setMedia(updatedAssignmentSubmission.getMedia());
        existAssignmentSubmission.setCorrected(updatedAssignmentSubmission.isCorrected());
        existAssignmentSubmission.setGrade(updatedAssignmentSubmission.getGrade());
        return existAssignmentSubmission;
    }
    
}
