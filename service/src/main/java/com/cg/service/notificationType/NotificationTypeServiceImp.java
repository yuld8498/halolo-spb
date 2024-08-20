package com.cg.service.notificationType;

import com.cg.domain.entity.notification.NotificationType;
import com.cg.repository.notification.NotificationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificationTypeServiceImp implements INotificationTypeService{
    @Autowired
    private NotificationTypeRepository notificationTypeRepository;
    @Override
    public List<NotificationType> findAll() {
        return notificationTypeRepository.findAll();
    }

    @Override
    public Optional<NotificationType> findById(Long id) {
        return notificationTypeRepository.findById(id);
    }

    @Override
    public Optional<NotificationType> findById(String id) {
        return Optional.empty();
    }

    @Override
    public NotificationType save(NotificationType notificationType) {
        return notificationTypeRepository.save(notificationType);
    }

    @Override
    public void remove(Long id) {
        Optional<NotificationType> notificationTypeOptional = notificationTypeRepository.findById(id);
        if (notificationTypeOptional.isPresent()){
            NotificationType notificationType = notificationTypeOptional.get();
            notificationType.setDeleted(true);
            notificationTypeRepository.save(notificationType);
        }
    }

    @Override
    public void remove(String id) {

    }
}
