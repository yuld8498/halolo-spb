package com.cg.api;

import com.cg.domain.dto.commentDTO.CommentDTO;
import com.cg.domain.dto.commentDTO.ReplyCommentDTO;
import com.cg.domain.dto.postDTO.PostDTO;
import com.cg.service.comment.CommentServiceImp;
import com.cg.service.replyComment.IReplyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/comment")
public class CommentAPI {
    @Autowired
    private CommentServiceImp commentService;

    @Autowired
    private IReplyCommentService replyCommentService;

    @GetMapping("/{postId}")
    public ResponseEntity<?> getAllCommentOfPostByPostId(@PathVariable String postId) {
        try{
            List<CommentDTO> commentDTOS = commentService.findCommentByPostId(postId);

            return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/reply/{commentId}")
    public ResponseEntity<?> getAllReplyCommentOfPostByPostId(@PathVariable String commentId) {
        try{
            List<ReplyCommentDTO> replyCommentDTOS = replyCommentService.findAllReplyCommentByCommentId(commentId);

            return new ResponseEntity<>(replyCommentDTOS, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{postId}")
    public ResponseEntity<?>createCommentByUser(@PathVariable String postId,@RequestBody String comment){
        try{
            CommentDTO newCommentDTO = commentService.createCommentByUser(postId,comment);

            PostDTO postDTO = new PostDTO();
            for(PostDTO post: PostAPI.listAllPosts){
                if (post.getId().equals(postId)){
                    postDTO= post;
                    break;
                }
            }

            PostAPI.listAllPosts.remove(PostAPI.listAllPosts.indexOf(postDTO));
            PostAPI.listAllPosts.add(0, postDTO);

            return new ResponseEntity<>(newCommentDTO,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/reply/{cmtId}")
    public ResponseEntity<?>replyCommentByUser(@PathVariable String cmtId,
                                               @RequestBody String comment){
        try{
            ReplyCommentDTO replyCommentDTO = replyCommentService.replyCommentByUser(cmtId, comment);

            return new ResponseEntity<>(replyCommentDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
