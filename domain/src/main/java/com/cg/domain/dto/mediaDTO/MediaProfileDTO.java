package com.cg.domain.dto.mediaDTO;

import com.cg.domain.dto.postDTO.PostCreateDTO;
import com.cg.domain.dto.postDTO.PostDTO;
import com.cg.domain.entity.media.MediaPost;
import com.cg.domain.entity.media.MediaProfile;
import com.cg.domain.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaProfileDTO {

    private String id;

    private String caption;


    private String fileFolder;
    private String fileName;
    private String fileUrl;

    private PostCreateDTO postCreateDTO;

    private int width;

    private int height;

    public MediaProfileDTO(String id, String caption, String fileFolder, String fileName, String fileUrl, PostCreateDTO postCreateDTO) {
        this.id = id;
        this.caption = caption;
        this.fileFolder = fileFolder;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.postCreateDTO = postCreateDTO;
    }

    public MediaProfile toMediaProfile(){
        return new MediaProfile()
                .setId(id)
                .setFileUrl(fileUrl)
                .setWidth(width)
                .setHeight(height)
                .setCaption(caption)
                .setPost(postCreateDTO.toPost());
    }
}
