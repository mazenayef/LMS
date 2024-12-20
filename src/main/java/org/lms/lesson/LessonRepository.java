package org.lms.lesson;

public class LessonRepository {
    public Lesson createLesson(LessonDTO lesson) throws Exception {
        try {
            if (lesson.getName() == null || lesson.getName().isEmpty()) {
                throw new Exception("Lesson name is required");
            }
            if (lesson.getDate() == null) {
                throw new Exception("Lesson date is required");
            }
            if (lesson.getOTP() == null || lesson.getOTP().isEmpty()) {
                throw new Exception("Lesson OTP is required");
            }
            if (lesson.getStudents() == null || lesson.getStudents().isEmpty()) {
                throw new Exception("Lesson students are required");
            }
            if (lesson.getCourseId() == null) {
                throw new Exception("Lesson course ID is required");
            }
            Lesson newLesson = new Lesson(LessonDB.Lessons.size() + 1, lesson.getName(), lesson.getDate(), lesson.getOTP(), lesson.getStudents(), lesson.getCourseId());
            LessonDB.Lessons.add(newLesson);
            return newLesson;
        } catch (Exception e) {
            throw new Exception("Error creating lesson");
        }
    }
}
