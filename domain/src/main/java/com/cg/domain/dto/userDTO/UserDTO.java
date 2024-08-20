package com.cg.domain.dto.userDTO;

import com.cg.domain.entity.user.Gender;
import com.cg.domain.entity.user.User;
import com.cg.utils.ValidateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String id;
    @NotNull(message = "Vui lòng nhập email!")
    @Size(min = 5, max = 32, message = "Email phải gồm 5-32 kí tự!")
    @Pattern(regexp = ValidateUtils.EMAIL_REGEX, message = "Vui lòng nhập đúng đinh dạng email!")
    private String email;
    @NotNull(message = "Vui lòng nhập mật khẩu!")
    @Size(min = 8, message = "Mật khẩu tối thiểu 8 kí tự, phải đầy đủ chữ hoa, thường và số ")
    @Pattern(regexp = ValidateUtils.PASSWORD_REGEX, message = "Vui lòng nhập đúng đinh dạng password!")
    private String password;

    private String dob;

    private String avatarFileName;

    private String avatarFileFolder;
    private String avatarFileUrl;

    private RoleDTO role;

    private String fullName;

    private Date createdAt;

    private GenderDTO genderDTO;


    public User toUser() {
        return new User()
                .setId(id)
                .setEmail(email)
                .setPassword(password)
                .setRole(role.toRole());
    }


    public UserDTO(String id,  String fullName,Date dob,String avatarFileFolder,String avatarFileName,String avatarFileUrl, String email) {
        this.id= id;
        this.fullName = fullName;
        this.dob = dob.toString();
        this.avatarFileFolder= avatarFileFolder;
        this.avatarFileName = avatarFileName;
        this.avatarFileUrl = avatarFileUrl;
        this.email = email;
    }
    public UserDTO(String id,Date createdAt, String fullName, Date dob, Gender gender,String avatarFileFolder, String avatarFileName, String avatarFileUrl, String email) {
        this.id= id;
        this.createdAt = createdAt;
        this.fullName = fullName;
        this.dob = dob.toString();
        this.genderDTO = gender.toGenderDTO();
        this.avatarFileFolder= avatarFileFolder;
        this.avatarFileName = avatarFileName;
        this.avatarFileUrl = avatarFileUrl;
        this.email = email;
    }
}

