package com.cg.repository.user;

import com.cg.domain.entity.user.FriendList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendListRepository extends JpaRepository<FriendList, String> {

    FriendList findByUserId(String userId);
}
