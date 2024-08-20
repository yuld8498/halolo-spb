package com.cg.domain.dto.messageDTO;

import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.message.RoomChat;
import com.cg.domain.entity.message.RoomMember;
import com.cg.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomMemberDTO {
    private String id;
    private String nickName;

    private UserDTO memberDTO;

    private RoomChatDTO roomChatDTO;

    public RoomMember toRoomMember(){
        return new RoomMember()
                .setId(id)
                .setNickName(nickName)
                .setMember(memberDTO.toUser())
                .setRoomChat(roomChatDTO.toRoomChat());
    }
}