package com.cg.domain.entity.media;

import com.cg.domain.dto.mediaDTO.MediaProfileDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "media_profile", indexes = @Index(columnList = "ts"))
public class MediaProfile extends BaseEntity {
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

    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean used;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    private int width;
    private int height;
    public MediaProfile(String id, String fileName, String fileFolder, String fileUrl, String caption, boolean used, Post post) {
        this.id = id;
        this.fileName = fileName;
        this.fileFolder = fileFolder;
        this.fileUrl = fileUrl;
        this.caption = caption;
        this.used = used;
        this.post = post;
    }

    public MediaProfileDTO toMediaProfileDTO() {
        return new MediaProfileDTO()
                .setId(id)
                .setCaption(caption)
                .setWidth(width)
                .setHeight(height)
                .setFileFolder(fileFolder)
                .setFileName(fileName)
                .setFileUrl(fileUrl)
                .setPostCreateDTO(post.postCreateDTO());
    }
}
