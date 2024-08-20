package com.cg.service.comment;

import com.cg.domain.dto.commentDTO.CommentDTO;
import com.cg.domain.dto.commentDTO.ICommentDTO;
import com.cg.domain.dto.notificationDTO.NotificationDTO;
import com.cg.domain.dto.postDTO.PostCreateDTO;
import com.cg.domain.dto.postDTO.PostDTO;
import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.comment.Comment;
import com.cg.domain.entity.notification.Notification;
import com.cg.domain.entity.notification.NotificationType;
import com.cg.domain.entity.post.Post;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.user.UserDetail;
import com.cg.repository.comment.CommentRepository;
import com.cg.repository.notification.NotificationRepository;
import com.cg.repository.post.PostRepository;
import com.cg.repository.user.UserDetailRepository;
import com.cg.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImp implements ICommentService{
    @Autowired
    private CommentRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public List<Comment> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Comment> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {
        Optional<Comment> commentOptional = repository.findById(id);
        if (commentOptional.isPresent()){
            Comment comment = commentOptional.get();
            comment.setDeleted(true);
            repository.save(comment);
        }
    }

    @Override
    public List<CommentDTO> findCommentByPostId(String postId) {
        List<ICommentDTO> iCommentDTOS =  repository.findAllByPostId(postId);
        List<CommentDTO> commentDTOS = new ArrayList<>();

        for (ICommentDTO commentDTO : iCommentDTOS) {

            CommentDTO newCommentDTO = new CommentDTO();
            PostCreateDTO postCreateDTO = new PostCreateDTO();
            UserDTO userDTO = new UserDTO();

            postCreateDTO.setId(commentDTO.getPostId());

            userDTO.setId(commentDTO.getCreatedBy());
            userDTO.setAvatarFileUrl(commentDTO.getAvatarUrl());
            userDTO.setFullName(commentDTO.getFullName());
            userDTO.setEmail(commentDTO.getEmail());
            userDTO.setAvatarFileName(commentDTO.getAvatarUrl());
            userDTO.setAvatarFileFolder(commentDTO.getFileFolder());
            userDTO.setAvatarFileUrl(commentDTO.getFileUrl());
            userDTO.setPassword("null");

            newCommentDTO.setPostCreateDTO(postCreateDTO);
            newCommentDTO.setUserDTO(userDTO);
            newCommentDTO.setContent(commentDTO.getContent());
            newCommentDTO.setId(commentDTO.getId());
            newCommentDTO.setCreatedAt(commentDTO.getCreatedAt());

            commentDTOS.add(newCommentDTO);
        }
        return  commentDTOS;
    }

    @Override
    public CommentDTO createCommentByUser(String postId, String content) {
        Comment comment = new Comment();
        Post post = postRepository.findById(postId).get();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        post.setTs(timestamp.getTime());
        postRepository.save(post);

        comment.setPost(post);
        String userIdOfPost = post.getUser().getId();

        User user = userRepository.findByEmail(getPrincipal()).get();
        comment.setCreatedBy(user.getId());
        UserDetail userDetail = userDetailRepository.findByUserId(user.getId());

        comment.setContent(content);

        Comment newCmt = repository.save(comment);

        UserDTO userDTO = user.toUserDTO();
        userDTO.setFullName(userDetail.getFullName());
        userDTO.setDob(String.valueOf(userDetail.getDob()));
        userDTO.setAvatarFileUrl(userDetail.getAvatarFileUrl());
        userDTO.setAvatarFileName(userDetail.getAvatarFileName());
        userDTO.setAvatarFileFolder(userDetail.getAvatarFileFolder());
        userDTO.setPassword("null");

        CommentDTO commentDTO = newCmt.toCommentDTO();
        commentDTO.setPostCreateDTO(post.postCreateDTO());
        commentDTO.setUserDTO(userDTO);
        commentDTO.setCreatedAt(new Date());

        NotificationType notificationType =new NotificationType();
        notificationType.setId(3L);

        Notification notification =new Notification();
        Optional<Notification> getNotificationOpt = notificationRepository.getNotificationByNoteEqualsIgnoreCase((post.getId()+"cmt"));
      if (!userIdOfPost.equals(user.getId())){
          if (getNotificationOpt.isPresent()){
              Notification getNotification = getNotificationOpt.get();
              String[] newArr = getNotification.getContent().split(" ");
              if(getNotification.getNotificationType().getId() == 3L ){
                  if (newArr.length>4){
                      if (newArr.length ==5){
                          getNotification.setContent(userDetail.getFullName() + " and " + getNotification.getContent());
                      }
                      if (newArr.length ==6){
                          getNotification.setContent(userDetail.getFullName() + " and " + getNotification.getContent());
                      }
                      if(newArr.length == 7 ){
                          getNotification.setContent(userDetail.getFullName() + " and 2 another user" + " commented on your post");
                      }
                      if(newArr.length > 7){
                          String howmany = "2" ;
                          for (String s: newArr){
                              if (s.matches("\\d+")){
                                  howmany = s;
                              }
                          }
                          getNotification.setContent(userDetail.getFullName() + " and "+ howmany+" another user" + " commented on your post");
                      }
                      notificationRepository.save(getNotification);
                  }
              }

          }else {
              notification.setNote(post.getId()+"cmt");
              notification.setNotificationType(notificationType);
              notification.setContent(userDetail.getFullName()+ " commented on your post");
              notification.setSender(user);
              notification.setRecipient(post.getUser());
              notificationRepository.save(notification);
          }
      }

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
