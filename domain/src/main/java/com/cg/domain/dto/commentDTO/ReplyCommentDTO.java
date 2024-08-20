package com.cg.domain.dto.commentDTO;

import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.comment.Comment;
import com.cg.domain.entity.comment.ReplyComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyCommentDTO {
    private String id;

    private String content;

    private CommentDTO commentDTO;

    private List<ReplyComment> replyCommentList;

    private UserDTO userDTO;

    public ReplyComment toReplyComment(){
        return new ReplyComment()
                .setId(id)
                .setContent(content)
                .setComment(commentDTO.toComment());
    }
}
