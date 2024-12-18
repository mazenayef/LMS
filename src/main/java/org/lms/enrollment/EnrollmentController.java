package org.lms.enrollment;

import org.lms.course.CourseRepository;
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
    public ResponseEntity<String> joinCourse(@PathVariable("courseId") Integer courseId) throws Exception{
        Integer studentId = 55;
        try {
            return ResponseEntity.ok().body(enrollmentService.joinCourse(courseId, studentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/leave")
    public ResponseEntity<String> leaveCourse(@PathVariable("courseId") Integer courseId) throws Exception{
        Integer studentId = 55;
        try {
            return ResponseEntity.ok().body(enrollmentService.leaveCourse(courseId, studentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
