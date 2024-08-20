package com.cg.service.roomchat;

import com.cg.domain.dto.messageDTO.RoomChatDTO;
import com.cg.domain.entity.message.IRoomChat;
import com.cg.domain.entity.message.RoomChat;
import com.cg.domain.entity.message.RoomMember;
import com.cg.domain.entity.message.UserMessage;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.user.UserDetail;
import com.cg.repository.message.RoomChatRepository;
import com.cg.repository.message.UserChatRepository;
import com.cg.repository.user.UserDetailRepository;
import com.cg.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomChatServiceImp implements IRoomChatService{
    @Autowired
    private RoomChatRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UserChatRepository userChatRepository;
    @Override
    public List<RoomChat> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<RoomChat> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<RoomChat> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public RoomChat save(RoomChat roomChat) {
        return repository.save(roomChat);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {

    }

    @Override
    public Optional<RoomChatDTO> getRoomChatByRoomMembers(List<RoomMember> roomMembers) {
        return repository.getRoomChatByRoomMembers(roomMembers);
    }

    @Override
    public RoomChat findBySenderIdAndRecipientId(String recipientId) {
        User user = userRepository.findByEmail(getPrincipal()).get();
        IRoomChat iRoomChat = repository.findRoomChatBySenderIdAndRecipientId(user.getId(), recipientId);
        RoomChat roomChat = new RoomChat();
        roomChat.setName(iRoomChat.getName());
        roomChat.setId(iRoomChat.getId());
        return roomChat;
    }

    @Override
    public RoomChatDTO findBySenderAndRecipient(String senderId, String recipientId) {
        return repository.findRoomChatDTOBySenderAndRecipient(senderId, recipientId);
    }

    @Override
    public UserMessage sendMess2UserMess(String roomId, String recipientId, String senderId, String messages) {
        User sender = userRepository.findById(senderId).get();
        User recipient = userRepository.findById(recipientId).get();
        UserDetail senderDetail = userDetailRepository.findByUserId(senderId);
        UserDetail recipientDetail = userDetailRepository.findByUserId(recipientId);

        Optional<RoomChat> roomChatOps = repository.findById(roomId);
        RoomChat roomChat = new RoomChat();
        RoomChatDTO roomChatDTO = repository.findRoomChatDTOBySenderAndRecipient(senderId,recipientId);

        if (!roomChatOps.isPresent()) {
            if (roomChatDTO == null){
                roomChat.setName(senderDetail.getFullName() + "to" + recipientDetail.getFullName());
                roomChat = repository.save(roomChat);
            }else {
                roomChat= roomChatDTO.toRoomChat();
            }
        } else {
            roomChat = roomChatOps.get();
        }

        UserMessage userMessage = new UserMessage();
        userMessage.setContent(messages);
        userMessage.setRecipient(recipient);
        userMessage.setSender(sender);
        userMessage.setRoomChat(roomChat);
        userMessage.setCreatedBy(sender.getEmail());
        UserMessage newUserMess = userChatRepository.save(userMessage);
        return newUserMess;
    }

    private String getPrincipal() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();

        } else {
            username = "";
        }

        return username;
    }
}
