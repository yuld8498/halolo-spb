package com.cg.repository.comment;

import com.cg.domain.dto.commentDTO.ICommentDTO;
import com.cg.domain.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {


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
                "cmt.post_id AS postId, " +
                "ud.avatar_file_name AS avatarUrl," +
                "ud.avatar_file_folder AS fileFolder," +
                "ud.avatar_file_url AS fileUrl," +
                "ud.full_name AS fullName, " +
                "u.email AS email " +
            "FROM comments AS cmt " +
            "JOIN users AS u " +
            "ON u.id = cmt.created_by " +
            "JOIN user_details AS ud " +
            "ON ud.user_id = cmt.created_by " +
            "Where cmt.post_id = :postId " +
            "ORDER BY cmt.ts DESC "
            , nativeQuery = true
    )
    List<ICommentDTO> findAllByPostId(@Param("postId") String postId);
}
