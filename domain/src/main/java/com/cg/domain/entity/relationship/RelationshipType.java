package com.cg.domain.entity.relationship;

import com.cg.domain.dto.relationshipDTO.RelationshipDTO;
import com.cg.domain.dto.relationshipDTO.RelationshipTypeDTO;
import com.cg.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "relationship_type")
public class RelationshipType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "relationship_type")
    private String type;

    public RelationshipTypeDTO toRelationshipTypeDTO(){
        return new RelationshipTypeDTO()
                .setId(id)
                .setType(type);
    }
}
