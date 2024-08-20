package com.cg.domain.dto.messageDTO;

import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.dto.userDTO.UserDetailDTO;
import com.cg.domain.entity.message.UserMessage;
import com.cg.domain.entity.user.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMessageDTO {

    private String id;

    private String content;

    private UserDTO senderDTO;

    private UserDetailDTO senderDetailDTO;

    private UserDetailDTO recipientDetailDTO;

    private UserDTO recipientDTO;
    private RoomChatDTO roomChatDTO;

    private boolean seen;

    public UserMessageDTO(String id, String content, UserDetailDTO senderDetailDTO, UserDetailDTO recipientDetailDTO, RoomChatDTO roomChatDTO, boolean seen) {
        this.id = id;
        this.content = content;
        this.senderDetailDTO = senderDetailDTO;
        this.recipientDetailDTO = recipientDetailDTO;
        this.roomChatDTO = roomChatDTO;
        this.seen = seen;
    }

    public UserMessageDTO(String id, String content, UserDTO senderDTO, UserDTO recipientDTO, RoomChatDTO roomChatDTO) {
        this.id = id;
        this.content = content;
        this.senderDTO = senderDTO;
        this.recipientDTO = recipientDTO;
        this.roomChatDTO = roomChatDTO;
    }

    public UserMessageDTO(String id, String content, UserDTO senderDTO, UserDTO recipientDTO, RoomChatDTO roomChatDTO, boolean seen) {
        this.id = id;
        this.content = content;
        this.senderDTO = senderDTO;
        this.recipientDTO = recipientDTO;
        this.roomChatDTO = roomChatDTO;
        this.seen = seen;
    }

    public UserMessage toUserMessage(){
        return new UserMessage()
                .setId(id)
                .setContent(content)
                .setSender(senderDTO.toUser())
                .setRecipient(recipientDTO.toUser())
                .setRoomChat(roomChatDTO.toRoomChat());
    }
}
