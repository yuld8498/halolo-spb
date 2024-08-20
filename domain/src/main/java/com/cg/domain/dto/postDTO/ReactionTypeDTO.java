package com.cg.domain.dto.postDTO;

import com.cg.domain.entity.post.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReactionTypeDTO {
    private Long id;

    private String reactionType;

    public ReactionType toReactionType(){
        return new ReactionType()
                .setId(id)
                .setReactionType(reactionType);
    }
}
