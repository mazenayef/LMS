package org.lms.lesson.controllers;

import org.lms.authentication.interceptors.CurrentUser;
import org.lms.authentication.interceptors.HasRole;
import org.lms.lesson.DTOs.CreateLessonDTO;
import org.lms.lesson.DTOs.OTPGet;
import org.lms.lesson.DTOs.OTPValue;
import org.lms.lesson.models.Lesson;
import org.lms.lesson.services.LessonService;
import org.lms.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Controller
@RequestMapping("courses/{courseId}/lesson") // will be changed later to include auth
public class LessonController {
    private final LessonService lessonService;
    LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }
    @GetMapping("/")
    public ResponseEntity<List<Lesson>> getLessons() {
        return ResponseEntity.ok(lessonService.getLessons());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable("id") Integer id) throws Exception {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }
    @HasRole({"INSTRUCTOR"})
    @GetMapping("/{id}/getOTP")
    public ResponseEntity<OTPValue> getOTP(@PathVariable("id") Integer id) throws Exception {

        return ResponseEntity.ok().body(new OTPValue(lessonService.getLessonById(id).getOTP()));
    }

    @HasRole({"INSTRUCTOR"})
    @PostMapping("/")
    public ResponseEntity<Lesson> createLesson(@RequestBody CreateLessonDTO lesson, @PathVariable("courseId") Integer courseId) throws Exception {
            return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.createLesson(lesson , courseId));
    }

    @HasRole({"INSTRUCTOR"})
    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@RequestBody CreateLessonDTO lesson, @PathVariable("id") Integer id) throws Exception {
            return ResponseEntity.ok(lessonService.updateLesson(id, lesson));

    }
    @HasRole({"ADMIN" , "INSTRUCTOR"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable("id") Integer id) throws Exception {
            lessonService.deleteLesson(id);
            return ResponseEntity.ok().build();
    }
    @HasRole({"ADMIN" , "INSTRUCTOR"})
    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAllLessons() {
        lessonService.deleteAllLessons();
        return ResponseEntity.ok().build();
    }
    @HasRole({"STUDENT"})
    @PostMapping("/{id}/attend")
    public ResponseEntity<Void> attendStudent(@RequestBody OTPGet OTP , @PathVariable("id") Integer lessonId , @CurrentUser User student) throws Exception {
            Integer studentId = student.getId();
            lessonService.attendStudent(lessonId , studentId , OTP);
            return ResponseEntity.ok().build();
    }



}
