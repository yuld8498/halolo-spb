package com.cg.domain.dto.messageDTO;

import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.message.GroupChat;
import com.cg.domain.entity.message.GroupMessage;
import com.cg.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessageDTO {
    private String id;

    private String message;

    private UserDTO userDTO;

    private GroupChatDTO groupChatDTO;

    public GroupMessage toGroupMessage(){
        return new GroupMessage()
                .setId(id)
                .setMessage(message)
                .setUser(userDTO.toUser())
                .setGroupChat(groupChatDTO.toGroupChat());
    }
}
