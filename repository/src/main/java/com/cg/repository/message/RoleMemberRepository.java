package com.cg.repository.message;

import com.cg.domain.entity.message.RoleMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMemberRepository extends JpaRepository<RoleMember, Long> {
}
