package org.lms.announcement.controllers;


import org.lms.announcement.dtos.AnnouncementDTO;
import org.lms.announcement.models.Annoucement;
import org.lms.announcement.services.AnnouncementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Controller
@RequestMapping("auth/course/{courseId}/announcements")
public class AnnouncementController {
    private AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping("/")
    // only instructor can add announcement
    // announcement without attachment
    public ResponseEntity<Annoucement> addAnnouncement(@RequestBody AnnouncementDTO announcementDTO , @PathVariable("courseId") String courseId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(announcementService.addAnnouncement(announcementDTO , courseId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
//    @PostMapping("/addwithattachment")
//    // announcement with attachment
//    ResponseEntity<Annoucement> addAnnouncement(AnnouncementDTO announcementDTO, List <MultipartFile> file) {
//        return ResponseEntity.ok().body(announcementService.addAnnouncement(announcementDTO, file));
//    }
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
    @PutMapping("/{id}")
    // update announcement
    public ResponseEntity<Annoucement> updateAnnouncement(@PathVariable("id") Integer id,@RequestBody AnnouncementDTO announcementDTO) throws Exception {
        try {
            return ResponseEntity.ok().body(announcementService.updateAnnouncement(id, announcementDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{id}")
    // delete announcement
    public ResponseEntity<String> deleteAnnouncement(@PathVariable("id") Integer id) throws Exception {
        try {
            return ResponseEntity.ok().body(announcementService.deleteAnnouncement(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
