package com.cg.service.userChat;

import com.cg.domain.dto.messageDTO.*;
import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.message.*;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.user.UserDetail;
import com.cg.repository.message.GroupMessageRepository;
import com.cg.repository.message.RoomChatRepository;
import com.cg.repository.message.RoomMemberRepository;
import com.cg.repository.message.UserChatRepository;
import com.cg.repository.user.UserDetailRepository;
import com.cg.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserChatServiceImp implements IUserChatService{
    @Autowired
    private UserChatRepository userChatRepository;
    @Autowired
    private GroupMessageRepository groupMessageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomChatRepository roomChatRepository;
    @Autowired
    private RoomMemberRepository roomMemberRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Override
    public List<UserMessage> findAll() {
        return userChatRepository.findAll();
    }

    @Override
    public Optional<UserMessage> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserMessage> findById(String id) {
        return userChatRepository.findById(id);
    }

    @Override
    public UserMessage save(UserMessage userChat) {
        return userChatRepository.save(userChat);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {
        Optional<UserMessage> userChat =userChatRepository.findById(id);
        if (userChat.isPresent()){
            UserMessage newUserChat = userChat.get();
            newUserChat.setDeleted(true);
            userChatRepository.save(newUserChat);
        }
    }


    @Override
    public List<LastMessageDTO> findAllLastMessage() {
        String userName = getPrincipal();
        User user = userRepository.findByEmail(userName).get();

        List<IMessagesDTO> iMessagesDTOS = userChatRepository.findAllLastMessageOfUser(user.getId());
        List<LastMessageDTO> lastMessageDTOS = new ArrayList<>();

        for (IMessagesDTO messagesDTO : iMessagesDTOS) {
            LastMessageDTO lastMessageDTO = new LastMessageDTO();

            lastMessageDTO.setId(messagesDTO.getId());
            lastMessageDTO.setContent(messagesDTO.getContent());
            lastMessageDTO.setAvatarUrl(messagesDTO.getAvatarUrl());
            lastMessageDTO.setTypeChat(messagesDTO.getType());
            lastMessageDTO.setFileFolder(messagesDTO.getFileFolder());
            lastMessageDTO.setFileName(messagesDTO.getFileName());

            lastMessageDTO.setRoomChatId(messagesDTO.getRoomChatId());
            lastMessageDTO.setRoomName(messagesDTO.getRoomName());

            lastMessageDTO.setRecipient(messagesDTO.getRecipient());
            lastMessageDTO.setSenderId(messagesDTO.getSenderId());
            lastMessageDTO.setSenderName(messagesDTO.getSenderName());

            lastMessageDTO.setCreatedAt(messagesDTO.getCreateAt());
            lastMessageDTO.setTs(messagesDTO.getTs());
            lastMessageDTO.setSeen(messagesDTO.getSeen());
            lastMessageDTOS.add(lastMessageDTO);
        }
        return lastMessageDTOS;
    }

    @Override
    public List<UserMessage> findAllByRoomChatId(String roomId) {
        return userChatRepository.findAllByRoomChatIdOrderByTsAsc(roomId);
    }

    @Override
    public List<?> getAllMessagesByRoomId(String roomId) {
        List<IMessagesDTO> userMessages = userChatRepository.getAllMessagesDtoOfRoomById(roomId);
        List<UserMessageDTO> userMessageDTOS = new ArrayList<>();


            for (IMessagesDTO iMessagesDTO : userMessages) {
                UserMessageDTO userMessageDTO =new UserMessageDTO();

                RoomChatDTO roomChatDTO = new RoomChatDTO();
                roomChatDTO.setId(iMessagesDTO.getRoomChatId());

                UserDTO senderDTO = new UserDTO();
                senderDTO.setAvatarFileName(iMessagesDTO.getSenderFileName());
                senderDTO.setAvatarFileUrl(iMessagesDTO.getSenderFileUrl());
                senderDTO.setAvatarFileFolder(iMessagesDTO.getSenderFileFolder());
                senderDTO.setId(iMessagesDTO.getSenderId());
                senderDTO.setFullName(iMessagesDTO.getSenderName());
                senderDTO.setPassword("null");

                UserDTO recipientDTO = new UserDTO();
                recipientDTO.setAvatarFileName(iMessagesDTO.getRecipientFileName());
                recipientDTO.setAvatarFileUrl(iMessagesDTO.getRecipientFileUrl());
                recipientDTO.setAvatarFileFolder(iMessagesDTO.getRecipientFileFolder());
                recipientDTO.setId(iMessagesDTO.getRecipientId());
                recipientDTO.setFullName(iMessagesDTO.getRecipientName());
                recipientDTO.setPassword("null");

                userMessageDTO.setRoomChatDTO(roomChatDTO);
                userMessageDTO.setSenderDTO(senderDTO);

                userMessageDTO.setId(iMessagesDTO.getId());
                userMessageDTO.setContent(iMessagesDTO.getContent());
                userMessageDTO.setSeen(iMessagesDTO.getSeen());

                userMessageDTOS.add(userMessageDTO);
            }

            return userMessageDTOS;
    }

    @Override
    public RoomChat createNewRoomChatWithAnotherUser(String userId) {
        User user =userRepository.findByEmail(getPrincipal()).get();
        RoomChatDTO roomChatDTO = roomChatRepository.findRoomChatDTOBySenderAndRecipient(user.getId(),userId);
        if (roomChatDTO == null){

            User anotherUser = userRepository.findById(userId).get();
            UserDetail userDetail =userDetailRepository.findByUserId(user.getId());
            UserDetail anotherUserDetail = userDetailRepository.findByUserId(anotherUser.getId());

            RoomChat roomChat = new RoomChat();
            roomChat.setName(userDetail.getFullName() + " to " + anotherUserDetail.getFullName());
            RoomChat newRoom = roomChatRepository.save(roomChat);

            RoomMember member1 = new RoomMember();
            member1.setMember(user);
            member1.setRoomChat(newRoom);
            member1.setNickName(userDetail.getFullName());
            roomMemberRepository.save(member1);

            RoomMember member2 = new RoomMember();
            member2.setMember(anotherUser);
            member2.setRoomChat(newRoom);
            member2.setNickName(anotherUserDetail.getFullName());
            roomMemberRepository.save(member2);
            return roomChat;
        }
        return roomChatDTO.toRoomChat();
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
