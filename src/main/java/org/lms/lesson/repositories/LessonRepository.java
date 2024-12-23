package org.lms.lesson.repositories;

import org.lms.course.CourseRepository;
import org.lms.lesson.DTOs.CreateLessonDTO;
import org.lms.lesson.models.Lesson;
import org.lms.shared.exceptions.HttpBadRequestException;
import org.lms.shared.exceptions.HttpNotFoundException;
import org.lms.user.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Repository
public class LessonRepository {
    private final CourseRepository courseRepository;
    public LessonRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    // lists for testing purposes
    private static List<Lesson> lessons = new ArrayList<>();
    private  static List<Integer> attendedStudents = new ArrayList<>();
    private static List<Integer> allStudnets = new ArrayList<>();
    private static Integer id = 0;
    public Lesson create(CreateLessonDTO lesson , Integer courseId) throws Exception {
        if (lesson.getStartDate().isAfter(lesson.getEndDate())) {
            throw new HttpBadRequestException("Start date should be before end date");
        }
        if (lesson.getStartDate().isBefore(LocalDateTime.now())
                || lesson.getEndDate().isBefore(LocalDateTime.now())) {
            throw new HttpBadRequestException("Start date should be before current date and end date should be after current date");
        }
        Lesson newLesson = new Lesson(id++, lesson.getName(), lesson.getStartDate(), lesson.getEndDate(), courseId);
        lessons.add(newLesson);
        return newLesson;
    }
    public Lesson getLessonById(Integer id)throws Exception {
        for (Lesson lesson : lessons) {
            if (lesson.getId().equals(id)) {
                return lesson;
            }
        }
        throw new HttpNotFoundException("Lesson not found");
    }
    public List<Lesson> getLessons() {
        return lessons;
    }
    public Lesson updateLesson(Integer id, CreateLessonDTO lesson) throws Exception {
        for (Lesson l : lessons) {
            if (l.getId().equals(id)) {
                l.setName(lesson.getName());
                l.setStartDate(lesson.getStartDate());
                l.setEndDate(lesson.getEndDate());
                l.setOTP(lesson.getOTP());
                l.setOTPTime(lesson.getOTPTime());
                return l;
            }
        }
        throw new HttpNotFoundException("Lesson not found");
    }
    public void deleteLesson(Integer id) throws Exception {
        for (Lesson lesson : lessons) {
            if (lesson.getId().equals(id)) {
                lessons.remove(lesson);
                return;
            }
        }
    }
    public void deleteAllLessons() {
        lessons.clear();
    }
    public void attendStudent(Integer lessonId, Integer studentId) throws Exception {
        for (Lesson lesson : lessons) {
            if (lesson.getId().equals(lessonId)) {
                allStudnets = courseRepository.getStudents(lesson.getCourseId());
                for (Integer student : allStudnets) {
                    if (student.equals(studentId)) {
                        lesson.getStudents().add(student);
                        return;
                    }
                }
                throw new HttpNotFoundException("Student not found in course");
            }
        }
        throw new HttpNotFoundException("Lesson not found");
    }

}
//private Integer id;
//private String name;
//private Integer date;
//private String OTP;
//private List<User> students;
//private Integer courseId;