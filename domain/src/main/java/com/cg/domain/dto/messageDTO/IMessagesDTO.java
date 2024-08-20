package com.cg.domain.dto.messageDTO;

import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.crypto.Data;
import java.util.Date;

public interface IMessagesDTO {
    String getId();

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date getCreateAt();

    Long getTs();
    String getContent();
    String getRecipient();
    String getRoomChatId();
    String getRoomName();
    String getSenderId();
    String getSenderName();
    String getAvatarUrl();
    String getFileFolder();
    String getFileName();
    String getType();

    boolean getSeen();
    String getRecipientFileFolder();
    String getRecipientFileName();
    String getRecipientFileUrl();
    String getRecipientId();
    String getRecipientName();
    String getSenderFileFolder();
    String getSenderFileName();
    String getSenderFileUrl();
}
