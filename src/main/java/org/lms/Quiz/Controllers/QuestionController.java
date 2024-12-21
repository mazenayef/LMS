package org.lms.Quiz.Controllers;

import java.util.Map;

import org.lms.Models.ResponseAPI;
import org.lms.Quiz.DTOs.QuestionsDTOs.QuestionSet;
import org.lms.Quiz.Services.QuestionService;
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

    @PostMapping(value = "/create", produces = "application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseAPI> createQuestion(@RequestBody QuestionSet question) throws Exception{
        ResponseAPI result = _service.createQuestion(question);
        if(result.statusCode != 200)
            return ResponseEntity.ok(result);
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseAPI getAll(){
        return _service.getAll();
    }

    @GetMapping(value = "/filter", produces = "application/json")
    public ResponseAPI filter(@RequestBody Map<String, Object> criteria){
        ResponseAPI result;
        try{
            result = _service.filter(criteria).get();
        }
        catch(Exception e){
            return new ResponseAPI(500,"internal server error", null);
        }
        return result;
    }

    @PutMapping(value = "/update/{id}", produces = "application/json")
    public ResponseAPI update(@PathVariable int id, @RequestBody QuestionSet questionSet){
        ResponseAPI result;
        try{
            result = _service.updateQuestion(id, questionSet).get();
        }
        catch(Exception e){
            return new ResponseAPI(500,"internal server error", null);
        }
        return result;
    } 

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable int id){
        _service.delete(id);
    }
}
