package com.cg.service.RelationshipType;

import com.cg.domain.entity.relationship.RelationshipType;
import com.cg.repository.relationship.RelationshipTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RelationshipTypeImp implements IRelationshipTypeService{
    @Autowired
    private RelationshipTypeRepository relationshipTypeRepository;
    @Override
    public List<RelationshipType> findAll() {
        return relationshipTypeRepository.findAll();
    }

    @Override
    public Optional<RelationshipType> findById(Long id) {
        return relationshipTypeRepository.findById(id);
    }

    @Override
    public Optional<RelationshipType> findById(String id) {
        return Optional.empty();
    }


    @Override
    public RelationshipType save(RelationshipType relationshipType) {
        return relationshipTypeRepository.save(relationshipType);
    }

    @Override
    public void remove(Long id) {
        Optional<RelationshipType> relationshipType = relationshipTypeRepository.findById(id);
        if (relationshipType.isPresent()){
            RelationshipType newRls = relationshipType.get();
            newRls.setDeleted(true);
            relationshipTypeRepository.save(newRls);
        }
    }

    @Override
    public void remove(String id) {

    }
}
