package com.cg.domain.dto.commentDTO;

import com.cg.domain.dto.postDTO.PostCreateDTO;
import com.cg.domain.dto.postDTO.PostDTO;
import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.comment.Comment;
import com.cg.domain.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private String id;

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    private String content;

    private PostCreateDTO postCreateDTO;

    private UserDTO userDTO;

    public Comment toComment(){
        return new Comment()
                .setId(id)
                .setContent(content)
                .setPost(postCreateDTO.toPost());
    }
}
