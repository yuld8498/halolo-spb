package com.cg.domain.dto.messageDTO;

import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.message.GroupChat;
import com.cg.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupChatDTO {
    private String id;

    private String groupName;

    private UserDTO owner;

    public GroupChat toGroupChat(){
        return new GroupChat()
                .setId(id)
                .setGroupName(groupName)
                .setOwner(owner.toUser());

    }
}
