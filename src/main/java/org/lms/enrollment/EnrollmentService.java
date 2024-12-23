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

    public String joinStudentCourse (Integer courseId, Integer studentId) throws Exception {
        return courseService.joinStudentCourse(courseId, studentId);
    }

    public String leaveStudentCourse (Integer courseId, Integer studentId) throws Exception {
        return courseService.leaveStudentCourse(courseId, studentId);
    }

    public String joinInstructorCourse (Integer courseId, Integer instructorId) throws Exception {
        return courseService.joinInstructorCourse(courseId, instructorId);
    }

    public String leaveInstructorCourse (Integer courseId, Integer instructorId) throws Exception {
        return courseService.leaveInstructorCourse(courseId, instructorId);
    }
}
