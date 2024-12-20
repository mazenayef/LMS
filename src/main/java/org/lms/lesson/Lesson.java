package org.lms.lesson;

import org.lms.user.User;

import java.util.List;

public class Lesson {
    private Integer id;
    private String name;
    private Integer date;
    private String OTP;
    private List<User> students;
    private Integer courseId;

    public Lesson() {
    }

    public Lesson(Integer id, String name, Integer date, String OTP, List<User> students, Integer courseId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.OTP = OTP;
        this.students = students;
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

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", OTP='" + OTP + '\'' +
                ", students=" + students +
                ", courseId=" + courseId +
                '}';
    }
}
