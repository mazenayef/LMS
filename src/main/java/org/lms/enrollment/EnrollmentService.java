package org.lms.enrollment;

import org.lms.Notificiation.DTOs.EmailObject;
import org.lms.Notificiation.Services.EmailService;
import org.lms.Notificiation.Services.NotificationService;
import org.lms.course.CourseRepository;
import org.lms.course.CourseService;
import org.lms.shared.exceptions.HttpNotFoundException;
import org.lms.user.User;
import org.lms.user.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentService {
    private final CourseService courseService;

    private final UserService userService;

    private final NotificationService notificationService;

    public EnrollmentService(CourseService courseService, UserService userService, NotificationService notificationService) {
        this.courseService = courseService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    public String joinStudentCourse (Integer courseId, Integer studentId) throws Exception {
        List<Integer> instructorList = courseService.getInstructorList(courseId);
        List<String> instructorEmailList = new ArrayList<>();
        String result = courseService.joinStudentCourse(courseId, studentId);
        for (Integer instructorId : instructorList) {
            String instructorEmail = userService.getEmailById(instructorId);
            instructorEmailList.add(instructorEmail);
        }
        for (String instructorEmail : instructorEmailList) {
            notificationService.notifiy(new EmailObject(
                    instructorEmail,
                    "Student joined course",
                    "A student has joined the course"
            ));
        }
        return result;
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
