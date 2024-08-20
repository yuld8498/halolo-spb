package com.cg.repository.notification;

import com.cg.domain.dto.notificationDTO.NotificationDTO;
import com.cg.domain.entity.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

    @Query("SELECT NEW com.cg.domain.entity.notification.Notification (" +
            "n.id, " +
            "n.content, " +
            "n.sender , " +
            "n.recipient , " +
            "n.notificationType, " +
            "n.note " +
            ") " +
            "FROM Notification AS n " +
            "WHERE n.recipient.id = ?1 " +
            "AND n.deleted = false "
    )
    List<Notification> findAllNotificationByUserId(@Param("userId") String userId);

    @Query("SELECT NEW com.cg.domain.dto.notificationDTO.NotificationDTO (" +
            "n.id, " +
            "n.content, " +
            "n.createdAt, " +
            "n.sender.id, " +
            "ud.avatarFileName, " +
            "ud.avatarFileFolder, " +
            "ud.avatarFileUrl, " +
            "ud.fullName, " +
            "n.recipient.id, " +
            "ud.avatarFileName, " +
            "n.notificationType.id, " +
            "n.deleted, " +
            "n.note, " +
            "n.seen" +
            ") " +
            "FROM Notification  AS n " +
            "JOIN UserDetail AS ud " +
            "ON ud.user.id = n.sender.id " +
            "WHERE n.recipient.id = :userId " +
            "AND n.deleted = false " +
            "ORDER BY n.createdAt DESC "
    )
    List<NotificationDTO> findAllNotificationDTOByUserId(@Param("userId") String userId);
//
//    @Query(value = "SELECT NEW com.cg.domain.entity.notification.Notification (" +
//            "n.id," +
//            " n.content," +
//            "n.sender, " +
//            " n.recipient," +
//            "n.notificationType," +
//            "n.note) " +
//            "FROM Notification  AS n WHERE n.note =: note")
//    Optional<Notification> getNotificationByNote(String note);

    Optional<Notification> getNotificationByNoteEqualsIgnoreCase(String note);
}
