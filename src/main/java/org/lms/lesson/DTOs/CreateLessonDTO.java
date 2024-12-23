package org.lms.lesson.DTOs;

import java.time.LocalDateTime;

public class CreateLessonDTO {
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String OTP;

    private LocalDateTime OTPTime;


    public CreateLessonDTO() {
    }

    public CreateLessonDTO(String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.OTP = null;
        this.OTPTime = null;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
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

    public LocalDateTime getOTPTime() {
        return OTPTime;
    }

    public void setOTPTime(LocalDateTime OTPTime) {
        this.OTPTime = OTPTime;
    }
}
