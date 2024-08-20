package com.cg.domain.entity.notification;

import com.cg.domain.dto.notificationDTO.NotificationDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications", indexes = @Index(columnList = "ts"))
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private User recipient;

    @ManyToOne
    @JoinColumn(name = "notification_type_id", referencedColumnName = "id")
    private NotificationType notificationType;

    private String note;

    private boolean seen;

    public NotificationDTO toNotificationDTO(){
        return new NotificationDTO()
                .setId(id)
                .setContent(content)
                .setSenderId(sender.getId())
                .setRecipientId(recipient.getId())
                .setNote(note)
                .setCreateAt(getCreatedAt())
                .setNotificationTypeId(notificationType.toNotificationTypeDTO().getId());
    }

    public Notification(String id, String content, User sender, User recipient, NotificationType notificationType, String note) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
        this.notificationType = notificationType;
        this.note =note;
    }

    public Notification(String id, String content, User sender, User recipient, NotificationType notificationType) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
        this.notificationType = notificationType;
    }
}

