package com.cg.domain.entity.user;

import com.cg.domain.dto.userDTO.UserDTO;
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
@Table(name = "users", indexes = @Index(columnList = "ts"))
public class User extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column (unique = true)
    private String email;

    private String password;


    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;



    public UserDTO toUserDTO() {
        return new UserDTO()
                .setId(id)
                .setEmail(email)
                .setCreatedAt(new Date())
                .setPassword(password)
                .setRole(role.toRoleDTO());
    }


}
