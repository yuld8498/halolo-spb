package com.cg.domain.dto.relationshipDTO;

import com.cg.domain.entity.relationship.RelationshipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipTypeDTO {
    private Long id;

    private String type;

    public RelationshipType toRelationshipType(){
        return new RelationshipType()
                .setId(id)
                .setType(type);
    }
}
