package com.cg.service.mediaCover;


import com.cg.domain.dto.mediaDTO.MediaCoverDTO;
import com.cg.domain.dto.mediaDTO.MediaProfileDTO;
import com.cg.domain.entity.media.MediaCover;
import com.cg.domain.entity.post.Post;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IMediaCoverService extends IGeneralService<MediaCover> {

    List<MediaCover> findMediaCoversByUserId(String userId);

    List<MediaCoverDTO> getAllMediaCoverByUserId(String userId);

    Optional<MediaCover> findByPost(Post post);

    Optional<MediaCover> findByUserIdAndUsedIsFalse(String userId);

    MediaCoverDTO createMediaCover (MediaCoverDTO mediaCoverDTO);



}
