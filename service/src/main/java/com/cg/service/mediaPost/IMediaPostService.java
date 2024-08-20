package com.cg.service.mediaPost;

import com.cg.domain.dto.mediaDTO.MediaPostDTO;
import com.cg.domain.entity.media.MediaPost;
import com.cg.domain.entity.post.Post;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IMediaPostService extends IGeneralService<MediaPost> {
    List<MediaPostDTO> findMediaPostByUserId(String userId);
    List<MediaPost> findAll();

    Optional<MediaPost> findByPost(Post post);

    MediaPost create(MediaPost mediaPost);

    void delete(MediaPost mediaPost);
}
