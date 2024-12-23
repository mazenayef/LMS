package org.lms.announcement.services;

import lombok.Getter;
import lombok.Setter;
import org.lms.announcement.dtos.AnnouncementDTO;
import org.lms.announcement.models.Annoucement;
import org.lms.announcement.repositories.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@Service
public class AnnouncementService {
    private AnnouncementRepository announcementRepository;
    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }
    public Annoucement addAnnouncement(AnnouncementDTO announcementDTO , String courseId) {
        return announcementRepository.create(announcementDTO , Integer.parseInt(courseId));
    }
    public List<Annoucement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }
    public Annoucement getAnnouncementById(Integer id) throws Exception {
        return announcementRepository.findOne(id);
    }
    public Annoucement updateAnnouncement(Integer id, AnnouncementDTO announcementDTO) throws Exception {
        return announcementRepository.update(id, announcementDTO);
    }
    public void deleteAnnouncement(Integer id) throws Exception {
        announcementRepository.delete(id);
    }

}
