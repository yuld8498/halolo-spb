package com.cg.repository.message;

import com.cg.domain.entity.message.GroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMessageRepository extends JpaRepository<GroupMessage, String> {
    List<GroupMessage> findAllByGroupChatIdOrderByTsAsc(String groupId);
}
