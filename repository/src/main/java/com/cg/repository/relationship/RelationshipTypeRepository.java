package com.cg.repository.relationship;

import com.cg.domain.entity.relationship.RelationshipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipTypeRepository extends JpaRepository<RelationshipType, Long> {
}
