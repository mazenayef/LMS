package org.lms.course;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course getCourseById(Integer id) throws Exception {
        return courseRepository.getCourseById(id);
    }

    public List<Course> getAllCourses() {
        return courseRepository.getAllCourses();
    }

    public Course addCourse(CourseDTO course) throws Exception {
        return courseRepository.addCourse(course);
    }

    public Course updateCourse(Integer id, CourseDTO course) throws Exception {
        return courseRepository.updateCourse(id, course);
    }

    public void deleteCourse(Integer id) {
        courseRepository.deleteCourse(id);
    }

    public String joinStudentCourse(Integer courseId, Integer studentId) throws Exception {
        return courseRepository.joinStudentCourse(courseId, studentId);
    }

    public String leaveStudentCourse(Integer courseId, Integer studentId) throws Exception {
        return courseRepository.leaveStudentCourse(courseId, studentId);
    }

    public String joinInstructorCourse(Integer courseId, Integer instructorId) throws Exception {
        return courseRepository.joinInstructorCourse(courseId, instructorId);
    }

    public String leaveInstructorCourse(Integer courseId, Integer instructorId) throws Exception {
        return courseRepository.leaveInstructorCourse(courseId, instructorId);
    }

    public List<Integer> getInstructorList(Integer courseId) throws Exception {
        return courseRepository.getInstructorList(courseId);
    }

    public List<Integer> getStudents(Integer courseId) throws Exception {
        return courseRepository.getStudents(courseId);
    }
}
