package com.cg.service.mediaCover;


import com.cg.domain.dto.mediaDTO.MediaCoverDTO;
import com.cg.domain.dto.mediaDTO.MediaProfileDTO;
import com.cg.domain.entity.media.MediaCover;
import com.cg.domain.entity.media.MediaPost;
import com.cg.domain.entity.media.MediaProfile;
import com.cg.domain.entity.post.Post;
import com.cg.repository.media.MediaCoverRepository;
import com.cg.repository.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MediaCoverServiceImp implements IMediaCoverService{
    @Autowired
    private MediaCoverRepository mediaCoverRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<MediaCover> findAll() {
        return mediaCoverRepository.findAll();
    }

    @Override
    public Optional<MediaCover> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<MediaCover> findById(String id) {
        return mediaCoverRepository.findById(id);
    }

    @Override
    public Optional<MediaCover> findByPost(Post post) {
        return mediaCoverRepository.findByPost(post);
    }

    @Override
    public MediaCover save(MediaCover mediaCover) {
        return mediaCoverRepository.save(mediaCover);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {
        Optional<MediaCover> mediaCoverOptional = mediaCoverRepository.findById(id);
        if (mediaCoverOptional.isPresent()){
            MediaCover mediaCover = mediaCoverOptional.get();
            mediaCover.setDeleted(true);
            mediaCoverRepository.save(mediaCover);
        }
    }

    @Override
    public List<MediaCover> findMediaCoversByUserId(String userId) {
        return mediaCoverRepository.findMediaCoversByUserId(userId);
    }

    @Override
    public List<MediaCoverDTO> getAllMediaCoverByUserId(String userId) {
        List<MediaCover> mediaCovers = mediaCoverRepository.findMediaCoversByUserId(userId);
        List<MediaCoverDTO> mediaCoverDTOS = new ArrayList<>();

        for (MediaCover mediaCover : mediaCovers) {
            mediaCoverDTOS.add(mediaCover.toMediaCoverDTO());
        }

        return mediaCoverDTOS;
    }

    @Override
    public Optional<MediaCover> findByUserIdAndUsedIsFalse(String userId) {
        return mediaCoverRepository.findMediaCoverUsedByUserId(userId);
    }

    @Override
    public MediaCoverDTO createMediaCover(MediaCoverDTO mediaCoverDTO) {
        Post post = new Post();
        post.setUser(mediaCoverDTO.getPostCreateDTO().toPost().getUser());
        Post newPost = postRepository.save(post);

        MediaCover mediaCover = mediaCoverDTO.toMediaCover();
        mediaCover.setPost(newPost);
        MediaCover newMediaCover = mediaCoverRepository.save(mediaCover);

        return newMediaCover.toMediaCoverDTO();
    }

}
