package org.lms.announcement.dtos;


import org.lms.mediafiles.dtos.MediaFileResourceDto;

import java.time.LocalDateTime;
import java.util.List;

public class AnnouncementDTO {
    private String title;
    private String description;


    public AnnouncementDTO(String title, String description) {
        this.title = title;
        this.description = description;

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

}
