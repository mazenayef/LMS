package org.lms.announcement.services;

import lombok.Getter;
import lombok.Setter;
import org.lms.announcement.dtos.AnnouncementDTO;
import org.lms.announcement.models.Announcement;
import org.lms.announcement.repositories.AnnouncementRepository;
import org.lms.mediafiles.models.MediaFile;
import org.lms.mediafiles.services.MediaFilesService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Service
public class AnnouncementService {
    private AnnouncementRepository announcementRepository;
    private MediaFilesService mediaFilesService;
    public AnnouncementService(AnnouncementRepository announcementRepository, MediaFilesService mediaFilesService) {
        this.announcementRepository = announcementRepository;
        this.mediaFilesService = mediaFilesService;
    }
    public Announcement addAnnouncement(AnnouncementDTO announcementDTO , String courseId) {
        return announcementRepository.create(announcementDTO , Integer.parseInt(courseId));
    }
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }
    public Announcement getAnnouncementById(Integer id) throws Exception {
        return announcementRepository.findOne(id);
    }
    public Announcement updateAnnouncement(Integer id, AnnouncementDTO announcementDTO) throws Exception {
        return announcementRepository.update(id, announcementDTO);
    }
    public void deleteAnnouncement(Integer id) throws Exception {
        announcementRepository.delete(id);
    }

    public MediaFile addAttachment(Integer announcementId, MultipartFile file) throws Exception {
        Announcement announcement = this.announcementRepository.findOne(announcementId);
        MediaFile mediaFile = this.mediaFilesService.createMedia(file);
        this.announcementRepository.addAttachment(announcementId, mediaFile.getId());

        return mediaFile;
    }

    public List<MediaFile> getAttachments(Integer announcementId) throws Exception {
        Announcement announcement = this.announcementRepository.findOne(announcementId);
        List<MediaFile> mediaFiles = new ArrayList<>();
        List<Integer> attachments = announcement.getMediaFileList();
        for (int i = 0; i < attachments.size(); i++) {
            mediaFiles.add(this.mediaFilesService.getMedia(attachments.get(i)));
        }
        return mediaFiles;
    }
}
