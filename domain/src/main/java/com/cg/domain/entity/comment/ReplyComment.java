package com.cg.domain.entity.comment;


import com.cg.domain.dto.commentDTO.ReplyCommentDTO;
import com.cg.domain.entity.BaseEntity;
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
@Table(name = "reply_comments", indexes = @Index(columnList = "ts"))
public class ReplyComment extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment comment;

    public ReplyCommentDTO toReplyCommentDTO() {
        return new ReplyCommentDTO()
                .setId(id)
                .setContent(content)
                .setCommentDTO(comment.toCommentDTO());
    }
}

