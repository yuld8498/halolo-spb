package com.cg.domain.entity.message;


import com.cg.domain.dto.messageDTO.RoleMemberDTO;
import com.cg.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role_member", indexes = @Index(columnList = "ts"))
public class RoleMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role_name")
    private String roleName;

    public RoleMemberDTO toRoleMemberDTO(){
        return new RoleMemberDTO()
                .setId(id)
                .setRoleName(roleName);
    }
}
