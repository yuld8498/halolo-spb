package com.cg.repository.message;

import com.cg.domain.dto.messageDTO.GroupChatDTO;
import com.cg.domain.dto.messageDTO.IGroupMessages;
import com.cg.domain.entity.message.GroupChat;
import com.cg.domain.entity.message.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, String> {

    @Query("SELECT gc " +
            "FROM GroupChat AS gc " +
            "WHERE gc.id IN :groupMembers " +
            "AND gc.groupName =:groupName "
    )
    Optional<GroupChat> getGroupChatByGroupMembers(@Param("groupMembers")List<GroupMember> groupMembers,
                                                   @Param("groupName")String groupName);

    @Query(value =
            "call sp_get_group_by_user(:usersId, :count); " , nativeQuery = true )
    GroupChat existsGroupChatByListUser(@Param("usersId")String usersId, @Param("count")int count);

}
