package com.cg.domain.dto.messageDTO;

import com.cg.domain.entity.message.RoomChat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomChatDTO {
    private String id;

    private String name;

    public RoomChat toRoomChat(){
        return new RoomChat()
                .setId(id)
                .setName(name);
    }
}
