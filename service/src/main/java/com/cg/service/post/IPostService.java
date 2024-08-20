package com.cg.service.post;

import com.cg.domain.dto.postDTO.IPostDTO;
import com.cg.domain.dto.postDTO.PostCreateDTO;
import com.cg.domain.dto.postDTO.PostDTO;
import com.cg.domain.dto.postDTO.PostWithMediaCreateDTO;
import com.cg.domain.entity.post.Post;
import com.cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPostService extends IGeneralService<Post> {
    List<IPostDTO> findAllPostOfUserById(String userId);

    List<IPostDTO> findAllPosts(String userId);

    PostDTO createPostWithMediaOfUser(MultipartFile file, PostCreateDTO postCreateDTO);
    PostDTO createPostMediaProfile(MultipartFile file, PostCreateDTO postCreateDTO);
    PostDTO createPostMediaCover(MultipartFile file, PostCreateDTO postCreateDTO);

    List<PostDTO> getAllPostOfUser(String userId);

    List<PostDTO> getAllPost();

    Long countPostByUserId(String userId);

    List<PostDTO> getPostOffset(String offset);

    PostDTO updatePost(PostCreateDTO postCreateDTO);

}
