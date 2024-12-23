package org.lms.course;

import java.util.List;

public class CourseDTO {
    private String title;
    private String description;
    private Integer duration;
    private List<Integer> studentList;
    private List<Integer> assignmentList;
    private List<Integer> announcementList;
    private List<Integer> quizList;
    private List<Integer> lessonList;

    private List<Integer> instructorList;

    public CourseDTO() {
    }

    public CourseDTO(String title, String description, Integer duration, List<Integer> studentList, List<Integer> assignmentList, List<Integer> announcementList, List<Integer> quizList, List<Integer> lessonList, List<Integer> instructorList) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.studentList = studentList;
        this.assignmentList = assignmentList;
        this.announcementList = announcementList;
        this.quizList = quizList;
        this.lessonList = lessonList;
        this.instructorList = instructorList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Integer> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Integer> studentList) {
        this.studentList = studentList;
    }

    public List<Integer> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<Integer> assignmentList) {
        this.assignmentList = assignmentList;
    }

    public List<Integer> getAnnouncementList() {
        return announcementList;
    }

    public void setAnnouncementList(List<Integer> announcementList) {
        this.announcementList = announcementList;
    }

    public List<Integer> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<Integer> quizList) {
        this.quizList = quizList;
    }

    public List<Integer> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<Integer> lessonList) {
        this.lessonList = lessonList;
    }

    public List<Integer> getInstructorList() {
        return instructorList;
    }

    public void setInstructorList(List<Integer> instructorList) {
        this.instructorList = instructorList;
    }

    @Override
    public String toString() {
        return "Course{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", studentList=" + studentList +
                ", assignmentList=" + assignmentList +
                ", announcementList=" + announcementList +
                ", quizList=" + quizList +
                ", lessonList=" + lessonList +
                ", instructorList=" + instructorList +
                '}';
    }
}
