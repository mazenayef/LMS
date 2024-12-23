package org.lms.course;

import jdk.jfr.ContentType;
import org.apache.coyote.Request;
import org.lms.authentication.interceptors.CurrentUser;
import org.lms.authentication.interceptors.HasRole;
import org.lms.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService CourseService) {
        this.courseService = CourseService;
    }

    // Test
    @GetMapping("/test")
    public String test() {
        return "Course Controller works!";
    }

    // Get all courses
    @GetMapping("/")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok().body(courseService.getAllCourses());
    }

    // Get course by id
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable("id") Integer id) throws Exception {
        return ResponseEntity.ok().body(courseService.getCourseById(id));
    }

    // Add course
    @PostMapping("/")
    @HasRole({"ADMIN"})
    public ResponseEntity<Course> addCourse(@RequestBody CourseDTO course, @CurrentUser User currentUser) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addCourse(course));
    }

    // Update course
    @PatchMapping("/{id}")
    @HasRole({"ADMIN"})
    public ResponseEntity<Course> updateCourse(@PathVariable("id") Integer id,@RequestBody CourseDTO course, @CurrentUser User currentUser) throws Exception {
        return ResponseEntity.ok().body(courseService.updateCourse(id, course));
    }

    // Delete course
    @DeleteMapping("/{id}")
    @HasRole({"ADMIN"})
    public ResponseEntity deleteCourse(@PathVariable("id") Integer id, @CurrentUser User currentUser) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }
}