package org.lms.enrollment;

import org.lms.authentication.interceptors.CurrentUser;
import org.lms.authentication.interceptors.HasRole;
import org.lms.course.CourseRepository;
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
    public ResponseEntity<String> joinCourse(@PathVariable("courseId") String courseId, @CurrentUser User currentUser) throws Exception{
        Integer userId = currentUser.getId();
        Integer courseIdInt = Integer.parseInt(courseId);
        try {
            if (currentUser.getRole() == User.Role.INSTRUCTOR) {
                return ResponseEntity.ok().body(enrollmentService.joinInstructorCourse(courseIdInt, userId));
            }
            else if (currentUser.getRole() == User.Role.STUDENT) {
                return ResponseEntity.ok().body(enrollmentService.joinStudentCourse(courseIdInt, userId));
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
        return null;
    }

    @PatchMapping("/leave")
    @HasRole({"STUDENT", "INSTRUCTOR"})
    public ResponseEntity<String> leaveCourse(@PathVariable("courseId") String courseId, @CurrentUser User currentUser) throws Exception{
        Integer userId = currentUser.getId();
        Integer courseIdInt = Integer.parseInt(courseId);
        try {
            if (currentUser.getRole() == User.Role.INSTRUCTOR) {
                return ResponseEntity.ok().body(enrollmentService.leaveInstructorCourse(courseIdInt, userId));
            }
            else if (currentUser.getRole() == User.Role.STUDENT) {
                return ResponseEntity.ok().body(enrollmentService.leaveStudentCourse(courseIdInt, userId));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return null;
    }
}
