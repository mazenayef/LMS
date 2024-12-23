package org.lms.Notificiation.Models;

import java.time.LocalDateTime;

public class NotificationModel {
    private int id;
    private int userId;
    private String header;
    private String descreption;
    private LocalDateTime createdAt;
    private boolean isRead;

    public NotificationModel(int userId, String header, String descreption, LocalDateTime createdAt, boolean isRead) {
        this.userId = userId;
        this.header = header;
        this.descreption = descreption;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }

    public NotificationModel(int id, int userId, String header, String descreption, LocalDateTime createdAt,
            boolean isRead) {
        this.id = id;
        this.userId = userId;
        this.header = header;
        this.descreption = descreption;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public String getDescreption() {
        return descreption;
    }
    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public boolean isRead() {
        return isRead;
    }
    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

}
