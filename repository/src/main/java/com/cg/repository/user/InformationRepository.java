package com.cg.repository.user;

import com.cg.domain.dto.userDTO.InformationDTO;
import com.cg.domain.entity.user.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InformationRepository extends JpaRepository<Information, String> {
    @Override
    Optional<Information> findById(@Param("id") String id);


    @Query("SELECT NEW com.cg.domain.entity.user.Information (" +
                "i.id, " +
                "i.nickName, " +
                "i.phoneNumber, " +
                "i.job, " +
                "i.address, " +
                "i.user " +
            ") " +
            "FROM Information AS i " +
            "WHERE i.user.email = :email"
    )
    Optional<Information> findByEmailUser(@Param("email") String email);


    @Query("SELECT NEW com.cg.domain.entity.user.Information (" +
                "i.id, " +
                "i.nickName, " +
                "i.phoneNumber, " +
                "i.job, " +
                "i.address, " +
                "i.user " +
            ") " +
            "FROM Information AS i " +
            "WHERE i.user.id = :userId"
    )
    Optional<Information> findByUserId(@Param("userId") String userId);

    @Query(value = "SELECT NEW com.cg.domain.dto.userDTO.InformationDTO(" +
            "ud.id," +
            "i.nickName," +
            "i.job," +
            "ud.fullName," +
            "i.phoneNumber," +
            "i.address," +
            "ud.dob) FROM " +
            "UserDetail AS ud " +
            "JOIN Information  AS i " +
            "ON i.user.id = ud.user.id AND i.user.id = :userId")
    InformationDTO getInformationByUserId(String userId);
}
