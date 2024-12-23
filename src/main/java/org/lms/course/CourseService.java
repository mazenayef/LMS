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

    public String joinCourse(Integer courseId, Integer studentId) throws Exception {
        return courseRepository.joinCourse(courseId, studentId);
    }

    public String leaveCourse(Integer courseId, Integer studentId) throws Exception {
        return courseRepository.leaveCourse(courseId, studentId);
    }

    public List<Integer> getStudents(Integer courseId) throws Exception {
        return courseRepository.getStudents(courseId);
    }
}
