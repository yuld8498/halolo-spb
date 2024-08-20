package com.cg.domain.entity.relationship;

import com.cg.domain.dto.relationshipDTO.RelationshipDTO;
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
@Table(name = "relationships")
public class Relationship extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String   id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @ManyToOne
    @JoinColumn(name = "relationship_type_id", referencedColumnName = "id")
    private RelationshipType relationshipType;

    public RelationshipDTO toRelationshipDTO(){
        return new RelationshipDTO()
                .setId(id)
                .setSender(sender.toUserDTO())
                .setRecipient(recipient.toUserDTO())
                .setRelationshipTypeDTO(relationshipType.toRelationshipTypeDTO());
    }
}
