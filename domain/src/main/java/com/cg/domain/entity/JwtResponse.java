package com.cg.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {

    private String id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String name;
    private Collection<? extends GrantedAuthority> roles;
    private String fullName;

    private String avatarName;
    private String avatarFolder;
    private String avatarUrl;
    private String genderName;
    private Date dob;

    public JwtResponse(String accessToken, String id, String username, String name, Collection<? extends GrantedAuthority> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.name = name;
        this.roles = roles;
    }

    public JwtResponse( String token,String id,  String username, String name,
                       Collection<? extends GrantedAuthority> roles, String fullName,
                       String avatarName, String avatarFolder, String avatarUrl, String genderName, Date dob) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.name = name;
        this.roles = roles;
        this.fullName = fullName;
        this.avatarName = avatarName;
        this.avatarFolder = avatarFolder;
        this.avatarUrl = avatarUrl;
        this.genderName = genderName;
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", roles=" + roles +
                '}';
    }
}
