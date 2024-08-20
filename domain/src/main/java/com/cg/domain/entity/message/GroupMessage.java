package com.cg.domain.entity.message;


import com.cg.domain.dto.messageDTO.GroupMessageDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.user.User;
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
@Table(name = "group_messages", indexes = @Index(columnList = "ts"))
public class GroupMessage extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(columnDefinition = "TEXT")
    private String message;

    private boolean seen;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_chat_id", referencedColumnName = "id")
    private GroupChat groupChat;

    public GroupMessage(String id, String message, User user, GroupChat groupChat) {
        this.id = id;
        this.message = message;
        this.user = user;
        this.groupChat = groupChat;
    }

    public GroupMessageDTO toGroupMessageDTO(){
        return new GroupMessageDTO()
                .setId(id)
                .setMessage(message)
                .setUserDTO(user.toUserDTO())
                .setGroupChatDTO(groupChat.toGroupChatDTO());
    }
}

