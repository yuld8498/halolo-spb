package com.cg.domain.dto.postDTO;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public interface IPostDTO {
    String getId();

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date getCreatedAt();

    String getCreatedBy();

    String getEmail();

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date getUpdatedAt();

    String getUpdatedBy();

    boolean isDeleted();

    Long getTs();

    String getContents();

    String getPostType();

    String getUrlMedia();
    String getFileFolder();
    String getFileName();
    int getWidth();

    int getHeight();
//    String getMediaId();

    String getUserId();

    String getUsed();

    String getAvatarName();
    String getAvatarFolder();
    String getAvatarUrl();



    String getFullName();

    Long getCountReactions();

    boolean isLiked();

//    UserDTO toUserDTO();
//
//    PostTypeDTO toPostTypeDTO();
//
//    PostDTO toPostDTO();
}
