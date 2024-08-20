package com.cg.service.replyComment;

import com.cg.domain.dto.commentDTO.ICommentDTO;
import com.cg.domain.dto.commentDTO.ReplyCommentDTO;
import com.cg.domain.entity.comment.ReplyComment;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IReplyCommentService extends IGeneralService<ReplyComment> {
    List<ReplyCommentDTO> findAllReplyCommentByCommentId(String commentId);
    ReplyCommentDTO replyCommentByUser(String cmtId, String content);
}
