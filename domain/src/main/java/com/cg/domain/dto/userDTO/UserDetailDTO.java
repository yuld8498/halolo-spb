package com.cg.domain.dto.userDTO;

import com.cg.domain.entity.user.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDetailDTO {
    private String id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss Z")
    private Date dob;

    private String avatarFileName;

    private String avatarFileFolder;

    private String avatarFileUrl;

    private String fullName;

    private GenderDTO genderDTO;

    private UserDTO userDTO;
    public UserDetail toUserDetail(){
        return new UserDetail()
                .setId(id)
                .setFullName(fullName)
                .setDob(dob)
                .setGender(genderDTO.toGender())
                .setAvatarFileFolder(avatarFileFolder)
                .setAvatarFileName(avatarFileName)
                .setAvatarFileUrl(avatarFileUrl)
                .setUser(userDTO.toUser());
    }
}
