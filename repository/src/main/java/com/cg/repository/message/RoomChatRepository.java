package com.cg.repository.message;

import com.cg.domain.dto.messageDTO.RoomChatDTO;
import com.cg.domain.entity.message.IRoomChat;
import com.cg.domain.entity.message.RoomChat;
import com.cg.domain.entity.message.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomChatRepository extends JpaRepository<RoomChat, String> {


    @Query("SELECT NEW com.cg.domain.dto.messageDTO.RoomChatDTO (" +
                "rc.id, " +
                "rc.name " + ") " +
            "FROM RoomChat  AS rc " +
            "WHERE rc.roomMembers IN :roomMembers "
    )
    Optional<RoomChatDTO> getRoomChatByRoomMembers( @Param("roomMembers") List<RoomMember> roomMembers);

    @Query(value = "SELECT rm.id, rm.room_name AS name FROM rooms_chat AS rm " +
            "JOIN (SELECT rm.room_id AS id FROM room_members AS rm " +
            "WHERE rm.member_id =:senderId) AS t " +
            "ON rm.id = t.id " +
            "JOIN (SELECT rc.room_id AS id from room_members AS rc " +
            "WHERE rc.member_id =:recipientId) AS u " +
            "ON rm.id = u.id" , nativeQuery = true)
    IRoomChat findRoomChatBySenderIdAndRecipientId(@Param("senderId")String senderId, @Param("recipientId")String recipientId);

    @Query("SELECT NEW com.cg.domain.dto.messageDTO.RoomChatDTO (" +
            "r.id," +
            "r.name) " +
            "FROM RoomChat  AS r " +
            "JOIN UserMessage  as u " +
            "ON (u.roomChat = r.id AND u.sender.id = :senderId AND u.recipient.id =: recipientId) " +
            "OR (u.roomChat = r.id AND u.sender.id = :recipientId AND u.recipient.id =: senderId) " +
            "GROUP BY r.id")
    RoomChatDTO findRoomChatDTOBySenderAndRecipient(@Param("senderId") String senderId,
                                                    @Param("recipientId") String recipientId);

}
