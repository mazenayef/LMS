package org.lms.Notificiation.Controllers;

import javax.mail.MessagingException;

import org.lms.Models.ResponseObject;
import org.lms.Notificiation.DTOs.EmailObject;
import org.lms.Notificiation.DTOs.NotificationSet;
import org.lms.Notificiation.Services.NotificationService;
import org.lms.authentication.interceptors.CurrentUser;
import org.lms.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    
    private final NotificationService _service;

    @PutMapping("/email/{to}/{header}/{message}")
    public void test(@PathVariable String to, @PathVariable String header, @PathVariable String message) throws MessagingException{
        EmailObject object = new EmailObject(to, header, message);
        _service.notifiy(object);
    }

    public NotificationController(NotificationService service){
        this._service = service;
    }
    @GetMapping(value = "find/{unRead}", produces = "application/json")
    public ResponseEntity<ResponseObject> getNotificationForUser(@CurrentUser User user, @PathVariable boolean unRead){
        return ResponseEntity.ok(_service.get(user.getId(), unRead));
    }

    @PutMapping(value = "/update/{id}", produces = "application/json")
    public ResponseEntity<ResponseObject> update(@PathVariable int id, @RequestBody NotificationSet notification){
        ResponseObject result = _service.update(id, notification);
        if(result.data != null)
            return ResponseEntity.ok(result);

        return ResponseEntity.badRequest().body(result);
    }

    @PatchMapping(value = "/markRead/{id}", produces = "application/json")
    public ResponseEntity<ResponseObject> markAsRead(@PathVariable int id){
        ResponseObject result = _service.markAsRead(id);
        if(result.data != null)
            return ResponseEntity.ok(result);

        return ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable int id){
        _service.delete(id);
    }
}
