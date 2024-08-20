package com.cg.service.notification;

import com.cg.domain.dto.notificationDTO.NotificationDTO;
import com.cg.domain.entity.notification.Notification;
import com.cg.service.IGeneralService;

import java.util.List;

public interface INotificationService extends IGeneralService<Notification> {

    List<NotificationDTO> getAllNotificationDTOByUserId(String id);

    List<Notification> findAllNotificationByUserId();

    void saveAll(List<Notification> notifications);
}
