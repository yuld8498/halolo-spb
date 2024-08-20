package com.cg.service.comment;

import com.cg.domain.dto.commentDTO.CommentDTO;
import com.cg.domain.dto.commentDTO.ICommentDTO;
import com.cg.domain.entity.comment.Comment;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ICommentService extends IGeneralService<Comment> {
    List<CommentDTO> findCommentByPostId(String postId);

    CommentDTO createCommentByUser(String post,String comment);
}
