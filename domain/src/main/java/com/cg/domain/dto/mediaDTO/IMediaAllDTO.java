package com.cg.domain.dto.mediaDTO;

import java.util.Date;

public interface IMediaAllDTO {

    String getId();
    Date getCreatedAt();
    String getFileUrl();
    String getFileFolder();
    String getFileName();
    int getWidth();
    int getHeight();
    Long getTs();
    String getPostId();
    String getUserId();
}
