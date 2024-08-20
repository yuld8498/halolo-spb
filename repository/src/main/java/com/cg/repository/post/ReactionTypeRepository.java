package com.cg.repository.post;

import com.cg.domain.dto.postDTO.ReactionTypeDTO;
import com.cg.domain.entity.post.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionTypeRepository extends JpaRepository<ReactionType, Long> {
    @Query("SELECT NEW com.cg.domain.dto.postDTO.ReactionTypeDTO (" +
                "rt.id, " +
                "rt.reactionType " +
            ") " +
            "FROM ReactionType AS rt"
    )
    List<ReactionTypeDTO> findAllReactionTypeDTO();
}

