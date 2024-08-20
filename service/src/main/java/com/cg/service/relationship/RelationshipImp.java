package com.cg.service.relationship;

import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.notification.Notification;
import com.cg.domain.entity.notification.NotificationType;
import com.cg.domain.entity.relationship.Relationship;
import com.cg.domain.entity.relationship.RelationshipType;
import com.cg.domain.entity.user.FriendList;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.user.UserDetail;
import com.cg.repository.notification.NotificationRepository;
import com.cg.repository.relationship.RelationshipRepository;
import com.cg.repository.relationship.RelationshipTypeRepository;
import com.cg.repository.user.FriendListRepository;
import com.cg.repository.user.UserDetailRepository;
import com.cg.repository.user.UserRepository;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class RelationshipImp implements IRelationshipService {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private FriendListRepository friendListRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private RelationshipTypeRepository relationshipTypeRepository;

    @Override
    public List<Relationship> findAll() {
        return relationshipRepository.findAll();
    }

    @Override
    public Optional<Relationship> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Relationship> findById(String id) {
        return relationshipRepository.findById(id);
    }

    @Override
    public Relationship save(Relationship relationship) {
        return relationshipRepository.save(relationship);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {
        Optional<Relationship> relationshipOptional = relationshipRepository.findById(id);
        if (relationshipOptional.isPresent()) {
            Relationship relationship = relationshipOptional.get();
            relationship.setDeleted(true);
            relationshipRepository.save(relationship);

            User sender = relationship.getSender();
            User recipient = relationship.getRecipient();

            FriendList senderFr = friendListRepository.findByUserId(sender.getId());
            FriendList recipientFr = friendListRepository.findByUserId(recipient.getId());

            String listStringFriendOfSender = senderFr.getFriends();
            String listStringFriendOfRecipient = recipientFr.getFriends();

            List<String> listFriendOfSender = Arrays.asList(listStringFriendOfSender.split(","));
            List<String> listFriendOfRecipient = Arrays.asList(listStringFriendOfRecipient.split(","));

            int indexOfUserInListOfSender = listFriendOfSender.indexOf(recipient.getId());
            int indexOfUserInListOfRecipient = listFriendOfRecipient.indexOf(sender.getId());

            listFriendOfSender.remove(indexOfUserInListOfSender);
            listFriendOfRecipient.remove(indexOfUserInListOfRecipient);

            String friendOfSender ="" ;
            String friendOfRecipient="" ;

            for (String friends : listFriendOfSender){
                friendOfSender = friendOfSender.concat(friends);
                if (listFriendOfSender.indexOf(friends) < listFriendOfSender.size()-1){
                    friendOfSender = friendOfSender.concat(",");
                }
            }
            senderFr.setFriends(friendOfSender);

            for (String friends : listFriendOfRecipient){
                friendOfRecipient = friendOfRecipient.concat(friends);
                if (listFriendOfRecipient.indexOf(friends) < listFriendOfRecipient.size()-1){
                    friendOfRecipient = friendOfRecipient.concat(",");
                }
            }
            recipientFr.setFriends(friendOfRecipient);

            List<FriendList> newFriendList = new ArrayList<>();
            newFriendList.add(senderFr);
            newFriendList.add(recipientFr);

            friendListRepository.saveAll(newFriendList);

        }
    }

    @Override
    public List<Relationship> getConnectionOfUser(String userId) {
        return relationshipRepository.getConnectionForUser(userId);
    }

    @Override
    public List<UserDTO> getFollowOfUser(String userId) {
        List<Relationship> relationships = relationshipRepository.getFollowOfUser(userId);

        List<UserDTO> userDTOS = new ArrayList<>();

        for (Relationship re : relationships) {
            String friendId;

            if (re.getSender().getId().equals(userId)) {
                friendId = re.getRecipient().getId();
            }
            else {
                friendId = re.getSender().getId();
            }

            UserDTO newUserDTO = userRepository.findById(friendId).get().toUserDTO();
            newUserDTO.setId(re.getId());
            newUserDTO.setPassword("null");
            userDTOS.add(newUserDTO);
        }

        return userDTOS;
    }

    @Override
    public List<UserDTO> getNonFriendOfUser(String userId) {
        FriendList friendList = friendListRepository.findByUserId(userId);
        String listFr = "x";

        if ( friendList.getFriends() != null){
            listFr = friendList.getFriends();
        }
        List<String> listFriend = Arrays.asList(listFr.split(","));

        return userRepository.getUserNonFriend(listFriend);
    }

    @Override
    public List<UserDTO> getFriendOfUser(String userId) {
        List<Relationship> relationships = relationshipRepository.getConnectionForUser(userId);

        List<UserDTO> userDTOS = new ArrayList<>();

        for (Relationship re : relationships) {
            if (re.getSender().getId().equals(userId)) {
                UserDetail userDetail = userDetailRepository.findByUserId(re.getRecipient().getId());
                UserDTO userDTO = re.getRecipient().toUserDTO();
                userDTO.setAvatarFileFolder(userDetail.getAvatarFileFolder());
                userDTO.setAvatarFileName(userDetail.getAvatarFileName());
                userDTO.setAvatarFileUrl(userDetail.getAvatarFileUrl());
                userDTO.setFullName(userDetail.getFullName());
                userDTO.setPassword("null");
                userDTOS.add(userDTO);
            }
            else {
                UserDetail userDetail = userDetailRepository.findByUserId(re.getSender().getId());
                UserDTO userDTO = re.getSender().toUserDTO();
                userDTO.setAvatarFileFolder(userDetail.getAvatarFileFolder());
                userDTO.setAvatarFileName(userDetail.getAvatarFileName());
                userDTO.setAvatarFileUrl(userDetail.getAvatarFileUrl());
                userDTO.setFullName(userDetail.getFullName());
                userDTO.setPassword("null");
                userDTOS.add(userDTO);
            }
        }
        return userDTOS;
    }

    @Override
    public void addFollow(String id, String friendId) {
        Relationship relationship = new Relationship();

        Optional<User> user = userRepository.findById(id);
        Optional<User> friend = userRepository.findById(friendId);

        Optional<RelationshipType> relationshipType = relationshipTypeRepository.findById(2L);

        relationship.setSender(user.get());
        relationship.setRecipient(friend.get());
        relationship.setRelationshipType(relationshipType.get());
        relationship.setDeleted(false);

        relationshipRepository.save(relationship);

        FriendList friendList = friendListRepository.findByUserId(user.get().getId());
        String listFriendToString = friendList.getFriends();
        String newFriendInList = ",".concat(friendId);
        String newListFriendToString = listFriendToString.concat(newFriendInList);
        friendList.setFriends(newListFriendToString);
        friendListRepository.save(friendList);
    }

    @Override
    public Long countFollowerByUserId(String userId) {
        return relationshipRepository.countFollowers(userId);
    }

    @Override
    public Long countFollowingByUserId(String userId) {
        return relationshipRepository.countFollowing(userId);
    }

    @Override
    public Long countFriendByUserId(String userId) {
        return relationshipRepository.countFriend(userId);
    }

    @Override
    public void addFriend(String userId, String emailFriend) {
        Relationship relationship = new Relationship();
        Optional<User> user = userRepository.findById(userId);
        Optional<User> friend = userRepository.findByEmail(emailFriend);
        RelationshipType relationshipType = new RelationshipType();
        relationshipType.setId(2L);
        relationship.setSender(user.get());
        relationship.setRecipient(friend.get());
        relationship.setRelationshipType(relationshipType);
        relationshipRepository.save(relationship);

        UserDetail userDetail = userDetailRepository.findByUserId(userId);

        Notification notification = new Notification();
        notification.setContent(userDetail.getFullName() + " sent you a friend request");
        NotificationType notificationType = new NotificationType();
        notificationType.setId(1L);
        notification.setNotificationType(notificationType);
        notification.setSender(user.get());
        notification.setRecipient(friend.get());
        notificationRepository.save(notification);

        String newFriendInList = ",".concat(friend.get().getId());
        FriendList friendList = friendListRepository.findByUserId(userId);
        String stringFriendList = friendList.getFriends();
        if (stringFriendList!=null){
            friendList.setFriends(stringFriendList.concat(newFriendInList));
        }else {
            friendList.setFriends(friend.get().getId());
        }
        friendListRepository.save(friendList);
    }

    @Override
    public void doAccept(String senderId, String recipientId) {
        Relationship newRelationship = relationshipRepository.findRelationshipBySenderIdAndRecipientIdOfUser(senderId, recipientId);

        RelationshipType relationshipType = relationshipTypeRepository.findById(1L).get();

        newRelationship.setRelationshipType(relationshipType);
        relationshipRepository.save(newRelationship);

        User sender = new User();
        sender.setId(recipientId);

        User recipient = new User();
        recipient.setId(senderId);

        NotificationType notificationType = new NotificationType();
        notificationType.setId(5L);

        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notification.setNotificationType(notificationType);
        notification.setCreatedBy(senderId);
        notification.setContent("friend request accept");
        notificationRepository.save(notification);

        String newFriendInList = ",".concat(senderId);
        FriendList friendList = friendListRepository.findByUserId(recipientId);
        String stringFriendList = friendList.getFriends();
        if (stringFriendList !=null){
            friendList.setFriends(stringFriendList.concat(newFriendInList));
        }else {
            friendList.setFriends(senderId);
        }
        friendListRepository.save(friendList);

    }

    @Override
    public void UnFollow(String relationshipId) {
        Optional<Relationship> relationship = relationshipRepository.findById(relationshipId);
        Relationship newRelationship = relationship.get();
        newRelationship.setDeleted(true);
        relationshipRepository.save(newRelationship);

        User user = userRepository.findByEmail(appUtils.getPrincipal()).get();
        FriendList friendList = friendListRepository.findByUserId(user.getId());
        String stringFriendList = friendList.getFriends();
        String recipientId = newRelationship.getRecipient().getId();
        String senderId = newRelationship.getSender().getId();
        if (stringFriendList!=null){
            List<String> list = Arrays.asList(stringFriendList.split(","));
            String userId  = user.getId().equals(recipientId) ? senderId : recipientId;
            list.remove(userId);
            String listNewFollow = "";
            for (String s:list){
                if (list.indexOf(s)< list.size()-1){
                    String userString = s.concat(",");
                    listNewFollow =  listNewFollow.concat(userString);
                }
                listNewFollow = listNewFollow.concat(s);
            }
        }
    }

    @Override
    public Relationship checkFriend(String userId) {
        User user = userRepository.findByEmail(appUtils.getPrincipal()).get();
        return relationshipRepository.checkFriend(user.getId(),userId);
    }

}
