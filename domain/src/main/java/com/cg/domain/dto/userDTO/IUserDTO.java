package com.cg.domain.dto.userDTO;

import com.cg.utils.ValidateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public interface IUserDTO {
    String getId();

    String getEmail();

    String getPassword();

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date getDob();

    String getAvatarUrl();

    RoleDTO getRole();

    String getFullName();

    GenderDTO GetGenderDTO();
}
