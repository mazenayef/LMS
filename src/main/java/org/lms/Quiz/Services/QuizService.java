package org.lms.Quiz.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.lms.Models.ResponseAPI;
import org.lms.Quiz.DTOs.Pair;
import org.lms.Quiz.DTOs.QuizDTOs.QuizSet;
import org.lms.Quiz.Models.Difficulty;
import org.lms.Quiz.Models.Question;
import org.lms.Quiz.Models.Quiz;
import org.lms.Quiz.Models.QuizAttempt;
import org.lms.Quiz.Repos.QuestionRepo.QuestionRepository;
import org.lms.Quiz.Repos.QuizRepo.QuizAttemptRepository;
import org.lms.Quiz.Repos.QuizRepo.QuizRepository;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    private final QuizRepository _repo;
    private final QuizAttemptRepository _attemptsRepo;
    
    public QuizService(QuizRepository repo, QuizAttemptRepository attemptsRepo){
        _repo = repo;
        _attemptsRepo = attemptsRepo;
    }

    public ResponseAPI create(QuizSet quizSet){
        if(quizSet.startTime.isAfter(quizSet.startTime))
            return new ResponseAPI(400, "wrong time interval", null);
        
        if(quizSet.difficulties.length < 2)
            return new ResponseAPI(400, "quiz length is invalid", null);

        
        int id = 0;
        for (Quiz q : QuizRepository.quizzes) 
            id = Math.max(id, q.getId());

        Quiz object = quizSet.toObject();
        object.setId(id);
        return new ResponseAPI(200,"normalized", _repo.create(object));
    }

    public Future<ResponseAPI> getAll(){
        CompletableFuture<ResponseAPI> future = CompletableFuture.supplyAsync(() -> {
            return new ResponseAPI(200,"normalized",_repo.getAll());
        });
        return future;
    }

    public Future<ResponseAPI> filter(Map<String, Object> criteria){
        CompletableFuture<ResponseAPI> future = CompletableFuture.supplyAsync(() -> {
            return new ResponseAPI(200,"normalized",_repo.filter(criteria));
        });
        return future;
    }

    public Future<ResponseAPI> update(int id, QuizSet quizSet){
        CompletableFuture<ResponseAPI> future = CompletableFuture.supplyAsync(() -> {

            Quiz quiz = QuizRepository.quizzes.stream()
            .filter(obj -> obj.getId() == id)
            .findFirst()
            .orElse(null);

            if(quiz == null)
                return new ResponseAPI(404,"could not find quiz",null);

            if(quizSet.startTime.isAfter(quizSet.startTime))
                return new ResponseAPI(400, "wrong time interval", null);
          
            if(quizSet.difficulties.length < 2)
                return new ResponseAPI(400, "quiz length is invalid", null);

            return new ResponseAPI(200,"normalized",_repo.update(quiz, quizSet));
        });
        return future;
    }

    public void delete(int id){
        Quiz quiz = QuizRepository.quizzes.stream()
            .filter(obj -> obj.getId() == id)
            .findFirst()
            .orElse(null);

        if(quiz != null)
            _repo.delete(quiz);
    }

    public Future<ResponseAPI> getModelOfQuiz(int userId,int quizId){
        CompletableFuture<ResponseAPI> future = CompletableFuture.supplyAsync(() -> {

            ArrayList<Question> easyQuestions = new ArrayList<>();
            ArrayList<Question> mediumQuestions = new ArrayList<>();
            ArrayList<Question> hardQuestions = new ArrayList<>();

            Map<String, Object> criteria = new HashMap<>();
            Quiz quiz = _repo.getAll()
            .stream()
            .filter(q -> q.getId() == quizId)
            .findFirst()
            .orElse(null);

            if(quiz == null)
                return new ResponseAPI(404, "could not find quiz", null);
            
            if(quiz.getStartTime().isAfter(LocalDateTime.now()))
                return new ResponseAPI(400, "quiz did not start yet",null);
            
            if(quiz.getStartTime().plusMinutes(quiz.getDuration()).isBefore(LocalDateTime.now()))
            return new ResponseAPI(400, "quiz is over",null);

            criteria.put("courseId",quiz.getCourseId());

            List<Question> allQuestions = QuestionRepository.questions.stream()
                                                    .filter(q -> q.getCourseId() == quiz.getCourseId())
                                                    .collect(Collectors.toList());
            for (Question q : allQuestions) {
                if(q.getDifficulty() == Difficulty.EASY)
                    easyQuestions.add(q);
                if(q.getDifficulty() == Difficulty.MEDIUM)
                    mediumQuestions.add(q);
                if(q.getDifficulty() == Difficulty.HARD)
                    hardQuestions.add(q);
            }
            ArrayList<Difficulty> proposedQuestions = new ArrayList<>(Arrays.asList(quiz.getDifficulty()));
            long countEasyQuestions = proposedQuestions.stream().filter(x -> x == Difficulty.EASY).count();
            long countMediumQuestions = proposedQuestions.stream().filter(x -> x == Difficulty.MEDIUM).count();
            long countHardQuestions = proposedQuestions.stream().filter(x -> x == Difficulty.HARD).count();
            List<Pair<Integer,Integer>> questions = new ArrayList<>();

            if(easyQuestions.size() < countEasyQuestions || mediumQuestions.size() < countMediumQuestions || hardQuestions.size() < countHardQuestions )
                return new ResponseAPI(400, "not enough questions to create quiz",null);
            
            ArrayList<Question> result = new ArrayList<>();
            Random random = new Random();

            while(countEasyQuestions-- > 0){
                int randInt = random.nextInt((int)easyQuestions.size());
                questions.add(new Pair<Integer,Integer>(easyQuestions.get(randInt).getId(),-1));
                result.add(easyQuestions.get(randInt));
                easyQuestions.remove(randInt);
            }
            while(countMediumQuestions-- > 0){
                int randInt = random.nextInt((int)mediumQuestions.size());
                questions.add(new Pair<Integer,Integer>(mediumQuestions.get(randInt).getId(),-1));
                result.add(mediumQuestions.get(randInt));
                mediumQuestions.remove(randInt);
            }
            while(countHardQuestions-- > 0){
                int randInt = random.nextInt((int)hardQuestions.size());
                questions.add(new Pair<Integer,Integer>(hardQuestions.get(randInt).getId(),-1));
                result.add(hardQuestions.get(randInt));
                hardQuestions.remove(randInt);
            }

            int attemptId = 0;
            for(QuizAttempt a : QuizAttemptRepository.attempts)
                attemptId = Math.max(attemptId,a.getId());
                
            QuizAttempt attempt = new QuizAttempt(attemptId,userId, quiz.getId(),-1, questions, LocalDateTime.now(), false);
            _attemptsRepo.create(attempt);

            return new ResponseAPI(200, "quiz created", attempt);
        });
        return future;
    }

    public Future<ResponseAPI> submitQuizAttempt(QuizAttempt quizAttempt){

        CompletableFuture<ResponseAPI> future = CompletableFuture.supplyAsync(() -> {
            Quiz quiz = _repo.getAll()
            .stream()
            .filter(q -> q.getId() == quizAttempt.getQuizId())
            .findFirst()
            .orElse(null);
    
            if(quiz == null)
                return new ResponseAPI(400, "could not find the quiz", null);
    
            if(quizAttempt.getCreatedAt().plusMinutes(quiz.getDuration()).isBefore(LocalDateTime.now()))
                return new ResponseAPI(403, "deadline is due", null);
    
            if(quizAttempt.getSubmitted())
                return new ResponseAPI(403, "already submitted", null);
    
            ArrayList<Question> questions = QuestionRepository.questions;
    
            quizAttempt.setGrade(0);
            quizAttempt.setSubmitted(true);
    
            for (Pair<Integer,Integer> answer : quizAttempt.getAnswers()) 
                for (Question question : questions) 
                    if(question.getId() == answer.first)
                        if(question.getCorrectChoice() == answer.second)
                            quizAttempt.setGrade(quizAttempt.getGrade() + 1);
            
            QuizAttempt unAnsweredAttempt = null;
    
            for (QuizAttempt attempt : QuizAttemptRepository.attempts) {
                if(attempt.getId() == quizAttempt.getId()){
                    unAnsweredAttempt = attempt;
                    break;
                }
            }
    
            if(unAnsweredAttempt == null)
                return new ResponseAPI(404,"could not find the attempt", null);

            _attemptsRepo.delete(unAnsweredAttempt);
            _attemptsRepo.create(quizAttempt);
    
            return new ResponseAPI(200,"normailized", quizAttempt);
        });
        return future;
    }

    public ResponseAPI getStudentGrade(int id){
        int countQuizzes = 0;
        int countQuestions = 0;
        int correctAnswers = 0;
        
        for(QuizAttempt attempt : QuizAttemptRepository.attempts){
            if(attempt.getStudentId() == id){
                countQuestions += attempt.getAnswers().size();
                correctAnswers += attempt.getGrade();
                countQuizzes++;
            }
        }
        return new ResponseAPI(
            200,
            "Student " + id + " took " + countQuizzes + " and answered " + correctAnswers + " from " + countQuestions,
            (correctAnswers / countQuestions * 100));
    }
}
