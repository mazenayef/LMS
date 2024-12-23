package org.lms.Quiz.Controllers;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.lms.Models.ResponseObject;
import org.lms.Quiz.DTOs.QuizDTOs.QuizSet;
import org.lms.Quiz.Models.QuizAttempt;
import org.lms.Quiz.Services.QuizService;
import org.lms.authentication.interceptors.CurrentUser;
import org.lms.authentication.interceptors.HasRole;
import org.lms.user.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService _service;

    public QuizController(QuizService service){
        this._service = service;
    }
    
    @HasRole({"ADMIN", "INSTRUCTOR"})
    @PostMapping(value = "/create", produces = "application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject> create(@RequestBody QuizSet question){
        ResponseObject result = _service.create(question);
        if(result.data != null)
            return ResponseEntity.ok(result);
        return ResponseEntity.badRequest().body(result);
    }

    @HasRole({"ADMIN", "INSTRUCTOR"})
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<ResponseObject> getALL() throws InterruptedException, ExecutionException{
        ResponseObject result = _service.getAll().get(); 
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/filter", produces = "application/json")
    public ResponseEntity<ResponseObject> filter(@RequestBody Map<String,Object> criteria) throws InterruptedException, ExecutionException{
        ResponseObject result = _service.filter(criteria).get(); 
        return ResponseEntity.ok(result);
    }

    @HasRole({"ADMIN", "INSTRUCTOR"})
    @PutMapping(value = "/update/{id}", produces = "application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject> update(@PathVariable int id, @RequestBody QuizSet questionSet) throws InterruptedException, ExecutionException{
        ResponseObject result = _service.update(id,questionSet).get();
        if(result.data != null)
            return ResponseEntity.ok(result);
        return ResponseEntity.badRequest().body(result);
    }

    @HasRole({"ADMIN", "INSTRUCTOR"})
    @DeleteMapping(value = "/delete/{id}")
    public void delet(@PathVariable int id){
        _service.delete(id);
    }

    @GetMapping(value = "/attempt/{userId}/{quizId}")
    public ResponseEntity<ResponseObject> attempt(@CurrentUser User user,@PathVariable int quizId) throws InterruptedException, ExecutionException, Exception{
        ResponseObject result = _service.getModelOfQuiz(user.getId(), quizId).get();
        if(result.data != null)
            return ResponseEntity.ok(result);
        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping(value = "/submit", produces = "application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject> submit(@RequestBody QuizAttempt attempt) throws InterruptedException, ExecutionException{
        ResponseObject result = _service.submitQuizAttempt(attempt).get();
        if(result.data != null)
            return ResponseEntity.ok(result);
        return ResponseEntity.badRequest().body(result);
    }

    @HasRole({"ADMIN", "INSTRUCTOR"})
    @GetMapping(value = "/student/{id}/grades", produces = "application/json")
    public ResponseEntity<ResponseObject> getMarks(@PathVariable int id){
        ResponseObject result = _service.getStudentGrade(id);
        if(result.data != null)
            return ResponseEntity.ok(result);
        return ResponseEntity.badRequest().body(result);
    }
}