package com.cg.domain.entity.message;

import com.cg.domain.dto.messageDTO.GroupMemberDTO;
import com.cg.domain.dto.messageDTO.RoomMemberDTO;
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
@Table(name = "room_members", indexes = @Index(columnList = "ts"))
public class RoomMember extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;
    @Column(name = "nick_name")
    private String nickName;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private User member;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    private RoomChat roomChat;

    public RoomMemberDTO toRoomMemberDTO(){
        return new RoomMemberDTO()
                .setId(id)
                .setNickName(nickName)
                .setMemberDTO(member.toUserDTO())
                .setRoomChatDTO(roomChat.toRoomChatDTO());
    }
}