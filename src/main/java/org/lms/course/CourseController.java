package org.lms.course;

import jdk.jfr.ContentType;
import org.apache.coyote.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
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
        try {
            return ResponseEntity.ok().body(courseService.getCourseById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Add course
    @PostMapping("/create")
    public ResponseEntity<Course> addCourse(@RequestBody CourseDTO course) throws Exception {
        try {
            return ResponseEntity.ok().body(courseService.addCourse(course));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update course
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") Integer id,@RequestBody CourseDTO course) throws Exception {
        try {
            return ResponseEntity.ok().body(courseService.updateCourse(id, course));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Delete course
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable("id") Integer id) {
        courseService.deleteCourse(id);
    }
}