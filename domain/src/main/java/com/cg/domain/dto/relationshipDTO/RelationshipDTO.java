package com.cg.domain.dto.relationshipDTO;

import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.relationship.Relationship;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipDTO {
    private String   id;

    private UserDTO sender;

    private UserDTO recipient;

    private RelationshipTypeDTO relationshipTypeDTO;

    public Relationship toRelationship(){
        return new Relationship()
                .setId(id)
                .setSender(sender.toUser())
                .setRecipient(recipient.toUser())
                .setRelationshipType(relationshipTypeDTO.toRelationshipType());
    }
}
