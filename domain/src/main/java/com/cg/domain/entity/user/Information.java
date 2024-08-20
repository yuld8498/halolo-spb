package com.cg.domain.entity.user;

import com.cg.domain.dto.userDTO.InformationDTO;
import com.cg.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "informations", indexes = @Index(columnList = "ts"))
public class Information extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String job;
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public InformationDTO toInformationDTO() {
        return new InformationDTO()
                .setId(id)
                .setNickName(nickName)
                .setJob(job)
                .setPhoneNumber(phoneNumber)
                .setAddress(address)
                .setUserDTO(user.toUserDTO());
    }

//    public Information(String id, String nickName, String job, String phoneNumber, String address, User user) {
//        this.id = id;
//        this.nickName = nickName;
//        this.job= job;
//        this.phoneNumber = phoneNumber;
//        this.address = address;
//        this.user = user;
//    }
}

