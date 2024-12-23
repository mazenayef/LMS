package org.lms.course;

import org.springframework.stereotype.Repository;

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
        throw new Exception("Course not found");
    }

    public List<Course> getAllCourses() {
        return CourseDB.courseList;
    }

    public Course addCourse(CourseDTO course) throws Exception{
        if (course.getTitle() == null || course.getDescription() == null || course.getDuration() == null) {
            throw new Exception("Invalid course data");
        }
        else {
            Course newCourse = new Course(CourseDB.courseList.size() + 1, course.getTitle(), course.getDescription(), course.getDuration(), course.getStudentList(), course.getAssignmentList(), course.getAnnouncementList(), course.getQuizList(), course.getLessonList(), course.getQuestionList());
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
                if (course.getQuestionList() != null) {
                    c.setQuestionList(course.getQuestionList());
                }
                return c;
            }
        }
        throw new Exception("Course not found");
    }

    public void deleteCourse(Integer id) {
        for (Course course : CourseDB.courseList) {
            if (Objects.equals(course.getId(), id)) {
                CourseDB.courseList.remove(course);
                break;
            }
        }
    }

    public String joinCourse (Integer courseId, Integer studentId) throws Exception {
        Course course = getCourseById(courseId);
        if (course.getStudentList().contains(studentId)) {
            throw new Exception("Student already enrolled in course");
        }
        course.getStudentList().add(studentId);
        return "Student enrolled in course";
    }

    public String leaveCourse (Integer courseId, Integer studentId) throws Exception {
        Course course = getCourseById(courseId);
        if (!course.getStudentList().contains(studentId)) {
            throw new Exception("Student not enrolled in course");
        }
        course.getStudentList().remove(studentId);
        return "Student removed from course";
    }
    public List<Integer> getStudents(Integer courseId) throws Exception {
        Course course = getCourseById(courseId);
        return course.getStudentList();
    }
}
