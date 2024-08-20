package com.cg.service.ReactionType;

import com.cg.domain.dto.postDTO.ReactionTypeDTO;
import com.cg.domain.entity.post.ReactionType;
import com.cg.repository.post.ReactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReactionTypeImp implements IReactionTypeService{
    @Autowired
    private ReactionTypeRepository reactionTypeRepository;
    @Override
    public List<ReactionType> findAll() {
        return reactionTypeRepository.findAll();
    }

    @Override
    public Optional<ReactionType> findById(Long id) {
        return reactionTypeRepository.findById(id);
    }

    @Override
    public Optional<ReactionType> findById(String id) {
        return Optional.empty();
    }

    @Override
    public ReactionType save(ReactionType reactionType) {
        return reactionTypeRepository.save(reactionType);
    }

    @Override
    public void remove(Long id) {
        Optional<ReactionType> reactionTypeOptional = reactionTypeRepository.findById(id);
        if (reactionTypeOptional.isPresent()){
            ReactionType reactionType = reactionTypeOptional.get();
            reactionType.setDeleted(true);
            reactionTypeRepository.save(reactionType);
        }
    }

    @Override
    public void remove(String id) {

    }

    @Override
    public List<ReactionTypeDTO> findAllReactionTypeDTO() {
        return reactionTypeRepository.findAllReactionTypeDTO();
    }
}
