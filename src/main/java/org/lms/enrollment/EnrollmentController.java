package org.lms.enrollment;

import org.lms.authentication.interceptors.CurrentUser;
import org.lms.authentication.interceptors.HasRole;
import org.lms.course.CourseRepository;
import org.lms.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course/{courseId}/enrollment")
public class EnrollmentController {
    private EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/test")
    public String test() {
        return "Enrollment Controller works!";
    }

    @PostMapping("/join")
    @HasRole({"STUDENT", "INSTRUCTOR"})
    public ResponseEntity<String> joinCourse(@PathVariable("courseId") Integer courseId, @CurrentUser User currentUser) throws Exception{
        Integer userId = currentUser.getId();

        try {
            if (currentUser.getRole().equals("INSTRUCTOR")) {
                return ResponseEntity.ok().body(enrollmentService.joinInstructorCourse(courseId, userId));
            }
            else {
                return ResponseEntity.ok().body(enrollmentService.joinStudentCourse(courseId, userId));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/leave")
    @HasRole({"STUDENT", "INSTRUCTOR"})
    public ResponseEntity<String> leaveCourse(@PathVariable("courseId") Integer courseId, @CurrentUser User currentUser) throws Exception{
        Integer userId = currentUser.getId();
        try {
            if (currentUser.getRole().equals("INSTRUCTOR")) {
                return ResponseEntity.ok().body(enrollmentService.leaveInstructorCourse(courseId, userId));
            }
            else {
                return ResponseEntity.ok().body(enrollmentService.leaveStudentCourse(courseId, userId));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
