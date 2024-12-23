package org.lms.Notificiation.Services;

import java.time.LocalDateTime;

import javax.mail.MessagingException;

import org.lms.Models.ResponseObject;
import org.lms.Notificiation.DTOs.EmailObject;
import org.lms.Notificiation.DTOs.NotificationSet;
import org.lms.Notificiation.Models.NotificationModel;
import org.lms.Notificiation.Repos.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository _repo;
    private final EmailService _emailService;
    public NotificationService(NotificationRepository repo,EmailService emailService){
        this._repo = repo;
        this._emailService = emailService;
    }

    public void notifiy(EmailObject email) throws MessagingException{
        int id = 0;
        for(NotificationModel n : NotificationRepository.notifications)
            id = Math.min(id,n.getId());
        
        NotificationModel notification = new NotificationModel(
            id+1,
            email.getUserId(),
            email.getHeader(),
            email.getMessage(),
            LocalDateTime.now(),
            false);

        _repo.create(notification);
        _emailService.sendEmail(email);
    }

    public ResponseObject get(int userId, boolean unRead) {
        return new ResponseObject("normalized",_repo.get(userId,unRead));
    }

    public ResponseObject update(int id, NotificationSet newNotification){
        NotificationModel old = null;
        for(NotificationModel n : NotificationRepository.notifications)
            if(n.getId() == id){
                old = n;
                break;
            }
        if(old == null)
            return new ResponseObject("could not find the notificaiton",null);

        return new ResponseObject("normailized",_repo.update(old, newNotification));
    }

    public ResponseObject markAsRead(int id){
        NotificationModel element = null;
        for (NotificationModel n : NotificationRepository.notifications) {
            if(n.getId() == id){
                element = n;
                break;
            }
        }
        if(element != null)
            return new ResponseObject("marked",_repo.markAsRead(element));

        return new ResponseObject("nor found",null);
    }

    public void delete(int id){
        NotificationModel element = null;
        for (NotificationModel n : NotificationRepository.notifications) {
            if(n.getId() == id){
                element = n;
                break;
            }
        }
        if(element != null)
            _repo.delete(element);
    }
}
