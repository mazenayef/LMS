package org.lms.announcement.models;


import java.time.LocalDateTime;
import java.util.List;


public class Announcement {
    private Integer id;
    private String title;
    private String description;
    private String createdAt;
    private Integer courseId;
    private List<Integer> MediaFileList;

    public Announcement(Integer id, String title, String description, Integer courseId, List<Integer> mediaFileList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = LocalDateTime.now().toString();
        this.courseId = courseId;
        MediaFileList = mediaFileList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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


    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public List<Integer> getMediaFileList() {
        return MediaFileList;
    }

    public void setMediaFileList(List<Integer> mediaFileList) {
        MediaFileList = mediaFileList;
    }

    public void addMediaFile(Integer mediaFileId) {
        this.MediaFileList.add(mediaFileId);
    }
}
