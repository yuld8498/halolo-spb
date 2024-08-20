package com.cg.repository.post;

import com.cg.domain.entity.post.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, String> {

//    @Query("SELECT new com.cg.domain.dto.postDTO.ReactionDTO " +
//            "(r.id, r.post, r.reactionType, r.user) from Reaction AS r " +
//            "WHERE (r.deleted = true and r.post = :postId)")
//    List<ReactionDTO> findAllReactionDTO(@Param("postId") String postId);

    @Modifying
    @Query("UPDATE Reaction AS r " +
            "SET r.deleted = true " +
            "WHERE r.createdBy = :id " +
            "AND r.post.id = :postId"
    )
    Optional<Reaction> findReactionByPostIdAndCreatedById(@Param("id") String id, @Param("postId") String postId);

    Optional<Reaction> findByUserIdAndPostIdAndDeletedIsFalse(String userId, String postId);
}
