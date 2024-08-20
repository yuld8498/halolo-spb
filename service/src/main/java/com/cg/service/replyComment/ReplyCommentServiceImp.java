package com.cg.service.replyComment;

import com.cg.domain.dto.commentDTO.CommentDTO;
import com.cg.domain.dto.commentDTO.ICommentDTO;
import com.cg.domain.dto.commentDTO.ReplyCommentDTO;
import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.comment.Comment;
import com.cg.domain.entity.comment.ReplyComment;
import com.cg.domain.entity.user.User;
import com.cg.repository.comment.CommentRepository;
import com.cg.repository.comment.ReplyCommentRepository;
import com.cg.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReplyCommentServiceImp implements IReplyCommentService{
    @Autowired
    private ReplyCommentRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public List<ReplyComment> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ReplyComment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ReplyComment> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public ReplyComment save(ReplyComment replyComment) {
        return repository.save(replyComment);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {
        Optional<ReplyComment> replyComment = repository.findById(id);
        if (replyComment.isPresent()){
            ReplyComment newCmt = replyComment.get();
            newCmt.setDeleted(true);
            repository.save(newCmt);
        }
    }

    @Override
    public List<ReplyCommentDTO> findAllReplyCommentByCommentId(String commentId) {

        List<ICommentDTO> comments = repository.findAllReplyCommentByCommentId(commentId);
        List<ReplyCommentDTO> replyCommentDTOS = new ArrayList<>();

        for (ICommentDTO commentDTO : comments){

            ReplyCommentDTO replyCommentDTO = new ReplyCommentDTO();
            CommentDTO newCommentDTO = new CommentDTO();
            UserDTO userDTO = new UserDTO();

            newCommentDTO.setId(commentDTO.getPostId());

            userDTO.setFullName(commentDTO.getFullName());
            userDTO.setAvatarFileUrl(commentDTO.getAvatarUrl());

            replyCommentDTO.setCommentDTO(newCommentDTO);
            replyCommentDTO.setUserDTO(userDTO);
            replyCommentDTO.setContent(commentDTO.getContent());
            replyCommentDTO.setId(commentDTO.getId());

            replyCommentDTOS.add(replyCommentDTO);
        }
        return replyCommentDTOS;
    }

    @Override
    public ReplyCommentDTO replyCommentByUser(String cmtId, String content) {
        ReplyComment replyComment = new ReplyComment();
        User user = userRepository.findByEmail(getPrincipal()).get();
        Comment comment = commentRepository.findById(cmtId).get();

        replyComment.setComment(comment);
        replyComment.setContent(content);
        replyComment.setCreatedBy(user.getId());

        ReplyCommentDTO commentDTO = repository.save(replyComment).toReplyCommentDTO();
        return commentDTO;
    }

    private String getPrincipal() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = "";
        }

        return username;
    }
}
