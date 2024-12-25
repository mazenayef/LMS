package org.lms.enrollment;

import org.lms.authentication.annotations.CurrentUser;
import org.lms.authentication.annotations.HasRole;
import org.lms.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses/{courseId}/enrollment")
public class EnrollmentController {
    private EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/test")
    public String test() {
        return "Enrollment Controller works!";
    }

    @PatchMapping("/join")
    @HasRole({"STUDENT", "INSTRUCTOR"})
    public ResponseEntity<StringDTO> joinCourse(@PathVariable("courseId") String courseId, @CurrentUser User currentUser) throws Exception{
        Integer userId = currentUser.getId();
        Integer courseIdInt = Integer.parseInt(courseId);
        if (currentUser.getRole() == User.Role.INSTRUCTOR) {
            StringDTO result = new StringDTO(enrollmentService.joinInstructorCourse(courseIdInt, userId));
            return ResponseEntity.ok().body(result);
        }
        else if (currentUser.getRole() == User.Role.STUDENT) {
            StringDTO result = new StringDTO(enrollmentService.joinStudentCourse(courseIdInt, userId));
            return ResponseEntity.ok().body(result);
        }
        return null;
    }

    @PatchMapping("/leave")
    @HasRole({"STUDENT", "INSTRUCTOR"})
    public ResponseEntity leaveCourse(@PathVariable("courseId") String courseId, @CurrentUser User currentUser) throws Exception {
        Integer userId = currentUser.getId();
        Integer courseIdInt = Integer.parseInt(courseId);
        if (currentUser.getRole() == User.Role.INSTRUCTOR) {
            StringDTO result = new StringDTO(enrollmentService.leaveInstructorCourse(courseIdInt, userId));
            return ResponseEntity.ok().body(result);
        } else if (currentUser.getRole() == User.Role.STUDENT) {
            StringDTO reult = new StringDTO(enrollmentService.leaveStudentCourse(courseIdInt, userId));
            return ResponseEntity.ok().body(reult);
        }
        return null;
    }
}
