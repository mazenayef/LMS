package org.lms.Notificiation.Repos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lms.Notificiation.DTOs.NotificationSet;
import org.lms.Notificiation.Models.NotificationModel;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepository {
    public static ArrayList<NotificationModel> notifications = new ArrayList<NotificationModel>();

    public NotificationModel create(NotificationModel notification){
        notifications.add(notification);
        return notification;
    }

    public List<NotificationModel> get(int userId, boolean unRead){
        List<NotificationModel> result = notifications.stream().filter(x -> x.getUserId() == userId).toList();
        if(unRead)
            result = result.stream().filter(x -> x.isRead() == false).toList();
        return result;
    }

    public NotificationModel update(NotificationModel oldNotification, NotificationSet newNotification){
        oldNotification.setCreatedAt(newNotification.createdAt);
        oldNotification.setDescreption(newNotification.descreption);
        oldNotification.setHeader(newNotification.header);
        oldNotification.setUserId(newNotification.userId);
        oldNotification.setRead(newNotification.isRead);
        return oldNotification;
    }

    public NotificationModel markAsRead(NotificationModel notification){
        notification.setRead(true);
        return notification;
    }

    public void delete(NotificationModel notification){
        notifications.remove(notification);
    }

}
