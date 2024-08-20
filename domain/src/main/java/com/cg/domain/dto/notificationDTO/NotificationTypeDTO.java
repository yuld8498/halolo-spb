package com.cg.domain.dto.notificationDTO;

import com.cg.domain.entity.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTypeDTO {
    private Long id;

    private String typeNotification;

    public NotificationType toNotificationType(){
        return new NotificationType()
                .setId(id)
                .setTypeNotification(typeNotification);
    }
}
