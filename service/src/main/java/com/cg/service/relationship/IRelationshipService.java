package com.cg.service.relationship;

import com.cg.domain.dto.relationshipDTO.RelationshipDTO;
import com.cg.domain.dto.userDTO.IUserDTO;
import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.relationship.Relationship;
import com.cg.domain.entity.relationship.RelationshipType;
import com.cg.domain.entity.user.User;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IRelationshipService extends IGeneralService<Relationship> {

    List<Relationship> getConnectionOfUser(String userId);

    List<UserDTO> getFollowOfUser(String userId);

    List<UserDTO> getNonFriendOfUser(String userId);

    List<UserDTO> getFriendOfUser(String userId);

    void addFollow(String id, String friendId);

    Long countFollowerByUserId(String userId);

    Long countFollowingByUserId(String userId);

    Long countFriendByUserId(String userId);

    void addFriend(String userId, String emailFriend);

    void doAccept(String senderId, String recipientId);

    void UnFollow(String relationshipId);


    Relationship checkFriend(String userId);
}
