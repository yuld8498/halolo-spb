package com.cg.repository.relationship;

import com.cg.domain.dto.userDTO.IUserDTO;
import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.relationship.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, String> {
    @Query(value = "SELECT NEW com.cg.domain.entity.relationship.Relationship (" +
            "r.id, " +
            "r.sender," +
            "r.recipient, " +
            "r.relationshipType " +
            ") " +
            "FROM Relationship AS r " +
            "WHERE (r.sender.id = :userId OR r.recipient.id = :userId) " +
            "AND r.relationshipType.id = 1 " +
            "AND r.deleted = false "
    )
    List<Relationship> getConnectionForUser(@Param("userId") String userId);

    @Query(value = "SELECT NEW com.cg.domain.entity.relationship.Relationship (" +
            "r.id, " +
            "r.sender," +
            "r.recipient, " +
            "r.relationshipType " +
            ") " +
            "FROM Relationship AS r " +
            "WHERE r.recipient.id = :userId " +
            "AND r.relationshipType.id = 2"
    )
    List<Relationship> getFollowOfUser(@Param("userId") String userId);


    @Query(value = "CALL sp_get_all_non_friend_of_user_by_id(:userId);", nativeQuery = true)
    List<IUserDTO> getNonFriendOfUser(@Param("userId") String userId);


    @Query("SELECT NEW com.cg.domain.dto.userDTO.UserDTO (" +
            "u.id," +
            "ud.fullName," +
            "ud.dob," +
            "ud.avatarFileFolder," +
            "ud.avatarFileName," +
            "ud.avatarFileUrl," +
            "u.email" +
            ") " +
            "FROM User  AS u " +
            "JOIN UserDetail  AS ud " +
            "ON ud.user.id = u.id " +
            "WHERE u.id NOT IN(:listFriend)"
    )
    List<UserDTO> getUserNonFriend(@Param("listFriend") String listFriend);


    @Query(value =
            "SELECT (" +
                    "COUNT (r.id)" +
                    ") " +
                    "FROM Relationship AS r " +
                    "WHERE (r.recipient.id = :userId OR r.sender.id = :userId) " +
                    "AND r.relationshipType.id = 1 "
    )
    Long countFriend(@Param("userId") String userId);


    @Query(value =
            "SELECT (" +
                    "COUNT (r.id)" +
                    ") " +
                    "FROM Relationship AS r " +
                    "WHERE r.recipient.id = :userId " +
                    "AND r.relationshipType.id = 2 "
    )
    Long countFollowers(@Param("userId") String userId);


    @Query(value =
            "SELECT (" +
                    "COUNT (r.id)" +
                    ") " +
                    "FROM Relationship AS r " +
                    "WHERE r.sender.id = :userId " +
                    "AND r.relationshipType.id = 2 "
    )
    Long countFollowing(@Param("userId") String userId);


    @Query("SELECT NEW com.cg.domain.entity.relationship.Relationship (" +
            "r.id, " +
            "r.sender, " +
            "r.recipient, " +
            "r.relationshipType" +
            ") " +
            "FROM Relationship AS r " +
            "WHERE r.sender.id= :senderId " +
            "AND r.recipient.id = :recipientId " +
            "AND r.relationshipType.id = 2 " +
            "AND r.deleted = false "
    )
    Relationship findRelationshipBySenderIdAndRecipientIdOfUser(String senderId, String recipientId);

    @Query("SELECT NEW com.cg.domain.entity.relationship.Relationship (" +
            "r.id, " +
            "r.sender, " +
            "r.recipient, " +
            "r.relationshipType" +
            ") " +
            "FROM Relationship AS r " +
            "WHERE ((r.sender.id= :myId " +
            "AND r.recipient.id = :userId) " +
            "OR (r.sender.id = :userId " +
            "AND r.recipient.id =:myId)) " +
            "AND r.relationshipType.id IN (1,2) " +
            "AND r.deleted = false "
    )
    Relationship checkFriend(@Param("myId") String myId, @Param("userId") String userId);
}
