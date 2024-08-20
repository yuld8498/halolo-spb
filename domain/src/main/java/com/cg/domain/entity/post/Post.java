package com.cg.domain.entity.post;

import com.cg.domain.dto.postDTO.PostCreateDTO;
import com.cg.domain.dto.postDTO.PostDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.user.User;
import com.cg.domain.enums.EPostType;
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
@Table(name = "posts", indexes = @Index(columnList = "ts"))
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "contents", columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

//    @OneToMany
//    private List<MediaPost> mediaPost;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private EPostType postType;


    public PostDTO toPostDTO(){
        return new PostDTO()
                .setId(id)
                .setUserId(user.getId())
                .setEmail(user.getEmail())
                .setCreatedAt(getCreatedAt())
                .setCreatedBy(getCreatedBy())
                .setUpdatedAt(getUpdatedAt())
                .setUpdatedBy(getUpdatedBy())
                .setDeleted(isDeleted())
                .setTs(getTs())
                .setPostType(postType.getValue())
                .setContent(content);
    }

    public PostCreateDTO postCreateDTO(){
        return new PostCreateDTO()
                .setId(id)
                .setContent(content)
                .setUserDTO(user.toUserDTO())
                .setPostType(postType);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", postType=" + postType +
                "} " + super.toString();
    }
}

