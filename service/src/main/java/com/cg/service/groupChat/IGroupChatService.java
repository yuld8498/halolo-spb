package com.cg.service.groupChat;

import com.cg.domain.dto.messageDTO.IGroupMessages;
import com.cg.domain.dto.messageDTO.LastMessageDTO;
import com.cg.domain.entity.message.GroupChat;
import com.cg.domain.entity.message.GroupMember;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IGroupChatService extends IGeneralService<GroupChat> {
    Optional<GroupChat> findByGroupMember(List<GroupMember> list, String groupName);
    GroupChat findGroupChatByListUser(String listUser, int count);
}
