package com.cg.domain.dto.postDTO;

import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.post.Reaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDTO {
    private String id;

    private PostCreateDTO postCreateDTO;

    private ReactionTypeDTO reactionTypeDTO;

    private UserDTO userDTO;

    public Reaction toReaction(){
        return new Reaction()
                .setId(id)
                .setPost(postCreateDTO.toPost())
                .setReactionType(reactionTypeDTO.toReactionType())
                .setUser(userDTO.toUser());
    }
}
