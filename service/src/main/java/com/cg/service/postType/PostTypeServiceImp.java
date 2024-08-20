package com.cg.service.postType;

import com.cg.domain.entity.post.PostType;
import com.cg.repository.post.PostTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostTypeServiceImp implements IPostTypeService{
    @Autowired
    private PostTypeRepository repository;
    @Override
    public List<PostType> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<PostType> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<PostType> findById(String id) {
        return Optional.empty();
    }

    @Override
    public PostType save(PostType postType) {
        return repository.save(postType);
    }

    @Override
    public void remove(Long id) {
        Optional<PostType> postTypeOptional = repository.findById(id);
        if (postTypeOptional.isPresent()){
            PostType postType = postTypeOptional.get();
            postType.setDeleted(true);
            repository.save(postType);
        }
    }

    @Override
    public void remove(String id) {

    }
}
