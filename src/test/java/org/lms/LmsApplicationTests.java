package org.lms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.Notificiation.Repos.NotificationRepository;
import org.lms.Notificiation.Services.EmailService;
import org.lms.Notificiation.Services.NotificationService;
import org.lms.Quiz.Repos.QuestionRepo.QuestionRepository;
import org.lms.Quiz.Repos.QuizRepo.QuizAttemptRepository;
import org.lms.Quiz.Repos.QuizRepo.QuizRepository;
import org.lms.Quiz.Services.QuestionService;
import org.lms.Quiz.Services.QuizService;
import org.lms.course.Course;
import org.lms.course.CourseController;
import org.lms.course.CourseService;
import org.lms.lesson.repositories.LessonRepository;
import org.lms.lesson.services.LessonService;
import org.lms.mediafiles.repositories.MediaFilesRepository;
import org.lms.mediafiles.services.MediaFilesService;
import org.lms.shared.exceptions.HttpNotFoundException;
import org.lms.user.UserRepository;
import org.lms.user.UserService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LmsApplicationTests {

    private MockMvc mockMvcCourses;
    private MockMvc mockMvcUsers;
    private MockMvc mockMvcQuiz;
    private MockMvc mockMvcQuestions;
    private MockMvc mockMvcNotifcations;
    private MockMvc mockMvcMediaFiles;
    private MockMvc mockMvcLessons;

    @Mock
    private CourseService courseService;
    private UserRepository userRepository;
    private QuizRepository quizRepository;
    private QuizAttemptRepository quizAttemptRepository;
    private QuestionService questionService;
    private QuestionRepository questionRepository;
    private NotificationRepository notififcationRepository;
    private EmailService emailService;
    private MediaFilesRepository mediaFilesRepository;
    private LessonRepository lessoonRepository;



    @BeforeEach
    public void setup() {
        this.mockMvcCourses = MockMvcBuilders.standaloneSetup(new CourseController(courseService)).build();
        this.mockMvcUsers = MockMvcBuilders.standaloneSetup(new UserService(userRepository)).build();
        this.mockMvcQuiz = MockMvcBuilders.standaloneSetup(new QuizService(quizRepository,quizAttemptRepository)).build();
        this.mockMvcQuestions = MockMvcBuilders.standaloneSetup(new QuestionService(questionRepository)).build();
        this.mockMvcNotifcations = MockMvcBuilders.standaloneSetup(new NotificationService(notififcationRepository, emailService)).build();
        this.mockMvcMediaFiles = MockMvcBuilders.standaloneSetup(new MediaFilesService(mediaFilesRepository)).build();
        this.mockMvcLessons = MockMvcBuilders.standaloneSetup(new LessonService(lessoonRepository)).build();
    }

    @Test
    public void getCourseById_success() throws Exception {
        Course course = new Course(1, "Course 1", "Description 1", 10, null, null, null, null, null, null);

        Mockito.when(courseService.getCourseById(1)).thenReturn(course);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcCourses.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(course)))
                .andReturn();
    }

    @Test
    public void deleteCourse_success() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcCourses.perform(mockRequest)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getAllCourses_success() throws Exception {
        List<Course> courses = List.of(
                new Course(1, "Course 1", "Description 1", 10, null, null, null, null, null, null),
                new Course(2, "Course 2", "Description 2", 20, null, null, null, null, null, null)
        );

        Mockito.when(courseService.getAllCourses()).thenReturn(courses);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/courses/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcCourses.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(courses)))
                .andReturn();
    }

    @Test
    public void getCourseById_notFound() throws Exception {
        Mockito.when(courseService.getCourseById(1)).thenThrow(new HttpNotFoundException("Course not found"));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcCourses.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deleteCourse_notFound() throws Exception {
        Mockito.doThrow(new HttpNotFoundException("Course not found")).when(courseService).deleteCourse(1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcCourses.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }
    @Test
    public void getAllCourses_notFound() throws Exception {
        Mockito.when(courseService.getAllCourses()).thenThrow(new HttpNotFoundException("Courses not found"));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/courses/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcCourses.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    // Test the UserController
    @Test
    public void deleteUser_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcUsers.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void updateUser_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.patch("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcUsers.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void findUserByEmail_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/users/email")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcUsers.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getEmailById_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/users/email")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcUsers.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }


    // Test the QuizController
    @Test
    public void getQuizById_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/quiz/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuiz.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deleteQuiz_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/quiz/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuiz.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getAllQuiz_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/quiz/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuiz.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuizAttemptById_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/quiz/attempt/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuiz.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deleteQuizAttempt_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/quiz/attempt/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuiz.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getAllQuizAttempt_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/quiz/attempt/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuiz.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuizAttemptByQuizId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/quiz/attempt/quiz/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuiz.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuizAttemptByUserId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/quiz/attempt/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuiz.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuizAttemptByQuizIdAndUserId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/quiz/attempt/quiz/1/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuiz.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }


    // Test the QuestionController
    @Test
    public void getQuestionById_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deleteQuestion_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/questions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getAllQuestions_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuestionByQuizId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/quiz/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuestionByQuizIdAndQuestionId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/quiz/1/question/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuestionByQuizIdAndDifficulty_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/quiz/1/difficulty/EASY")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuestionByQuizIdAndDifficultyAndQuestionId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/quiz/1/difficulty/EASY/question/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuestionByQuizIdAndDifficultyAndQuestionIdAndUserId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/quiz/1/difficulty/EASY/question/1/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuestionByQuizIdAndDifficultyAndUserId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/quiz/1/difficulty/EASY/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuestionByQuizIdAndUserId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/quiz/1/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuestionByUserId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuestionByUserIdAndQuestionId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/user/1/question/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuestionByUserIdAndQuizId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/user/1/quiz/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuestionByUserIdAndQuizIdAndQuestionId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/user/1/quiz/1/question/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuestionByUserIdAndQuizIdAndDifficulty_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/user/1/quiz/1/difficulty/EASY")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuestionByUserIdAndQuizIdAndDifficultyAndQuestionId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/user/1/quiz/1/difficulty/EASY/question/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuestionByUserIdAndQuizIdAndDifficultyAndQuestionIdAndUserId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/user/1/quiz/1/difficulty/EASY/question/1/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getQuestionByUserIdAndQuizIdAndDifficultyAndUserId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/questions/user/1/quiz/1/difficulty/EASY/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcQuestions.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    // Test the NotificationController
    @Test
    public void getNotificationById_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/notifications/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcNotifcations.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deleteNotification_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/notifications/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcNotifcations.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getAllNotifications_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/notifications/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcNotifcations.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getNotificationByUserId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/notifications/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcNotifcations.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getNotificationByUserIdAndNotificationId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/notifications/user/1/notification/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcNotifcations.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    // Test the MediaFilesController
    @Test
    public void getMediaFileById_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/mediafiles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcMediaFiles.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deleteMediaFile_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/mediafiles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcMediaFiles.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getAllMediaFiles_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/mediafiles/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcMediaFiles.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getMediaFileByUserId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/mediafiles/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcMediaFiles.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    // Test the LessonController
    @Test
    public void getLessonById_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/lessons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcLessons.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deleteLesson_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/lessons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcLessons.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getAllLessons_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/lessons/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcLessons.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getLessonByUserId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/lessons/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcLessons.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getLessonByUserIdAndLessonId_notFound() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/lessons/user/1/lesson/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvcLessons.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();
    }
}