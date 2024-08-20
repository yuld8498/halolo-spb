package com.cg.domain.dto.postDTO;

import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.post.Post;
import com.cg.domain.enums.EPostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO implements IPostDTO {
    private String id;
    private String userId;
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    private String createdBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    private String updatedBy;
    private boolean deleted;
    private Long ts;
    private String content;
//    private Long postTypeId;
    private String postType;
    private String type;
    private String urlMedia;
    private String fileFolder;
    private String fileName;
    private int width;
    private int height;

    private String avatarName;
    private String avatarFolder;
    private String avatarUrl;

    private String fullName;


    private boolean liked;

    private List<String> friendsList;
    private Long countReaction;

    public PostDTO(IPostDTO iPostDTO) {
        id = iPostDTO.getId();
        userId = iPostDTO.getUserId();
        email = iPostDTO.getEmail();
        createdAt = iPostDTO.getCreatedAt();
        createdBy = iPostDTO.getCreatedBy();
        updatedAt = iPostDTO.getUpdatedAt();
        updatedBy = iPostDTO.getUpdatedBy();
        deleted = iPostDTO.isDeleted();
        ts = iPostDTO.getTs();
        content = iPostDTO.getContents();
//        postTypeId = iPostDTO.getPostTypeId();
        postType = iPostDTO.getPostType();
//        type = iPostDTO.getType();
        urlMedia = iPostDTO.getUrlMedia();
        fileFolder = iPostDTO.getFileFolder();
        fileName = iPostDTO.getFileName();
        width = iPostDTO.getWidth();
        height = iPostDTO.getHeight();
        avatarName = iPostDTO.getAvatarName();
        avatarFolder = iPostDTO.getAvatarFolder();
        avatarUrl = iPostDTO.getAvatarUrl();
        fullName = iPostDTO.getFullName();
        liked = iPostDTO.isLiked();
        countReaction = iPostDTO.getCountReactions();
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String getContents() {
        return content;
    }

    @Override
    public String getPostType() {
        return postType;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getUsed() {
        return null;
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Override
    public String getFileFolder() {
        return fileFolder;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Long getCountReactions() {
        return countReaction;
    }

    @Override
    public boolean isLiked() {
        return liked;
    }

    public PostDTO(String id, String userId,
                   String email, Date createdAt,
                   String createdBy, Date updatedAt,
                   String updatedBy, boolean deleted,
                   Long ts, String content, String postType,
                   String type, String urlMedia, String avatarUrl,
                   String fullName, boolean liked, Long countReaction) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
        this.ts = ts;
        this.content = content;
        this.postType = postType;
        this.type = type;
        this.urlMedia = urlMedia;
        this.avatarUrl = avatarUrl;
        this.fullName = fullName;
        this.liked = liked;
        this.countReaction = countReaction;
    }

    public PostDTO(String id, String userId, String email, Date createdAt,
                   String createdBy, Date updatedAt, String updatedBy,
                   boolean deleted, Long ts, String content, String postType,
                   String type, String urlMedia, String avatarName, String avatarFolder,
                   String avatarUrl, String fullName, boolean liked, List<String> friendsList, Long countReaction) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
        this.ts = ts;
        this.content = content;
        this.postType = postType;
        this.type = type;
        this.urlMedia = urlMedia;
        this.avatarName = avatarName;
        this.avatarFolder = avatarFolder;
        this.avatarUrl = avatarUrl;
        this.fullName = fullName;
        this.liked = liked;
        this.friendsList = friendsList;
        this.countReaction = countReaction;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                ", updatedAt=" + updatedAt +
                ", updatedBy='" + updatedBy + '\'' +
                ", deleted=" + deleted +
                ", ts=" + ts +
                ", content='" + content + '\'' +
                ", postType='" + postType + '\'' +
                ", type='" + type + '\'' +
                ", urlMedia='" + urlMedia + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", fullName='" + fullName + '\'' +
                ", liked=" + liked +
                ", countReaction=" + countReaction +
                '}';
    }
}
