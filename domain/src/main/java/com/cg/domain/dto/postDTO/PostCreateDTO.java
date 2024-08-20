package com.cg.domain.dto.postDTO;

import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.media.MediaPost;
import com.cg.domain.entity.post.Post;
import com.cg.domain.entity.post.PostType;
import com.cg.domain.enums.EPostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDTO extends BaseEntity {
    private String id;
    private String content;

    private String fileName;

    private String fileFolder;

    private String fileUrl;
    private UserDTO userDTO;

    private String filePostId;
    private EPostType postType;

    private MultipartFile file;

    private String fileType;


    public Post toPost() {
        return new Post()
                .setId(id)
                .setContent(content)
                .setUser(userDTO.toUser())
                .setPostType(postType);
    }

    public MediaPost toMediaPost() {
        return new MediaPost()
                .setId(filePostId)
                .setCaption(content)
                .setFileName(fileName)
                .setFileFolder(fileFolder)
                .setFileUrl(fileUrl);
    }

    @Override
    public String toString() {
        return "PostCreateDTO{" + "id='" + id + '\'' +

                ", content='" + content + '\'' + ", urlMedia='" + fileUrl + '\'' + ", userDTO=" + userDTO + '}';
    }
}
