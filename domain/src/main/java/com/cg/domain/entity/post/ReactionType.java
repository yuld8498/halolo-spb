package com.cg.domain.entity.post;

import com.cg.domain.dto.postDTO.ReactionTypeDTO;
import com.cg.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reactions_type")
public class ReactionType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_reaction")

    private String reactionType;

    public ReactionTypeDTO toReactionTypeDTO(){
        return new ReactionTypeDTO()
                .setId(id)
                .setReactionType(reactionType);
    }
}
