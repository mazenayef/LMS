package org.lms.enrollment;

import org.lms.course.CourseRepository;
import org.lms.course.CourseService;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
    private final CourseService courseService;

    public EnrollmentService(CourseService courseService) {
        this.courseService = courseService;
    }

    public String joinCourse (Integer courseId, Integer studentId) throws Exception {
        return courseService.joinCourse(courseId, studentId);
    }

    public String leaveCourse (Integer courseId, Integer studentId) throws Exception {
        return courseService.leaveCourse(courseId, studentId);
    }
}
