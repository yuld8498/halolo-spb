package com.cg.domain.entity.user;

import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.dto.userDTO.UserDetailDTO;
import com.cg.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_details", indexes = @Index(columnList = "ts"))
public class UserDetail extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private Date dob;

    @Column(name = "avatar_file_name")
    private String avatarFileName;

    @Column(name = "avatar_file_folder")
    private String avatarFileFolder;

    @Column(name = "avatar_file_url")
    private String avatarFileUrl;

    @Column(name = "full_name")
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public UserDetailDTO toUserDetailDTO(){
        return new UserDetailDTO()
                .setId(id)
                .setFullName(fullName)
                .setDob(dob)
                .setGenderDTO(gender.toGenderDTO())
                .setAvatarFileFolder(avatarFileFolder)
                .setAvatarFileName(avatarFileName)
                .setAvatarFileUrl(avatarFileUrl)
                .setUserDTO(user.toUserDTO());
    }
}

