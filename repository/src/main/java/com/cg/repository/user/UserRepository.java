package com.cg.repository.user;

import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    @Modifying
    @Query("UPDATE User AS u SET u.deleted = 1 WHERE u.id = :id")
    void blockUser(@Param("id") String id);


    @Modifying
    @Query("UPDATE User AS u SET u.deleted = 0 WHERE u.id = :id")
    void unlockUser(@Param("id") String id);


    @Query("SELECT NEW com.cg.domain.dto.userDTO.UserDTO (" +
            "u.id, " +
            "ud.fullName, " +
            "ud.dob, " +
            "ud.avatarFileFolder, " +
            "ud.avatarFileName, " +
            "ud.avatarFileUrl, " +
            "u.email " +
            ") " +
            "FROM User  AS u " +
            "JOIN UserDetail  AS ud " +
            "ON ud.user.id = u.id " +
            "WHERE ud.fullName LIKE CONCAT('%',:nameChar,'%') "
    )
    List<UserDTO> findDtoByNameLike(@Param("nameChar") String nameChar);

    @Query("SELECT NEW com.cg.domain.dto.userDTO.UserDTO (" +
            "u.id," +
            "ud.fullName," +
            "ud.dob," +
            "ud.avatarFileFolder," +
            "ud.avatarFileName," +
            "ud.avatarFileUrl," +
            "u.email" +
            ") " +
            "FROM User  AS u " +
            "JOIN UserDetail  AS ud " +
            "ON ud.user.id = u.id " +
            "WHERE u.id NOT IN(:listFriend)"
    )
    List<UserDTO> getUserNonFriend(@Param("listFriend") List<String> listFriend);

    @Query(value = "CALL sp_find_user_chat(:userId, :name);", nativeQuery = true)
    List<?> findUserDTOByName(@Param("userId") String userId, @Param("name") String name);


    @Query(value = "CALL sp_find_user_by_name(:name);", nativeQuery = true)
    List<?> searchUserDTOByName(@Param("name") String name);

    @Query("SELECT NEW com.cg.domain.dto.userDTO.UserDTO (" +
            "u.id, " +
            "u.createdAt, " +
            "ud.fullName, " +
            "ud.dob, " +
            "ud.gender," +
            "ud.avatarFileFolder, " +
            "ud.avatarFileName, " +
            "ud.avatarFileUrl, " +
            "u.email " +
            ") " +
            "FROM User  AS u " +
            "JOIN UserDetail  AS ud " +
            "ON ud.user.id = u.id " +
            "WHERE u.id = :userId ")
    UserDTO getUserAndDetailByUserId(@Param("userId") String userId);
}
