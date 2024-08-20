package com.cg.domain.dto.mediaDTO;

import com.cg.domain.dto.postDTO.PostCreateDTO;
import com.cg.domain.dto.postDTO.PostDTO;
import com.cg.domain.entity.media.MediaCover;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaCoverDTO {

    private String id;

    private String fileFolder;

    private String fileName;

    private String fileUrl;
    private String caption;
    private PostCreateDTO postCreateDTO;

    private int width;
    private int height;

    public MediaCoverDTO(String id, String fileFolder, String fileName, String fileUrl, String caption, PostCreateDTO postCreateDTO) {
        this.id = id;
        this.fileFolder = fileFolder;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.caption = caption;
        this.postCreateDTO = postCreateDTO;
    }

    public MediaCover toMediaCover(){
        return new MediaCover()
                .setId(id)
                .setFileName(fileName)
                .setFileFolder(fileFolder)
                .setFileUrl(fileUrl)
                .setCaption(caption)
                .setPost(postCreateDTO.toPost());
    }
    public MediaCoverDTO(String id, String fileFolder, String fileName, String fileUrl, String caption) {
        this.id = id;
        this.fileFolder = fileFolder;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.caption = caption;
    }

}
