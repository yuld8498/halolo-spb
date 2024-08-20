package com.cg.domain.dto.messageDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LastMessageDTO {
    private String id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    private Long ts;
    private String content;
    private String roomChatId;
    private String senderId;
    private String senderName;
    private String recipient;
    private String roomName;
    private String typeChat;
    private String avatarUrl;

    private String fileName;
    private String fileFolder;

    private boolean seen;

    public LastMessageDTO(String id, Date createdAt, Long ts, String content,
                          String roomChatId, String senderId, String senderName, String recipient,
                          String roomName, String typeChat, String avatarUrl) {
        this.id = id;
        this.createdAt = createdAt;
        this.ts = ts;
        this.content = content;
        this.roomChatId = roomChatId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.recipient = recipient;
        this.roomName = roomName;
        this.typeChat = typeChat;
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "LastMessageDTO{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", ts=" + ts +
                ", content='" + content + '\'' +
                ", roomChatId='" + roomChatId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", senderName='" + senderName + '\'' +
                ", recipient='" + recipient + '\'' +
                ", roomName='" + roomName + '\'' +
                ", typeChat='" + typeChat + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
