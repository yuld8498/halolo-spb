package com.cg.domain.dto.messageDTO;

import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.message.GroupChat;
import com.cg.domain.entity.message.GroupMember;
import com.cg.domain.entity.message.RoleMember;
import com.cg.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberDTO {

    private String id;

    private String nickName;

    private RoleMemberDTO roleMemberDTO;

    private UserDTO memberDTO;

    private GroupChatDTO groupChatDTO;

    public GroupMember toGroupMember(){
        return new GroupMember()
                .setId(id)
                .setNickName(nickName)
                .setRoleMember(roleMemberDTO.toRoleMember())
                .setMember(memberDTO.toUser())
                .setGroupChat(groupChatDTO.toGroupChat());
    }
}
