package org.lms.course;

import org.lms.shared.exceptions.HttpBadRequestException;
import org.lms.shared.exceptions.HttpNotFoundException;
import org.springframework.stereotype.Repository;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

@Repository
public class CourseRepository {
    public Course getCourseById(Integer id) throws Exception{
        for (Course course : CourseDB.courseList) {
            if (Objects.equals(course.getId(), id)) {
                if (course != null) {
                    return course;
                }
            }
        }
        throw new HttpNotFoundException("Course not found");
    }

    public List<Course> getAllCourses() {
        return CourseDB.courseList;
    }

    public Course addCourse(CourseDTO course) throws Exception{
        if (course.getTitle() == null || course.getDescription() == null || course.getDuration() == null) {
            throw new HttpBadRequestException("Invalid course data");
        }
        else {
            Course newCourse = new Course(CourseDB.courseList.size() + 1, course.getTitle(), course.getDescription(), course.getDuration(), course.getStudentList(), course.getAssignmentList(), course.getAnnouncementList(), course.getQuizList(), course.getLessonList(), course.getInstructorList());
            CourseDB.courseList.add(newCourse);
            return newCourse;
        }
    }

    public Course updateCourse(Integer id, CourseDTO course) throws Exception{
        for (Course c : CourseDB.courseList) {
            if (Objects.equals(c.getId(), id)) {
                if (course.getTitle() != null) {
                    c.setTitle(course.getTitle());
                }
                if (course.getDescription() != null) {
                    c.setDescription(course.getDescription());
                }
                if (course.getDuration() != null) {
                    c.setDuration(course.getDuration());
                }
                if (course.getStudentList() != null) {
                    c.setStudentList(course.getStudentList());
                }
                if (course.getAssignmentList() != null) {
                    c.setAssignmentList(course.getAssignmentList());
                }
                if (course.getAnnouncementList() != null) {
                    c.setAnnouncementList(course.getAnnouncementList());
                }
                if (course.getQuizList() != null) {
                    c.setQuizList(course.getQuizList());
                }
                if (course.getLessonList() != null) {
                    c.setLessonList(course.getLessonList());
                }
                if (course.getInstructorList() != null) {
                    c.setInstructorList(course.getInstructorList());
                }
                return c;
            }
        }
        throw new HttpNotFoundException("Course not found");
    }

    public void deleteCourse(Integer id) {
        for (Course course : CourseDB.courseList) {
            if (Objects.equals(course.getId(), id)) {
                CourseDB.courseList.remove(course);
                break;
            }
        }
    }

    public String joinStudentCourse (Integer courseId, Integer studentId) throws Exception {
        Course course = getCourseById(courseId);
        if (course.getStudentList().contains(studentId)) {
            throw new HttpBadRequestException("Student already enrolled in course");
        }
        course.getStudentList().add(studentId);
        return "Student enrolled in course";
    }

    public String leaveStudentCourse (Integer courseId, Integer studentId) throws Exception {
        Course course = getCourseById(courseId);
        if (!course.getStudentList().contains(studentId)) {
            throw new HttpNotFoundException("Student not enrolled in course");
        }
        course.getStudentList().remove(studentId);
        return "Student removed from course";
    }

    public String joinInstructorCourse (Integer courseId, Integer instructorId) throws Exception {
        Course course = getCourseById(courseId);
        if (course.getInstructorList().contains(instructorId)) {
            throw new HttpBadRequestException("Instructor already enrolled in course");
        }
        course.getInstructorList().add(instructorId);
        return "Instructor enrolled in course";
    }

    public String leaveInstructorCourse (Integer courseId, Integer instructorId) throws Exception {
        Course course = getCourseById(courseId);
        if (!course.getInstructorList().contains(instructorId)) {
            throw new HttpNotFoundException("Instructor not enrolled in course");
        }
        course.getInstructorList().remove(instructorId);
        return "Instructor removed from course";
    }

    public List<Integer> getInstructorList(Integer courseId) throws Exception {
        Course course = getCourseById(courseId);
        return course.getInstructorList();
    }
  
    public List<Integer> getStudents(Integer courseId) throws Exception {
        Course course = getCourseById(courseId);
        return course.getStudentList();
    }
}
