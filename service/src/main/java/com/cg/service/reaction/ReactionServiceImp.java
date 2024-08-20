package com.cg.service.reaction;

import com.cg.domain.entity.notification.Notification;
import com.cg.domain.entity.notification.NotificationType;
import com.cg.domain.entity.post.Post;
import com.cg.domain.entity.post.Reaction;
import com.cg.domain.entity.post.ReactionType;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.user.UserDetail;
import com.cg.repository.notification.NotificationRepository;
import com.cg.repository.post.PostRepository;
import com.cg.repository.post.ReactionRepository;
import com.cg.repository.user.UserDetailRepository;
import com.cg.repository.user.UserRepository;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReactionServiceImp implements IReactionService{

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;


    @Override
    public List<Reaction> findAll() {
        return reactionRepository.findAll();
    }

    @Override
    public Optional<Reaction> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Reaction> findById(String id) {
        return reactionRepository.findById(id);
    }

    @Override
    public Reaction save(Reaction reaction) {
        return reactionRepository.save(reaction);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {
        Optional<Reaction> reactionOptional = reactionRepository.findById(id);
        if (reactionOptional.isPresent()){
            Reaction reaction = reactionOptional.get();
            reaction.setDeleted(true);
            reactionRepository.save(reaction);
        }
    }

    @Override
    public Optional<Reaction> findByPostIdAndCreatedById(String postId, String id) {
        return reactionRepository.findReactionByPostIdAndCreatedById(id,postId);
    }

    @Override
    public void addReactionForPost( String postId) {
        Reaction reaction = new Reaction();
        Optional<User> user = userRepository.findByEmail(appUtils.getPrincipal());
        Optional<Post> post = postRepository.findById(postId);
        String userIdOfPost = post.get().getUser().getId();

        reaction.setDeleted(false);

        reaction.setCreatedAt(new Date());
        reaction.setCreatedBy(user.get().getId());
        reaction.setPost(post.get());

        ReactionType reactionType = new ReactionType();
        reactionType.setId(1L);

        reaction.setReactionType(reactionType);
        reaction.setUser(user.get());
        reactionRepository.save(reaction);

        NotificationType notificationType =new NotificationType();
        notificationType.setId(2L);

        Notification notification =new Notification();
        Optional<Notification> getNotificationOpt = notificationRepository.getNotificationByNoteEqualsIgnoreCase(postId+"cmt");
        User userS = user.get();

        UserDetail userDetail = userDetailRepository.findByUserId(userS.getId());

        if(!(user.get().getId().equals(userIdOfPost))){
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

                notification.setNote(postId+"reaction");
                notification.setNotificationType(notificationType);
                notification.setContent(userDetail.getFullName()+ " reaction on your post");
                notification.setSender(user.get());
                notification.setRecipient(post.get().getUser());
            }
        }

        notificationRepository.save(notification);
    }

    @Override
    public Optional<Reaction> getByUserIdAndPostId(String postId) {
        User user = userRepository.findByEmail(appUtils.getPrincipal()).get();
        return reactionRepository.findByUserIdAndPostIdAndDeletedIsFalse(user.getId(), postId);
    }

}
