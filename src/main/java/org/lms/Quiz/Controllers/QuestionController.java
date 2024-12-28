package org.lms.Quiz.Controllers;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.lms.Models.ResponseObject;
import org.lms.Quiz.DTOs.QuestionsDTOs.QuestionSet;
import org.lms.Quiz.Services.QuestionService;
import org.lms.authentication.interceptors.HasRole;
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
@RequestMapping("/question")
public class QuestionController {
    
    private QuestionService _service;
    QuestionController(QuestionService service){
        this._service = service;
    }

    @HasRole({"ADMIN", "INSTRUCTOR"})
    @PostMapping(value = "/create", produces = "application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject> createQuestion(@RequestBody QuestionSet question) throws Exception{
        ResponseObject result = _service.createQuestion(question);
        if(result.data != null)
            return ResponseEntity.ok(result);
        return ResponseEntity.badRequest().body(result);
    }

    @HasRole({"ADMIN", "INSTRUCTOR"})
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<ResponseObject> getAll(){
        return ResponseEntity.ok(_service.getAll());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ResponseObject> getQuestionBody(@PathVariable Integer id){
        return ResponseEntity.ok(_service.getQuestionBody(id));
    }

    @HasRole({"ADMIN", "INSTRUCTOR"})
    @GetMapping(value = "/filter", produces = "application/json")
    public ResponseEntity<ResponseObject> filter(@RequestBody Map<String, Object> criteria) throws InterruptedException, ExecutionException{
        ResponseObject result;
        result = _service.filter(criteria).get();
        return ResponseEntity.ok(result);
    }

    @HasRole({"ADMIN", "INSTRUCTOR"})
    @PutMapping(value = "/update/{id}", produces = "application/json")
    public ResponseEntity<ResponseObject> update(@PathVariable int id, @RequestBody QuestionSet questionSet) throws InterruptedException, ExecutionException{
        ResponseObject result;
        result = _service.updateQuestion(id, questionSet).get();
        if(result.data != null)
            return ResponseEntity.ok(result);
        return ResponseEntity.badRequest().body(result);
    } 

    @HasRole({"ADMIN", "INSTRUCTOR"})
    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable int id){
        _service.delete(id);
    }
}
