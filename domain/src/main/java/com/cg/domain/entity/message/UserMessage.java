package com.cg.domain.entity.message;

import com.cg.domain.dto.messageDTO.UserMessageDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_messages", indexes = @Index(columnList = "ts"))
public class UserMessage extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean seen;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private User recipient;

    @ManyToOne
    @JoinColumn(name = "room_chat_id", referencedColumnName = "id")
    private RoomChat roomChat;

    public UserMessage(String id, String content, User sender, User recipient, RoomChat roomChat) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
        this.roomChat = roomChat;
    }

    public UserMessageDTO toUserChatDTO(){
        return new UserMessageDTO()
                .setId(id)
                .setContent(content)
                .setSenderDTO(sender.toUserDTO())
                .setRecipientDTO(recipient.toUserDTO())
                .setSeen(seen)
                .setRoomChatDTO(roomChat.toRoomChatDTO());
    }
}
