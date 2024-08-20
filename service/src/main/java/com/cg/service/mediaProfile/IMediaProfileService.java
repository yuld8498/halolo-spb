package com.cg.service.mediaProfile;

import com.cg.domain.dto.mediaDTO.MediaProfileDTO;
import com.cg.domain.entity.media.MediaProfile;
import com.cg.domain.entity.post.Post;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IMediaProfileService extends IGeneralService<MediaProfile> {
    List<MediaProfile> findMediaProfilesByUserId(String userId);

    List<MediaProfileDTO> getAllMediaProfileByUserId(String userId);

    Optional<MediaProfile> findByPost(Post post);

    MediaProfileDTO createMediaProfile (MediaProfileDTO mediaProfileDTO);
}
