package org.lms.announcement.repositories;

import org.lms.announcement.dtos.AnnouncementDTO;
import org.lms.announcement.models.Announcement;
import org.lms.course.Course;
import org.lms.course.CourseRepository;
import org.lms.shared.exceptions.HttpNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnnouncementRepository {
    private CourseRepository courseRepository;
    private static List<Announcement> announcements = new ArrayList<>();
    private static Integer id = 0;
    AnnouncementRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Announcement create(AnnouncementDTO announcementDTO , Integer courseId) throws Exception {
        Course course = courseRepository.getCourseById(courseId);

        Announcement announcement = new Announcement(id++, announcementDTO.getTitle(), announcementDTO.getDescription(),courseId ,null);
        announcements.add(announcement);
        List<Integer> announcementList = course.getAnnouncementList();
        announcementList.add(announcement.getId());
        course.setAnnouncementList(announcementList);
        return announcement;
    }

    public List<Announcement> findAll(Integer courseId) {
        List<Announcement> announcements = new ArrayList<>();
        for (Announcement announcement : this.announcements) {
            if (announcement.getCourseId().equals(courseId)) {
                announcements.add(announcement);
            }
        }
        return announcements;
    }
    public Announcement findOne(Integer id) throws Exception {
        for (Announcement announcement : announcements) {
            if (announcement.getId().equals(id)) {
                return announcement;
            }
        }
        throw new HttpNotFoundException("Announcement not found");
    }
    public Announcement update(Integer id, AnnouncementDTO announcementDTO) throws Exception {
        for (Announcement announcement : announcements) {
            if (announcement.getId().equals(id)) {
                announcement.setTitle(announcementDTO.getTitle());
                announcement.setDescription(announcementDTO.getDescription());
                announcement.setCreatedAt(LocalDateTime.now().toString());
                return announcement;
            }
        }
        throw new HttpNotFoundException("Announcement not found");
    }
    public void delete(Integer id) throws Exception {
        for (Announcement announcement : announcements) {
            if (announcement.getId().equals(id)) {
                announcements.remove(announcement);
                return ;
            }
        }
    }

    public void addAttachment(Integer announcementId, Integer mediaFileId) throws Exception {
        for (Announcement announcement : announcements) {
            if (announcement.getId().equals(announcementId)) {
                announcement.addMediaFile(mediaFileId);
                return;
            }
        }
        throw new HttpNotFoundException("Announcement not found");
    }

}
