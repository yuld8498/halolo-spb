package com.cg.domain.entity.message;

import com.cg.domain.dto.messageDTO.GroupMemberDTO;
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
@Table(name = "group_members", indexes = @Index(columnList = "ts"))
public class GroupMember extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;
    @Column(name = "nick_name")
    private String nickName;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleMember roleMember;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private User member;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private GroupChat groupChat;

    public GroupMemberDTO toGroupMemberDTO(){
        return new GroupMemberDTO()
                .setId(id)
                .setNickName(nickName)
                .setRoleMemberDTO(roleMember.toRoleMemberDTO())
                .setMemberDTO(member.toUserDTO())
                .setGroupChatDTO(groupChat.toGroupChatDTO());
    }
}
