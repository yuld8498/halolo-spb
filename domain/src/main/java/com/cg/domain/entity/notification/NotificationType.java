package com.cg.domain.entity.notification;

import com.cg.domain.dto.notificationDTO.NotificationTypeDTO;
import com.cg.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications_type", indexes = @Index(columnList = "ts"))
public class NotificationType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeNotification;

    public NotificationTypeDTO toNotificationTypeDTO(){
        return new NotificationTypeDTO()
                .setId(id)
                .setTypeNotification(typeNotification);
    }
}

