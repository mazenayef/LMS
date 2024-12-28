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
        List<User> instructorsList = new ArrayList<>();
        String result = courseService.joinStudentCourse(courseId, studentId);
        for (Integer instructorId : instructorList) {
            User instructor = userService.findUserById(instructorId);
            instructorsList.add(instructor);
        }
        for (User instructor : instructorsList) {
            notificationService.notifiy(new EmailObject(
                    instructor.getEmail(),
                    "Student joined course",
                    "A student has joined the course",
                    instructor.getId()
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
