package com.cg.domain.dto.notificationDTO;

import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.notification.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private String id;

    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    private String senderId;

    private String senderAvatar;

    private String senderFileFolder;
    private String senderFileUrl;

    private String senderName;

    private String recipientId;

    private String recipientAvatar;

    private Long notificationTypeId;

    private boolean deleted;

    private String note;

    private boolean seen;

    public NotificationDTO(String id, String content, Date createAt, String senderId, String senderAvatar,
                           String senderName, String recipientId, String recipientAvatar,
                           Long notificationTypeId, boolean deleted, String note, boolean seen) {
        this.id = id;
        this.content = content;
        this.createAt = createAt;
        this.senderId = senderId;
        this.senderAvatar = senderAvatar;
        this.senderName = senderName;
        this.recipientId = recipientId;
        this.recipientAvatar = recipientAvatar;
        this.notificationTypeId = notificationTypeId;
        this.deleted = deleted;
        this.note = note;
        this.seen = seen;
    }

    public Notification toNotification(){
        return new Notification()
                .setId(id)
                .setNote(note)
                .setContent(content);
    }

}
