package com.cg.domain.dto.mediaDTO;

import java.util.Date;

public interface IMediaDTO {
    String getId();
    Date getCreatedAt();
    String getContent();
    String getUrlMedia();
    String getType();
    Long getTs();
    String getPostId();
}
