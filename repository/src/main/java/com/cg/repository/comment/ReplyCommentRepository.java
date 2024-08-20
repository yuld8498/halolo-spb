package com.cg.repository.comment;

import com.cg.domain.dto.commentDTO.ICommentDTO;
import com.cg.domain.entity.comment.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyCommentRepository extends JpaRepository<ReplyComment, String> {

    @Query(value = "" +
            "SELECT " +
                "cmt.id, " +
                "cmt.created_at AS createdAt, " +
                "cmt.created_by AS createdBy, " +
                "cmt.deleted, " +
                "cmt.ts, " +
                "cmt.updated_at AS updatedAt, " +
                "cmt.updated_by AS updatedBy, " +
                "cmt.content, " +
                "cmt.comment_id AS postId, " +
                "u.cover_url AS avatarUrl, " +
                "u.full_name AS fullName " +
            "FROM reply_comments AS cmt " +
            "JOIN users AS u " +
            "ON u.id = cmt.created_by " +
            "Where cmt.comment_id = :replyCommentId "
            , nativeQuery = true
    )
    List<ICommentDTO> findAllReplyCommentByCommentId(@Param("replyCommentId") String replyCommentId);
}
