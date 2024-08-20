package com.cg.domain.dto.commentDTO;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public interface ICommentDTO {
    String getId();

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date getCreatedAt();

    String getCreatedBy();
    int getDeleted();
    Long getTs();

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date getUpdatedAt();

    String getUpdatedBy();
    String getContent();
    String getPostId();
    String getAvatarUrl();
    String getCommentId();
    String getFullName();
    String getEmail();
    String getFileFolder();
    String getFileUrl();
}
