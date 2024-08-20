package com.cg.service.mediaPost;

import com.cg.domain.dto.mediaDTO.MediaPostDTO;
import com.cg.domain.entity.media.MediaPost;
import com.cg.domain.entity.post.Post;
import com.cg.repository.media.MediaPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MediaPostServiceImp implements IMediaPostService{
    @Autowired
    private MediaPostRepository mediaPostRepository;
    @Override
    public List<MediaPost> findAll() {
        return mediaPostRepository.findAll();
    }

    @Override
    public MediaPost create(MediaPost mediaPost) {
        return mediaPostRepository.save(mediaPost);
    }

    @Override
    public void delete(MediaPost mediaPost) {
        mediaPostRepository.delete(mediaPost);
    }

    @Override
    public Optional<MediaPost> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<MediaPost> findById(String id) {
        return mediaPostRepository.findById(id);
    }

    @Override
    public Optional<MediaPost> findByPost(Post post) {
        return mediaPostRepository.findByPost(post);
    }

    @Override
    public MediaPost save(MediaPost mediaPost) {
        return mediaPostRepository.save(mediaPost);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {
        Optional<MediaPost> mediaPostOptional = mediaPostRepository.findById(id);
        if (mediaPostOptional.isPresent()){
            MediaPost mediaPost = mediaPostOptional.get();
            mediaPost.setDeleted(true);
            mediaPostRepository.save(mediaPost);
        }
    }

    @Override
    public List<MediaPostDTO> findMediaPostByUserId(String userId) {
        return mediaPostRepository.findMediaPostByUserId(userId);
    }
}
