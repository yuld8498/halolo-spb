package com.cg.api;

import com.cg.domain.dto.postDTO.PostDTO;
import com.cg.domain.dto.postDTO.ReactionTypeDTO;
import com.cg.domain.entity.post.Post;
import com.cg.domain.entity.post.Reaction;
import com.cg.domain.entity.post.ReactionType;
import com.cg.domain.entity.user.User;
import com.cg.service.ReactionType.IReactionTypeService;
import com.cg.service.post.IPostService;
import com.cg.service.reaction.IReactionService;
import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/reaction")
public class ReactionAPI {

    @Autowired
    private IReactionTypeService reactionTypeService;

    @Autowired
    private IReactionService reactionService;

    @Autowired
    private IPostService postService;

    @Autowired
    private IUserService userService;

//    @GetMapping("/reaction-types")
//    public ResponseEntity<?> getListReactionType(){
//        Iterable<ReactionTypeDTO> reactionTypeDTOS = reactionTypeService.findAllReactionTypeDTO();
//        if (reactionTypeDTOS == null){
//            return new ResponseEntity<>("Danh sách biểu cảm trống", HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(reactionTypeDTOS,HttpStatus.OK);
//    }

    @PostMapping("/{postId}")
    public ResponseEntity<?> addReactionForPost(@PathVariable String postId){
        try {
            reactionService.addReactionForPost(postId);
            for (PostDTO postDTO: PostAPI.listAllPosts){
                if (postDTO.getId().equals(postId)){
                    postDTO.setLiked(true);
                    postDTO.setCountReaction(postDTO.getCountReaction() + 1);
                }
            }
            return new ResponseEntity<>("Reaction is success", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Server can't handle", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/{postId}")
    public ResponseEntity<?> unReactionForPost(@PathVariable String id, String postId){
        try {
            reactionService.findByPostIdAndCreatedById(id, postId);

            for (PostDTO postDTO: PostAPI.listAllPosts){
                if (postDTO.getId().equals(postId)){
                    postDTO.setLiked(false);
                    postDTO.setCountReaction(postDTO.getCountReaction()-1);
                }
            }

            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Server can't handle", HttpStatus.BAD_REQUEST);
        }
    }

}
