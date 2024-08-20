package com.cg.service.roomchat;

import com.cg.domain.dto.messageDTO.RoomChatDTO;
import com.cg.domain.entity.message.RoomChat;
import com.cg.domain.entity.message.RoomMember;
import com.cg.domain.entity.message.UserMessage;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;


public interface IRoomChatService extends IGeneralService<RoomChat> {
    Optional<RoomChatDTO> getRoomChatByRoomMembers(List<RoomMember> roomMembers);

    RoomChat findBySenderIdAndRecipientId(String recipientId);

    RoomChatDTO findBySenderAndRecipient(String senderId, String recipientId);

    UserMessage sendMess2UserMess(String roomId, String recipientId,String senderId, String messages);
}
