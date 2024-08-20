package com.cg.api;

import com.cg.domain.dto.mediaDTO.MediaCoverDTO;
import com.cg.domain.dto.postDTO.PostCreateDTO;
import com.cg.domain.dto.postDTO.PostDTO;
import com.cg.domain.dto.postDTO.PostWithMediaCreateDTO;
import com.cg.domain.entity.post.Post;
import com.cg.domain.entity.post.Reaction;
import com.cg.domain.entity.user.FriendList;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.user.UserDetail;
import com.cg.exception.DataInputException;
import com.cg.service.friendlist.IFriendListService;
import com.cg.service.post.PostServiceImp;
import com.cg.service.reaction.IReactionService;
import com.cg.service.user.IUserService;
import com.cg.service.userDetail.IUserDetailService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@RestController
@RequestMapping("/api/posts")
public class PostAPI {

    public static ArrayList<PostDTO> listAllPosts = new ArrayList<>();

    @Autowired
    private IUserService userService;
    @Autowired
    private PostServiceImp postService;
    @Autowired
    private IReactionService reactionService;
    @Autowired
    private IFriendListService friendListService;
    @Autowired
    private IUserDetailService userDetailService;

    @Autowired
    private AppUtils appUtils;


    @GetMapping("/{count}")
    public ResponseEntity<?> getAllPost(@PathVariable int count) {
        List<PostDTO> listRes = new ArrayList<>();
        User user = userService.findByEmail(appUtils.getPrincipal()).get();
        String userId = user.getId();
        int resCount = 0;

        try {
            if (listAllPosts.isEmpty()) {
                List<PostDTO> postDTOS = postService.getAllPost();
                listAllPosts.addAll(postDTOS);
            }

            FriendList friendList = friendListService.findByUserId(userId);

            if (friendList!=null){
                String friends = friendList.getFriends();

                for (PostDTO postDTO : listAllPosts) {
                    if (listRes.size() < 5) {
                        List<String> friendOfUser = postDTO.getFriendsList();
                        if (friendOfUser != null && (friends.contains(postDTO.getUserId())
                                || (postDTO.getUserId().equals(userId) && listAllPosts.indexOf(postDTO)>count))
                        ) {
                            resCount = resCount + 1;

                            if (resCount > count) {
                                Optional<Reaction> reaction = reactionService.getByUserIdAndPostId(postDTO.getId());

                                postDTO.setLiked(reaction.isPresent());
                                listRes.add(postDTO);
                            }
                        }
                    }
                }
            }

            if (listRes.isEmpty()) {
                if (listAllPosts.size() >= count) {
                    for (PostDTO postDTO : listAllPosts) {
                        if (listRes.size() < 5 && listAllPosts.indexOf(postDTO) >= count && count < listAllPosts.size()
                                ||(postDTO.getUserId().equals(userId) && listAllPosts.indexOf(postDTO)>count)) {
                            listRes.add(postDTO);
                        }
                    }
                    return new ResponseEntity<>(listRes, HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listRes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/offset/{offset}")
    public ResponseEntity<?> getPostOffset(@PathVariable String offset) {
        List<PostDTO> list = new ArrayList<>();

        try {
            List<PostDTO> postDTOS = postService.getPostOffset(offset);
            list.addAll(postDTOS);

            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/of/{userId}")
    public ResponseEntity<?> getAllPostOfUser(@PathVariable String userId) {
        try {
            List<PostDTO> postDTOS = postService.getAllPostOfUser(userId);

            return new ResponseEntity<>(postDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-post")
    public ResponseEntity<?> createPostOfUser(MultipartFile file, PostWithMediaCreateDTO postWithMediaCreateDTO) {
        try {
            PostDTO newDto = postService.createPostWithMediaOfUser(file, postWithMediaCreateDTO.toPostCreateDTO());
            newDto.setCountReaction(0L);

            UserDetail userDetail = userDetailService.findByUserId(newDto.getUserId());

            newDto.setAvatarFolder(userDetail.getAvatarFileFolder());
            newDto.setAvatarUrl(userDetail.getAvatarFileUrl());
            newDto.setAvatarName(userDetail.getAvatarFileName());
            listAllPosts.add(0,newDto);
//            listAllPosts.add(newDto);

            return new ResponseEntity<>(newDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-post-profile")
    public ResponseEntity<?> createPostProFile(MultipartFile file, PostWithMediaCreateDTO postWithMediaCreateDTO) {
        try {
            System.out.println(listAllPosts.size());
            PostDTO newDto = postService.createPostMediaProfile(file, postWithMediaCreateDTO.toPostCreateDTO());

            String avatarUrl = newDto.getAvatarUrl();
            String avatarFolder = newDto.getAvatarFolder();
            String avatarName = newDto.getAvatarName();
            String userId = newDto.getUserId();

            newDto.setAvatarFolder(avatarFolder);
            newDto.setAvatarName(avatarName);
            newDto.setAvatarUrl(avatarUrl);
            newDto.setCountReaction(0L);

            for (PostDTO postDTO: listAllPosts){
                if (postDTO.getUserId().equals(userId)){
                    postDTO.setAvatarUrl(avatarUrl);
                    postDTO.setAvatarFolder(avatarFolder);
                    postDTO.setAvatarName(avatarName);
                }
            }

            listAllPosts.add(0,newDto);

            return new ResponseEntity<>(newDto,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-post-cover")
    public ResponseEntity<?> createPostCover(MultipartFile file, PostWithMediaCreateDTO postWithMediaCreateDTO) {
        try {
            PostDTO newDto = postService.createPostMediaCover(file, postWithMediaCreateDTO.toPostCreateDTO());
            newDto.setCountReaction(1L);

            listAllPosts.add(0,newDto);

            MediaCoverDTO mediaCoverDTO = new MediaCoverDTO();
            mediaCoverDTO.setId(newDto.getId());
            mediaCoverDTO.setFileFolder(newDto.getAvatarFolder());
            mediaCoverDTO.setFileName(newDto.getAvatarName());
            mediaCoverDTO.setFileUrl(newDto.getUrlMedia());

            return new ResponseEntity<>(mediaCoverDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/count-post/{userId}")
    public ResponseEntity<?> countPostByUserId(@PathVariable String userId) {
        return new ResponseEntity<>(postService.countPostByUserId(userId), HttpStatus.OK);
    }

    @PutMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable String postId) {
        Optional<Post> post = postService.findById(postId);

        if (!post.isPresent()) {
            return new ResponseEntity<>("Post is null!", HttpStatus.NO_CONTENT);
        }

        try {
            post.get().setDeleted(true);
            postService.save(post.get());

            PostDTO newPostDTO = new PostDTO();

            for (PostDTO postDTO : listAllPosts) {
                if (postDTO.getId().equals(post.get().getId())) {
                    newPostDTO = postDTO;
                }
            }

            listAllPosts.remove(newPostDTO);

            return new ResponseEntity<>("Delete is success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server can't handle!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<?> editPost(@PathVariable String postId,
                                      @RequestBody PostCreateDTO postCreateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Optional<Post> postOptional = postService.findById(postId);

        if (!postOptional.isPresent()) {
            throw new DataInputException("PostId không tồn tại !");
        }

        postCreateDTO.setId(postOptional.get().getId());

        try {
            PostDTO editPost = postService.updatePost(postCreateDTO);

            for(PostDTO postDTO: listAllPosts){
                if (postDTO.getId().equals(editPost.getId())){
                    postDTO.setContent(editPost.getContent());
                    postDTO.setWidth(editPost.getWidth());
                    postDTO.setHeight(editPost.getHeight());
                    break;
                }
            }

            return new ResponseEntity<>(editPost, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping("/find/{postId}")
    public ResponseEntity<?> findByPostId(@PathVariable String postId) {
        for (PostDTO postDTO : listAllPosts) {
            if (postDTO.getId().contains(postId)) {
                return new ResponseEntity<>(postDTO, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
