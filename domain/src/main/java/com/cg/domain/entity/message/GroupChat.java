package com.cg.domain.entity.message;

import com.cg.domain.dto.messageDTO.GroupChatDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups_chat", indexes = @Index(columnList = "ts"))
public class GroupChat extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @OneToMany(mappedBy = "groupChat")
    List<GroupMember> groupMembers;


    public GroupChatDTO toGroupChatDTO(){
        return new GroupChatDTO()
                .setId(id)
                .setGroupName(groupName)
                .setOwner(owner.toUserDTO());

    }
}
