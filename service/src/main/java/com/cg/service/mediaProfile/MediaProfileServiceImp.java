package com.cg.service.mediaProfile;

import com.cg.domain.dto.mediaDTO.MediaProfileDTO;
import com.cg.domain.entity.media.MediaProfile;
import com.cg.domain.entity.post.Post;
import com.cg.repository.media.MediaProfileRepository;
import com.cg.repository.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MediaProfileServiceImp implements IMediaProfileService {
    @Autowired
    private MediaProfileRepository mediaProfileRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<MediaProfile> findAll() {
        return mediaProfileRepository.findAll();
    }

    @Override
    public Optional<MediaProfile> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<MediaProfile> findById(String id) {
        return mediaProfileRepository.findById(id);
    }

    @Override
    public MediaProfile save(MediaProfile mediaProfile) {
        return mediaProfileRepository.save(mediaProfile);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {
        Optional<MediaProfile> mediaProfileOptional = mediaProfileRepository.findById(id);
        if (mediaProfileOptional.isPresent()) {
            MediaProfile mediaProfile = mediaProfileOptional.get();
            mediaProfile.setDeleted(true);
            mediaProfileRepository.save(mediaProfile);
        }
    }

    @Override
    public List<MediaProfile> findMediaProfilesByUserId(String userId) {
        return mediaProfileRepository.findMediaProfilesByUserId(userId);
    }

    @Override
    public List<MediaProfileDTO> getAllMediaProfileByUserId(String userId) {

        List<MediaProfile> mediaProfiles = mediaProfileRepository.findMediaProfilesByUserId(userId);
        List<MediaProfileDTO> mediaProfileDTOS = new ArrayList<>();

        for (MediaProfile mediaProfile : mediaProfiles) {
            mediaProfileDTOS.add(mediaProfile.toMediaProfileDTO());
        }

        return mediaProfileDTOS;
    }

    @Override
    public Optional<MediaProfile> findByPost(Post post) {
        return mediaProfileRepository.findByPost(post);
    }

    @Override
    public MediaProfileDTO createMediaProfile(MediaProfileDTO mediaProfileDTO) {
        Post post = new Post();
        post.setUser(mediaProfileDTO.getPostCreateDTO().toPost().getUser());
        Post newPost = postRepository.save(post);

        MediaProfile mediaProfile = mediaProfileDTO.toMediaProfile();
        mediaProfile.setPost(newPost);
        mediaProfile.setUsed(true);
        MediaProfile newMediaProfile = mediaProfileRepository.save(mediaProfile);

        return newMediaProfile.toMediaProfileDTO();
    }
}
