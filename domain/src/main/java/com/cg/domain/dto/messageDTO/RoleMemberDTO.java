package com.cg.domain.dto.messageDTO;

import com.cg.domain.entity.message.RoleMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleMemberDTO {

    private Long id;

    private String roleName;

    public RoleMember toRoleMember(){
        return new RoleMember()
                .setId(id)
                .setRoleName(roleName);
    }
}
