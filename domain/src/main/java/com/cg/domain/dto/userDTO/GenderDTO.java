package com.cg.domain.dto.userDTO;

import com.cg.domain.entity.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GenderDTO {

    private Long id;

    private String name;

    public Gender toGender(){
        return new Gender()
                .setId(id)
                .setName(name);
    }
}
