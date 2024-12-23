package org.lms.assignment.repository;

import java.util.ArrayList;
import java.util.List;
import org.lms.assignment.dtos.AssignmentSubmissionDto;
import org.lms.assignment.models.Assignment;
import org.lms.assignment.models.AssignmentSubmission;
import org.lms.mediafiles.dtos.MediaFileResourceDto;
import org.lms.shared.exceptions.HttpNotFoundException;
import org.lms.user.User;
import org.springframework.stereotype.Repository;
@Repository
public class AssignmentSubmissionRepository {
    static public List<AssignmentSubmission> assignmentSubmissionDB=new ArrayList<>();
    public AssignmentRepository assignmentRepository;
    AssignmentSubmissionRepository(AssignmentRepository assignmentRepository){
        this.assignmentRepository=assignmentRepository;
    }

    public AssignmentSubmission findAssignmentSubmission(Integer id) throws Exception{
        AssignmentSubmission assignmentSubmission=null;
        for (AssignmentSubmission it : assignmentSubmissionDB) {
            if (it.getStudent().getId()==id) {
                assignmentSubmission=it;
                break;
            }
        }
        if(assignmentSubmission==null){
            throw new HttpNotFoundException("there is not exist");
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
            throw new HttpNotFoundException("there is not exist");
        }
        return assignmentSubmission;
    }
    
    public void deleteAssignmentSubmission(Integer id) throws Exception{
        for (AssignmentSubmission it : assignmentSubmissionDB) {
            if (it.getId()==id) {
                assignmentSubmissionDB.remove(it);
                return;
            }
        }
        throw new HttpNotFoundException("there is not exist");
    }
    public void create (String createdAT,User student,Integer assignmentID) throws Exception{
        Assignment assignment=assignmentRepository.findByID(assignmentID);
        if(assignment!=null){
        AssignmentSubmission assignmentSubmission= new AssignmentSubmission(createdAT, student,assignmentID);
        assignmentSubmissionDB.add(assignmentSubmission);
        }
    }
    public AssignmentSubmission updateAssignmentSubmission(AssignmentSubmission existAssignmentSubmission,AssignmentSubmissionDto updatedAssignmentSubmission){
        existAssignmentSubmission.setCreatedAt(updatedAssignmentSubmission.getCreatedAt());
        existAssignmentSubmission.setCorrected(updatedAssignmentSubmission.isCorrected());
        existAssignmentSubmission.setGrade(updatedAssignmentSubmission.getGrade());
        return existAssignmentSubmission;
    }
    
    public void addAttachment(Integer submissionID , Integer mediaID){
        for (AssignmentSubmission assignmentSubmission : assignmentSubmissionDB) {
            if(assignmentSubmission.getId()==submissionID){
                assignmentSubmission.addMedia(mediaID);
                return;
            }
        }
        throw new HttpNotFoundException("Not Found");
    }
}
