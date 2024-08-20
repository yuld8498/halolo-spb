package com.cg.repository.message;

import com.cg.domain.dto.messageDTO.IMessagesDTO;
import com.cg.domain.entity.message.RoomChat;
import com.cg.domain.entity.message.UserMessage;
import com.cg.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChatRepository extends JpaRepository<UserMessage, String> {
    @Query(value = "call sp_find_all_last_messages_of_user_by_id(:userId);", nativeQuery = true)
    List<IMessagesDTO> findAllLastMessageOfUser(@Param("userId")String userId);

    @Query(value = "call sp_get_all_messages_of_room_by_id(:roomChatId);", nativeQuery = true)
    List<IMessagesDTO> getAllMessagesDtoOfRoomById(@Param("roomChatId")String roomChatId);

    List<UserMessage> findAllByRoomChatIdOrderByTsAsc(String roomId);

    @Query("SELECT NEW com.cg.domain.entity.message.UserMessage (" +
            "um.id," +
            "um.content," +
            "um.seen," +
            "um.sender," +
            "um.recipient," +
            "um.roomChat) " +
            "FROM UserMessage  AS um " +
            "WHERE um.roomChat.id = :roomId " +
            "AND um.ts > :ts")
    List<UserMessage> findMessagesRoomLimit(@Param("roomId")String roomId,@Param("ts")String ts);

}
