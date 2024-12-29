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
import org.lms.Models.ResponseObject;
import org.lms.Quiz.DTOs.Pair;
import org.lms.Quiz.DTOs.QuizDTOs.QuizAttemptSet;
import org.lms.Quiz.DTOs.QuizDTOs.QuizPerformance;
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
    private final QuizAttemptRepository quizAttemptRepository;

    public QuizService(QuizRepository repo, QuizAttemptRepository attemptsRepo, QuizAttemptRepository quizAttemptRepository){
        _repo = repo;
        _attemptsRepo = attemptsRepo;
        this.quizAttemptRepository = quizAttemptRepository;
    }

    public ResponseObject create(QuizSet quizSet){
        if(quizSet.startTime.isAfter(quizSet.startTime))
            return new ResponseObject("wrong time interval", null);
        
        if(quizSet.difficulties.length < 2)
            return new ResponseObject("quiz length is invalid", null);

        
        int id = 0;
        for (Quiz q : QuizRepository.quizzes) 
            id = Math.max(id, q.getId());

        Quiz object = quizSet.toObject();
        object.setId(id + 1);
        return new ResponseObject("normalized", _repo.create(object));
    }

    public Future<ResponseObject> getAll(){
        CompletableFuture<ResponseObject> future = CompletableFuture.supplyAsync(() -> {
            return new ResponseObject("normalized",_repo.getAll());
        });
        return future;
    }

    public Future<ResponseObject> filter(Map<String, Object> criteria){
        CompletableFuture<ResponseObject> future = CompletableFuture.supplyAsync(() -> {
            return new ResponseObject("normalized",_repo.filter(criteria));
        });
        return future;
    }

    public Future<ResponseObject> update(int id, QuizSet quizSet){
        CompletableFuture<ResponseObject> future = CompletableFuture.supplyAsync(() -> {

            Quiz quiz = QuizRepository.quizzes.stream()
            .filter(obj -> obj.getId() == id)
            .findFirst()
            .orElse(null);

            if(quiz == null)
                return new ResponseObject("could not find quiz",null);

            if(quizSet.startTime.isAfter(quizSet.startTime))
                return new ResponseObject("wrong time interval", null);
          
            if(quizSet.difficulties.length < 2)
                return new ResponseObject("quiz length is invalid", null);

            return new ResponseObject("normalized",_repo.update(quiz, quizSet));
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

    public Future<ResponseObject> getModelOfQuiz(int userId,int quizId) {
        CompletableFuture<ResponseObject> future = CompletableFuture.supplyAsync(() -> {

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
                return new ResponseObject("could not find quiz", null);
            
            if(quiz.getStartTime().isAfter(LocalDateTime.now()))
                return new ResponseObject("quiz did not start yet",null);
            
            if(quiz.getStartTime().plusMinutes(quiz.getDuration()).isBefore(LocalDateTime.now()))
            return new ResponseObject("quiz is over",null);

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
            System.out.println(countEasyQuestions);
            long countMediumQuestions = proposedQuestions.stream().filter(x -> x == Difficulty.MEDIUM).count();
            System.out.println(countMediumQuestions);
            long countHardQuestions = proposedQuestions.stream().filter(x -> x == Difficulty.HARD).count();
            System.out.println(countHardQuestions);
            List<Pair<Integer,Integer>> questions = new ArrayList<>();

            System.out.println(easyQuestions.size());
            System.out.println(mediumQuestions.size());
            System.out.println(hardQuestions.size());

            if(easyQuestions.size() < countEasyQuestions || mediumQuestions.size() < countMediumQuestions || hardQuestions.size() < countHardQuestions )
                return new ResponseObject("not enough questions to create quiz",null);
            
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

            return new ResponseObject("quiz created", attempt);
        });
        return future;
    }

    public Future<ResponseObject> submitQuizAttempt(QuizAttemptSet quizAttemptSet){

        CompletableFuture<ResponseObject> future = CompletableFuture.supplyAsync(() -> {
            QuizAttempt quizAttempt = QuizAttemptRepository.attempts.stream().filter(q -> q.getId() == quizAttemptSet.attemptId).findFirst().orElse(null);
            Quiz quiz = _repo.getAll()
            .stream()
            .filter(q -> q.getId() == quizAttempt.getQuizId())
            .findFirst()
            .orElse(null);

            if(quizAttempt == null)
                return new ResponseObject("could not find the attempt", null);

            if (quizAttemptSet.answers.size() != quizAttempt.getAnswers().size())
                return new ResponseObject("questions count is not equal to the questions count", null);

            for (int i = 0; i < quizAttemptSet.answers.size(); i++) {
                if (quizAttemptSet.answers.get(i).first != quizAttempt.getAnswers().get(i).first)
                    return new ResponseObject("questions are not in the same order", null);
            }

            quizAttempt.setAnswers(quizAttemptSet.answers);
    
            if(QuizAttemptRepository.attempts.stream().filter(x -> x.getQuizId() == quizAttempt.getQuizId() && x.getStudentId() == quizAttempt.getStudentId() && x.getSubmitted()).count() > 0)
                return new ResponseObject("already submited", null);

            if(quiz == null)
                return new ResponseObject("could not find the quiz", null);
    
            if(quizAttempt.getCreatedAt().plusMinutes(quiz.getDuration()).isBefore(LocalDateTime.now())){
                System.out.println(quizAttempt.getCreatedAt().plusMinutes(quiz.getDuration()));
                return new ResponseObject("deadline is due", null);
            }

            if(quizAttempt.getSubmitted())
                return new ResponseObject("already submitted", null);
    
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
                return new ResponseObject("could not find the attempt", null);

            _attemptsRepo.delete(unAnsweredAttempt);
            _attemptsRepo.create(quizAttempt);
    
            return new ResponseObject("normailized", quizAttempt);
        });
        return future;
    }

    public ResponseObject getAllAttempts(){
        return new ResponseObject("normalized", QuizAttemptRepository.attempts);
    }

    public ResponseObject getStudentAttempt(int userId){
        List<QuizAttempt> attempt = QuizAttemptRepository.attempts.stream().filter(x -> x.getStudentId() == userId).toList();
        if(attempt != null)
            return new ResponseObject("normalized", attempt);
        return new ResponseObject("not found", null);
    }

    public ResponseObject getStudentGrade(int id){
        QuizPerformance performance = new QuizPerformance();

        for(QuizAttempt attempt : QuizAttemptRepository.attempts){
            if(attempt.getStudentId() == id){
                performance.countQuestions += attempt.getAnswers().size();
                performance.correctAnswers += attempt.getGrade();
                performance.countQuizzes++;
            }
        }

        if(performance.countQuizzes == 0 || performance.correctAnswers == -1)
            return new ResponseObject("student didnt take any quizzes yet", null);

        performance.persentage = (int)((float)((float)performance.correctAnswers / (float)performance.countQuestions) * 100);
        return new ResponseObject(
            "Student " + id + " took " + performance.countQuizzes + " and answered " + performance.correctAnswers + " from " + performance.countQuestions,
            performance
            );
    }
}
