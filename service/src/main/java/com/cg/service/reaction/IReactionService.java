package com.cg.service.reaction;

import com.cg.domain.entity.post.Reaction;
import com.cg.service.IGeneralService;

import java.util.Optional;

public interface IReactionService extends IGeneralService<Reaction> {
    Optional<Reaction> findByPostIdAndCreatedById(String postId, String id);
    void addReactionForPost( String postId);

    Optional<Reaction> getByUserIdAndPostId(String postId);
}
