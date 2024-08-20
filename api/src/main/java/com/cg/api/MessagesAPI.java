package com.cg.api;

import com.cg.domain.dto.messageDTO.*;
import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.message.*;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.user.UserDetail;
import com.cg.service.groupChat.IGroupChatService;
import com.cg.service.groupMember.IGroupMemberService;
import com.cg.service.groupMessage.IGroupMessageService;
import com.cg.service.roomchat.IRoomChatService;
import com.cg.service.user.IUserService;
import com.cg.service.userChat.IUserChatService;
import com.cg.service.userDetail.IUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessagesAPI {

    @Autowired
    private IGroupMessageService groupMessageService;

    @Autowired
    private IGroupChatService groupChatService;

    @Autowired
    private IGroupMemberService groupMemberService;

    @Autowired
    private IUserChatService userChatService;

    @Autowired
    private IRoomChatService roomChatService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserDetailService userDetailService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> findAllLastMessage(@PathVariable String userId) {
        try {
            List<LastMessageDTO> lastMessageDTOS = userChatService.findAllLastMessage();
            return new ResponseEntity<>(lastMessageDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{recipientId}")
    public ResponseEntity<?> getMessagesWithUserById(@PathVariable String recipientId) {
        try {
            RoomChat roomChat = roomChatService.findBySenderIdAndRecipientId(recipientId);
            return new ResponseEntity<>(roomChat, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //get all messages user by room id
    @GetMapping("/users/{roomId}")
    public ResponseEntity<?> getAllMessagesByRoomId(@PathVariable String roomId) {
        try {
            List<?> roomMessages = userChatService.getAllMessagesByRoomId(roomId);
            return new ResponseEntity<>(roomMessages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/create/room/{userId}")
    public ResponseEntity<?> createNewRoomWithAnotherUser(@PathVariable String userId) {
        try {
            RoomChat roomChat = userChatService.createNewRoomChatWithAnotherUser(userId);
            return new ResponseEntity<>(roomChat.toRoomChatDTO(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/group")
    public ResponseEntity<?>getGroupByListUser(@RequestBody GroupChatCreateDTO groupChatCreateDTO){
        GroupChat groupMessages = groupChatService.findGroupChatByListUser(groupChatCreateDTO.getListUser(),
                groupChatCreateDTO.getCount());
        if (groupMessages == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(groupMessages.toGroupChatDTO(),HttpStatus.OK);
    }

    //create messages group with user
    @PostMapping("/group/{groupId}")
    public ResponseEntity<?> sendMess2GroupMess(@PathVariable String groupId,
                                                @RequestParam String userId, @RequestBody String message) {
        User user = userService.findById(userId).get();
        GroupChat groupChat = groupChatService.findById(groupId).get();
        GroupMessage groupMessage = new GroupMessage("", message, user, groupChat);
        GroupMessage newGroupMessage = groupMessageService.save(groupMessage);

        LastMessageDTO lastMessageDTO = new LastMessageDTO();

        return new ResponseEntity<>(newGroupMessage.toGroupMessageDTO(), HttpStatus.OK);
    }

    //create messages user with user
    @PostMapping("/users/{roomId}")
    public ResponseEntity<?> sendMess2UserMess(@PathVariable String roomId, @RequestParam String recipientId,
                                               @RequestParam String senderId, @RequestBody String message) {
        try{
           UserMessage userMessage = roomChatService.sendMess2UserMess(roomId,recipientId,senderId,message);
           UserMessageDTO userMessageDTO = userMessage.toUserChatDTO();

           UserDetail senderDetail = userDetailService.findByUserId(senderId);
            UserDTO senderDTO = userMessageDTO.getSenderDTO();
            senderDTO.setAvatarFileUrl(senderDetail.getAvatarFileUrl());
            senderDTO.setAvatarFileFolder(senderDetail.getAvatarFileFolder());
            senderDTO.setAvatarFileName(senderDetail.getAvatarFileName());
            senderDTO.setPassword("null");

            UserDetail recipientDetail = userDetailService.findByUserId(recipientId);
            UserDTO recipientDTO = userMessageDTO.getRecipientDTO();
            recipientDTO.setAvatarFileUrl(recipientDetail.getAvatarFileUrl());
            recipientDTO.setAvatarFileFolder(recipientDetail.getAvatarFileFolder());
            recipientDTO.setAvatarFileName(recipientDetail.getAvatarFileName());
            recipientDTO.setPassword("null");

            userMessageDTO.setRecipientDTO(recipientDTO);
            userMessageDTO.setSenderDTO(senderDTO);

            return new ResponseEntity<>(userMessageDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create/group/{groupName}")
    public ResponseEntity<?> createNewGroupChat(@RequestBody List<String> userIds, @PathVariable String groupName) {
        GroupChat groupChat = new GroupChat();
        groupChat.setGroupName(groupName);

        User owner = userService.findByEmail(getPrincipal()).get();
        groupChat.setOwner(owner);
        groupChat.setCreatedBy(owner.getId());
        RoleMember roleMember = new RoleMember();
        roleMember.setId(1L);

        UserDetail userDetail =userDetailService.findByUserId(owner.getId());

        GroupChat newGroupChat = groupChatService.save(groupChat);
        GroupMember newGroupMember = new GroupMember();
        newGroupMember.setMember(owner);
        newGroupMember.setNickName(userDetail.getFullName());
        newGroupMember.setGroupChat(newGroupChat);
        newGroupMember.setRoleMember(roleMember);
        groupMemberService.save(newGroupMember);

        for (String string : userIds) {
            roleMember.setId(2L);
            GroupMember groupMember = new GroupMember();
            User user = userService.findById(string).get();
            groupMember.setMember(user);

            UserDetail user2 =userDetailService.findByUserId(owner.getId());
            groupMember.setNickName(user2.getFullName());
            groupMember.setGroupChat(newGroupChat);
            groupMember.setRoleMember(roleMember);
            groupMemberService.save(groupMember);
        }

        return new ResponseEntity<>(newGroupChat.toGroupChatDTO(), HttpStatus.OK);
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
