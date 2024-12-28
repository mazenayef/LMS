package org.lms.announcement.controllers;


import org.lms.announcement.dtos.AnnouncementDTO;
import org.lms.announcement.models.Announcement;
import org.lms.announcement.services.AnnouncementService;
import org.lms.authentication.interceptors.CurrentUser;
import org.lms.authentication.interceptors.HasRole;
import org.lms.mediafiles.models.MediaFile;
import org.lms.shared.interceptors.ExcludeFromCommonResponse;
import org.lms.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Controller
@RequestMapping("courses/{courseId}/announcements")
public class AnnouncementController {
    private AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }
    @HasRole({"INSTRUCTOR"})
    @PostMapping("/")
    // only instructor can add announcement
    public ResponseEntity<Announcement> addAnnouncement(@RequestBody AnnouncementDTO announcementDTO , @PathVariable("courseId") String courseId , @CurrentUser User user) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(announcementService.addAnnouncement(announcementDTO , courseId));

    }

    @ExcludeFromCommonResponse
    @HasRole({"INSTRUCTOR"})
    @PostMapping("/{id}/attachments")
    public ResponseEntity<MediaFile> addAttachment(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(announcementService.addAttachment(Integer.parseInt(id), file));
    }

    @GetMapping("/{id}/attachments")
    public ResponseEntity<List<MediaFile>> getAttachments(@PathVariable("id") String id) throws Exception {
        return ResponseEntity.ok().body(announcementService.getAttachments(Integer.parseInt(id)));
    }

    @GetMapping("/")
    // get all announcements
    public ResponseEntity<List<Announcement>> getAllAnnouncements(@PathVariable("courseId") String courseId) {
        return ResponseEntity.ok().body(announcementService.getAllAnnouncements(Integer.parseInt(courseId)));
    }
    @GetMapping("/{id}")
    // get announcement by id
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable("id") String id) throws Exception {
        try {
            return ResponseEntity.ok().body(announcementService.getAnnouncementById(Integer.parseInt(id)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @HasRole({"INSTRUCTOR"})
    @PutMapping("/{id}")
    // update announcement
    public ResponseEntity<Announcement> updateAnnouncement(@PathVariable("id") Integer id, @RequestBody AnnouncementDTO announcementDTO , @CurrentUser User user) throws Exception {
        return ResponseEntity.ok().body(announcementService.updateAnnouncement(id, announcementDTO));

    }
    @HasRole({"INSTRUCTOR"})
    @DeleteMapping("/{id}")
    // delete announcement
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable("id") Integer id) throws Exception {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.ok().build();

    }


}
