package org.lms.lesson.models;

import org.lms.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private Integer id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String OTP;
    private LocalDateTime OTPTime;
    private List<Integer> students;
    private Integer courseId;

    public Lesson() {
    }

    public Lesson(Integer id, String name, LocalDateTime startDate ,LocalDateTime endDate, Integer courseId) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.OTP = null;
        this.students = new ArrayList<>();
        this.courseId = courseId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public List<Integer> getStudents() {
        return students;
    }

    public void setStudents(List<Integer> students) {
        this.students = students;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getOTPTime() {
        return OTPTime;
    }

    public void setOTPTime(LocalDateTime OTPTime) {
        this.OTPTime = OTPTime;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + startDate.toString()+
                ", OTP='" + OTP + '\'' +
                ", students=" + students +
                ", courseId=" + courseId +
                '}';
    }
}
