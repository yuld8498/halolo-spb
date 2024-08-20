package com.cg.repository.user;

import com.cg.domain.entity.user.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, String> {
    UserDetail findByUserId(String userId);


}
