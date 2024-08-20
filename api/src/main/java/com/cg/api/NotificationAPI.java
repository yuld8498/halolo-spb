package com.cg.api;

import com.cg.domain.dto.notificationDTO.NotificationDTO;
import com.cg.domain.entity.notification.Notification;
import com.cg.domain.entity.user.User;
import com.cg.service.notification.INotificationService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/notification")
public class NotificationAPI {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private INotificationService notificationService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllNotificationByUserId(@PathVariable String id) {
     try{
         List<NotificationDTO> notifications = notificationService.getAllNotificationDTOByUserId(id);

         if (notifications == null) {
             return new ResponseEntity<>("Notifications user has ID :" + id + "not found" + "!", HttpStatus.NO_CONTENT);
         }

         return new ResponseEntity<>(notifications, HttpStatus.OK);
     }catch (Exception e){
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
    }

//    @PostMapping("/create")
//    public ResponseEntity<?> doCreateNotification(@Validated @RequestBody NotificationDTO notificationDTO, BindingResult bindingResult){
//        if (bindingResult.hasErrors()) {
//            return appUtils.mapErrorToResponse(bindingResult);
//        }
//
//        try {
//            Notification notification = notificationDTO.toNotification();
//
//            notification.setId("");
//
//            notification.setDeleted(false);
//
//            notification = notificationService.save(notification);
//
//            NotificationDTO notificationRes = notification.toNotificationDTO();
//
//            return new ResponseEntity<>(notificationRes, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Server ko xử lý được", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable String id) {

        Optional<Notification> notification = notificationService.findById(id);
        Notification newNotification = notification.get();
        newNotification.setDeleted(true);

        try {
            notificationService.save(newNotification);

            return new ResponseEntity<>("Delete is success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server can't handle", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/deleteAll/{userId}")
    public ResponseEntity<?> deleteAllNotification(@PathVariable String userId) {
        try{
            List<Notification> notifications = notificationService.findAllNotificationByUserId();
            for (Notification notification:notifications){
                notification.setDeleted(true);
            }
            notificationService.saveAll(notifications);
            return new ResponseEntity<>("Delete is success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server can't handle", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/seen/{id}")
    public ResponseEntity<?> seenNotification(@PathVariable String id) {

        Optional<Notification> notification = notificationService.findById(id);
        Notification newNotification = notification.get();
        newNotification.setSeen(true);

        try {
            notificationService.save(newNotification);

            return new ResponseEntity<>("Delete is success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server can't handle", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/seenAll")
    public ResponseEntity<?> seenAllNotification() {
        try{
            List<Notification> notifications = notificationService.findAllNotificationByUserId();
            for (Notification notification:notifications){
                notification.setSeen(true);
            }
            notificationService.saveAll(notifications);
            return new ResponseEntity<>("Delete is success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server can't handle", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
