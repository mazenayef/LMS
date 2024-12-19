package org.lms.announcement.repositories;

import org.lms.announcement.dtos.AnnouncementDTO;
import org.lms.announcement.models.Annoucement;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnnouncementRepository {
    private static List<Annoucement> announcements = new ArrayList<>();
    private static Integer id = 0;

    public Annoucement create(AnnouncementDTO announcementDTO , Integer courseId) {
        Annoucement announcement = new Annoucement(id++, announcementDTO.getTitle(), announcementDTO.getDescription(),courseId ,null);
        announcements.add(announcement);
        return announcement;
    }

    public List<Annoucement> findAll() {
        return announcements;
    }
    public Annoucement findOne(Integer id) throws Exception {
        for (Annoucement announcement : announcements) {
            if (announcement.getId().equals(id)) {
                return announcement;
            }
        }
        throw new Exception("Announcement not found");
    }
    public Annoucement update(Integer id, AnnouncementDTO announcementDTO) throws Exception {
        for (Annoucement announcement : announcements) {
            if (announcement.getId().equals(id)) {
                announcement.setTitle(announcementDTO.getTitle());
                announcement.setDescription(announcementDTO.getDescription());
                announcement.setCreatedAt(LocalDateTime.now().toString());
                return announcement;
            }
        }
        throw new Exception("Announcement not found");
    }
    public String delete(Integer id) throws Exception {
        for (Annoucement announcement : announcements) {
            if (announcement.getId().equals(id)) {
                announcements.remove(announcement);
                return "Announcement deleted successfully";
            }
        }
        throw new Exception("Announcement not found");
    }

}
