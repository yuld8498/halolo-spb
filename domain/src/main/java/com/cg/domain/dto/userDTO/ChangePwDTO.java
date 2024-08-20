package com.cg.domain.dto.userDTO;

import com.cg.utils.ValidateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangePwDTO {
    private String password;

    @NotNull(message = "Vui lòng nhập mật khẩu!")
    @Size(min = 8, message = "Mật khẩu tối thiểu 8 kí tự, phải đầy đủ chữ hoa, thường và số ")
    @Pattern(regexp = ValidateUtils.PASSWORD_REGEX, message = "Vui lòng nhập đúng đinh dạng password!")
    private String newPassword;
    private String confirmPassword;
}
