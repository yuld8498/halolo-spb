package com.cg.service.notification;

import com.cg.domain.dto.notificationDTO.NotificationDTO;
import com.cg.domain.entity.notification.Notification;
import com.cg.domain.entity.user.User;
import com.cg.repository.notification.NotificationRepository;
import com.cg.repository.user.UserRepository;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificationServiceImp implements INotificationService{

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Notification> findById(String id) {
        return notificationRepository.findById(id);
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {
        Optional<Notification> notificationOptional  = notificationRepository.findById(id);
        if (notificationOptional.isPresent()){
            Notification notification = notificationOptional.get();
            notification.setDeleted(true);
            notificationRepository.save(notification);
        }
    }

    @Override
    public List<NotificationDTO> getAllNotificationDTOByUserId(String id) {
        User user = userRepository.findByEmail(appUtils.getPrincipal()).get();
        return notificationRepository.findAllNotificationDTOByUserId(user.getId());
    }

    @Override
    public List<Notification> findAllNotificationByUserId() {
        User user = userRepository.findByEmail(appUtils.getPrincipal()).get();
        return  notificationRepository.findAllNotificationByUserId(user.getId());
    }

    @Override
    public void saveAll(List<Notification> notifications) {
        notificationRepository.saveAll(notifications);
    }


}
