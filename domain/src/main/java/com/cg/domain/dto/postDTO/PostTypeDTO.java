package com.cg.domain.dto.postDTO;

import com.cg.domain.entity.post.PostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PostTypeDTO {
    private Long id;
    private String postType;

    public PostType toPostType(){
        return new PostType()
                .setId(id)
                .setPostType(postType);
    }
}
