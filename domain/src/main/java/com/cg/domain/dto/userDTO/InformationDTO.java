package com.cg.domain.dto.userDTO;


import com.cg.domain.entity.user.Information;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InformationDTO {
    private String id;

    private String nickName;

    private String job;

    private String fullName;

    private String phoneNumber;

    private String address;


    private String dob;

    private Date birthDay;

    private UserDTO userDTO;

    public InformationDTO(String id, String nickName, String job, String fullName, String phoneNumber, String address,Date birthDay) {
        this.id = id;
        this.nickName = nickName;
        this.job = job;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDay = birthDay;
    }

    public InformationDTO(String id, String nickName, String job, String phoneNumber, String address, UserDTO userDTO) {
        this.id = id;
        this.nickName = nickName;
        this.job = job;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.userDTO = userDTO;
    }

    public Information toInformation(){
        return new Information()
                .setId(id)
                .setNickName(nickName)
                .setPhoneNumber(phoneNumber)
                .setAddress(address)
                .setJob(job)
                .setUser(userDTO.toUser());
    }
}
