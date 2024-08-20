package com.cg.domain.entity.media;

import com.cg.domain.dto.mediaDTO.MediaPostDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "media_post", indexes = @Index(columnList = "ts"))
public class MediaPost extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_folder")
    private String fileFolder;
    @Column(name = "file_url")
    private String fileUrl;

    @Column(columnDefinition = "TEXT")
    private String caption;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    private int width;
    private int height;

    public MediaPost(String id, String fileName, String fileFolder, String fileUrl, String caption, Post post) {
        this.id = id;
        this.fileName = fileName;
        this.fileFolder = fileFolder;
        this.fileUrl = fileUrl;
        this.caption = caption;
        this.post = post;
    }

    public MediaPostDTO toMediaPostDTO() {
        return new MediaPostDTO()
                .setId(id)
                .setFileName(fileName)
                .setFileFolder(fileFolder)
                .setFileUrl(fileUrl)
                .setCaption(caption)
                .setPostCreateDTO(post.postCreateDTO());
    }
}
