package org.lms.announcement.controllers;


import org.lms.announcement.dtos.AnnouncementDTO;
import org.lms.announcement.models.Annoucement;
import org.lms.announcement.services.AnnouncementService;
import org.lms.authentication.interceptors.CurrentUser;
import org.lms.authentication.interceptors.HasRole;
import org.lms.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Controller
@RequestMapping("course/{courseId}/announcements")
public class AnnouncementController {
    private AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }
    @HasRole({"INSTRUCTOR"})
    @PostMapping("/")
    // only instructor can add announcement
    public ResponseEntity<Annoucement> addAnnouncement(@RequestBody AnnouncementDTO announcementDTO , @PathVariable("courseId") String courseId , @CurrentUser User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(announcementService.addAnnouncement(announcementDTO , courseId));

    }
    @GetMapping("/")
    // get all announcements
    public ResponseEntity<List<Annoucement>> getAllAnnouncements() {
        return ResponseEntity.ok().body(announcementService.getAllAnnouncements());
    }
    @GetMapping("/{id}")
    // get announcement by id
    public ResponseEntity<Annoucement> getAnnouncementById(@PathVariable("id") String id) throws Exception {
        try {
            return ResponseEntity.ok().body(announcementService.getAnnouncementById(Integer.parseInt(id)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @HasRole({"INSTRUCTOR"})
    @PutMapping("/{id}")
    // update announcement
    public ResponseEntity<Annoucement> updateAnnouncement(@PathVariable("id") Integer id,@RequestBody AnnouncementDTO announcementDTO , @CurrentUser User user) throws Exception {
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
