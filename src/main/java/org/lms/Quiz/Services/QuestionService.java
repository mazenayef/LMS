package org.lms.Quiz.Services;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.lms.Models.ResponseObject;
import org.lms.Quiz.DTOs.QuestionsDTOs.QuestionSet;
import org.lms.Quiz.Models.Question;
import org.lms.Quiz.Repos.QuestionRepo.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private final QuestionRepository _questionRepo;
    public QuestionService(QuestionRepository questionRepo){
        this._questionRepo = questionRepo;
    }

    public ResponseObject createQuestion(QuestionSet questionSet){
        if(questionSet.choices.length < 2 || questionSet.correctChoice > questionSet.choices.length)
            return new ResponseObject("invalid choices", null);

        Question question =  new Question(questionSet.correctChoice, questionSet.courseId, questionSet.body, questionSet.choices, questionSet.difficulty);
        Integer id = 0;
        for (Question q : QuestionRepository.questions){
                id = Math.max(id, q.getId());
            }
            question.setId(id + 1);

        Question result = _questionRepo.create(question);
        return new ResponseObject("normalized", result);
    }

    public ResponseObject getAll(){
        return new ResponseObject("normalized", _questionRepo.getAll());
    }

    public Future<ResponseObject> filter(Map<String, Object> criteria){
        CompletableFuture<ResponseObject> future = CompletableFuture.supplyAsync(() -> {
            return new ResponseObject("normalized",_questionRepo.filter(criteria));
        });
        return future;
    }

    public Future<ResponseObject> updateQuestion(int id, QuestionSet questionSet){
        CompletableFuture<ResponseObject> future = CompletableFuture.supplyAsync(() -> {
            for (Question q : QuestionRepository.questions) {
                if(q.getId() == id){
                    return new ResponseObject("updated",_questionRepo.update(q, questionSet));
                }
            }
           return new ResponseObject("could not find the question", null);
        });
        return future;
    }

    public void delete(int id){
        Question question = null;
        for(Question q : QuestionRepository.questions)
            if(q.getId() == id)
                question = q;
        if(question != null)
            _questionRepo.delete(question);
    }
}
