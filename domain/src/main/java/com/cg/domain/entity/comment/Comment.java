package com.cg.domain.entity.comment;

import com.cg.domain.dto.commentDTO.CommentDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments", indexes = @Index(columnList = "ts"))
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;


    public CommentDTO toCommentDTO() {
        return new CommentDTO()
                .setId(id)
                .setContent(content)
                .setPostCreateDTO(post.postCreateDTO());
    }
}

