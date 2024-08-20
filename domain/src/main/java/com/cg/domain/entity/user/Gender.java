package com.cg.domain.entity.user;

import com.cg.domain.dto.userDTO.GenderDTO;
import com.cg.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genders", indexes = @Index(columnList = "ts"))
public class Gender extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public GenderDTO toGenderDTO() {
        return new GenderDTO()
                .setId(id)
                .setName(name);
    }
}
