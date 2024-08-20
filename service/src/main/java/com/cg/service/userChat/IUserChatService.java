package com.cg.service.userChat;

import com.cg.domain.dto.messageDTO.IMessagesDTO;
import com.cg.domain.dto.messageDTO.LastMessageDTO;
import com.cg.domain.dto.messageDTO.UserMessageDTO;
import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.message.RoomChat;
import com.cg.domain.entity.message.UserMessage;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IUserChatService extends IGeneralService<UserMessage> {

    List<LastMessageDTO> findAllLastMessage();

    List<UserMessage> findAllByRoomChatId(String roomId);

    List<?> getAllMessagesByRoomId(String roomId);

    RoomChat createNewRoomChatWithAnotherUser(String userId);
}
