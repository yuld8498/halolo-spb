package com.cg.domain.dto.userDTO;

import com.cg.domain.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleDTO {
    private Long id;

    private String code;

    private String name;

    public Role toRole() {
        return new Role()
                .setId(id)
                .setCode(code)
                .setName(name);
    }
}
