package com.cg.domain.entity.post;

import com.cg.domain.dto.postDTO.ReactionDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.user.User;
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
@Table(name = "reactions", indexes = @Index(columnList = "ts"))
public class Reaction extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "reaction_type_id", referencedColumnName = "id")
    private ReactionType reactionType;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public ReactionDTO reactionDTO(){
        return new ReactionDTO()
                .setId(id)
                .setPostCreateDTO(post.postCreateDTO())
                .setReactionTypeDTO(reactionType.toReactionTypeDTO())
                .setUserDTO(user.toUserDTO());
    }
}
