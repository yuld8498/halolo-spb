package com.cg.domain.entity.message;

import com.cg.domain.dto.messageDTO.RoomChatDTO;
import com.cg.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms_chat", indexes = @Index(columnList = "ts"))
public class RoomChat extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "room_name")
    private String name;

    @OneToMany(mappedBy = "roomChat")
    private List<RoomMember> roomMembers;

    public RoomChatDTO toRoomChatDTO(){
        return new RoomChatDTO()
                .setId(id)
                .setName(name);
    }

    public RoomChat(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
