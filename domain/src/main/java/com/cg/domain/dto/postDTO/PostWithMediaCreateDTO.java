package com.cg.domain.dto.postDTO;

import com.cg.domain.enums.EPostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostWithMediaCreateDTO {

    private String id;
    private String content;
    private String urlMedia;
    private String userId;

    private EPostType postType;


    @Override
    public String toString() {
        return "PostWithMediaCreateDTO{" +
                "content='" + content + '\'' +
                ", urlMedia='" + urlMedia + '\'' +
                ", userId='" + userId + '\'' +
                ", postType='" + postType + '\'' +
                '}';
    }

    public PostCreateDTO toPostCreateDTO(){
        return (PostCreateDTO) new PostCreateDTO()
                .setId(id)
                .setContent(content)
                .setFileUrl(urlMedia)
                .setCreatedBy(userId);
    }
}
