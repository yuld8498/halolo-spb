package com.cg.domain.dto.mediaDTO;

import com.cg.domain.dto.postDTO.PostCreateDTO;
import com.cg.domain.dto.postDTO.PostDTO;
import com.cg.domain.entity.media.MediaCover;
import com.cg.domain.entity.media.MediaPost;
import com.cg.domain.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaPostDTO {

    private String id;

    private String fileName;

    private String fileFolder;

    private String fileUrl;

    private String caption;

    private PostCreateDTO postCreateDTO;

    private int width;
    private int height;

    public MediaPostDTO(String id, String fileName, String fileFolder, String fileUrl, String caption, int width, int height) {
        this.id = id;
        this.fileName = fileName;
        this.fileFolder = fileFolder;
        this.fileUrl = fileUrl;
        this.caption = caption;
        this.width = width;
        this.height = height;
    }

    public MediaPostDTO(String id, String fileName, String fileFolder, String fileUrl, String caption, PostCreateDTO postCreateDTO) {
        this.id = id;
        this.fileName = fileName;
        this.fileFolder = fileFolder;
        this.fileUrl = fileUrl;
        this.caption = caption;
        this.postCreateDTO = postCreateDTO;
    }

    public MediaPost toMediaPost(){
        return new MediaPost()
                .setId(id)
                .setFileName(fileName)
                .setFileFolder(fileFolder)
                .setFileUrl(fileUrl)
                .setCaption(caption)
                .setPost(postCreateDTO.toPost());
    }
    public MediaPostDTO(String id, String fileUrl, String caption){
        this.id=id;
        this.fileUrl=fileUrl;
        this.caption=caption;
    }

    public MediaPostDTO(String id, String fileFolder, String fileName, String fileUrl, String caption) {
        this.id = id;
        this.fileFolder = fileFolder;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.caption = caption;
    }
}
