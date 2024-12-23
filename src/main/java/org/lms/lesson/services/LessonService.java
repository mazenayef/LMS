package org.lms.lesson.services;
import org.lms.lesson.DTOs.CreateLessonDTO;
import org.lms.lesson.DTOs.OTPGet;
import org.lms.lesson.models.Lesson;
import org.lms.lesson.repositories.LessonRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@EnableScheduling
@Component
@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private class OTPService {
        private String OTP;
        private Integer lessonId;
        private LocalDateTime OTPTime;
        public OTPService(Integer lessonId , LocalDateTime OTPTime) {
            this.lessonId = lessonId;
            this.OTPTime = OTPTime;
            this.OTP = generateOTP();
        }

        public String generateOTP() {
            final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            StringBuilder builder = new StringBuilder();
            int count = 6;
            SecureRandom random = new SecureRandom();
            while (count-- != 0) {
                int character = random.nextInt(ALPHA_NUMERIC_STRING.length());
                builder.append(ALPHA_NUMERIC_STRING.charAt(character));
            }
            return builder.toString();
        }
        public String getOTP() {
            return OTP;
        }
        public Integer getLessonId() {
            return lessonId;
        }
        public LocalDateTime getOTPTime() {
            return OTPTime;
        }
        public boolean verifyOTP(String OTP) {
            return this.OTP.equals(OTP) && LocalDateTime.now().isBefore(OTPTime);
        }

    }
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void generateOTPForLesson() {
        List<Lesson> lessons = lessonRepository.getLessons();

        if (lessons != null) {
            for (Lesson lesson : lessons) {
                if (lesson.getStartDate().isBefore(LocalDateTime.now().plusMinutes(1)) && lesson.getOTP() == null) {
                    try {
                        generateOTP(lesson.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public String generateOTP(Integer lessonId) throws Exception {
        Lesson lesson = lessonRepository.getLessonById(lessonId);
        LocalDateTime OTPTime = lesson.getStartDate().plusMinutes(15);
        OTPService otpService = new OTPService(lessonId , OTPTime);
        CreateLessonDTO createLessonDTO = new CreateLessonDTO(lesson.getName(), lesson.getStartDate(), lesson.getEndDate());
        createLessonDTO.setOTP(otpService.getOTP());
        createLessonDTO.setOTPTime(OTPTime);
        lessonRepository.updateLesson(lessonId, createLessonDTO);
        return otpService.getOTP();
    }


    public Lesson createLesson(CreateLessonDTO lesson, Integer courseId) throws Exception {
        return lessonRepository.create(lesson, courseId);
    }
    public List<Lesson> getLessons() {
        return lessonRepository.getLessons();
    }
    public Lesson getLessonById(Integer id) throws Exception {
        return lessonRepository.getLessonById(id);
    }
    public Lesson updateLesson(Integer id, CreateLessonDTO lesson) throws Exception {
        return lessonRepository.updateLesson(id, lesson);
    }
    public void deleteLesson(Integer id) throws Exception {
        lessonRepository.deleteLesson(id);
    }
    public void deleteAllLessons() {
        lessonRepository.deleteAllLessons();
    }
    public void attendStudent(Integer lessonId, Integer studentId , OTPGet submittedOTP) throws Exception {
        Lesson lesson = lessonRepository.getLessonById(lessonId);
        if (lesson.getOTP() == null) {
            throw new Exception("OTP not generated");
        }
        if (!submittedOTP.OTP.equals(lesson.getOTP())) {
            throw new Exception("Invalid OTP");
        }
        if (submittedOTP.OTPTime.isAfter(lesson.getOTPTime())) {
            throw new Exception("OTP expired");
        }
        if (!lesson.getStartDate().isBefore(LocalDateTime.now())) {
            throw new Exception("Lesson not started yet");
        }
        lessonRepository.attendStudent(lessonId, studentId);


    }


}
