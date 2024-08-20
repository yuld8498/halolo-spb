package com.cg.service.ReactionType;

import com.cg.domain.dto.postDTO.ReactionTypeDTO;
import com.cg.domain.entity.post.ReactionType;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IReactionTypeService extends IGeneralService<ReactionType> {
    List<ReactionTypeDTO> findAllReactionTypeDTO();
}
