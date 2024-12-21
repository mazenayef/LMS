package org.lms.Quiz.Controllers;

import java.util.Map;

import org.lms.Models.ResponseAPI;
import org.lms.Quiz.DTOs.QuizDTOs.QuizSet;
import org.lms.Quiz.Models.QuizAttempt;
import org.lms.Quiz.Services.QuizService;
import org.springframework.http.MediaType;
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
    
    @PostMapping(value = "/create", produces = "application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseAPI create(@RequestBody QuizSet question){
        return _service.create(question);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseAPI getALL(){
        try {
            ResponseAPI result = _service.getAll().get(); 
            return result;
        } catch (Exception e) {
            return new ResponseAPI(500,e.getMessage(), null);
        }
    }

    @GetMapping(value = "/filter", produces = "application/json")
    public ResponseAPI filter(@RequestBody Map<String,Object> criteria){
        try {
            ResponseAPI result = _service.filter(criteria).get(); 
            return result;
        } catch (Exception e) {
            return new ResponseAPI(500,e.getMessage(), null);
        }
    }

    @PutMapping(value = "/update/{id}", produces = "application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseAPI update(@PathVariable int id, @RequestBody QuizSet questionSet){
        try {
            ResponseAPI result = _service.update(id, questionSet).get(); 
            return result;
        } catch (Exception e) {
            return new ResponseAPI(500,e.getMessage(), null);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delet(@PathVariable int id){
        _service.delete(id);
    }

    // start of the student releated methods
    // TODO will swap the id of the user to current user and get his id from request after auth is done

    @GetMapping(value = "/attempt/{userId}/{quizId}")
    public ResponseAPI attempt(@PathVariable int userId,@PathVariable int quizId){
        try {
            ResponseAPI result = _service.getModelOfQuiz(userId, quizId).get();
            return result;
        } catch (Exception e) {
            return new ResponseAPI(500,e.getMessage(), null);
        }
    }

    @PutMapping(value = "/submit", produces = "application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseAPI submit(@RequestBody QuizAttempt attempt){
        try {
            ResponseAPI result = _service.submitQuizAttempt(attempt).get();
            return result;
        } catch (Exception e) {
            return new ResponseAPI(500,e.getMessage(), null);
        }
    }

    @GetMapping(value = "/student/{id}/grades", produces = "application/json")
    public ResponseAPI getMarks(@PathVariable int id){
        return _service.getStudentGrade(id);
    }
}
