package com.cg.domain.entity.post;

import com.cg.domain.dto.postDTO.PostTypeDTO;
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
@Table(name = "posts_type", indexes = @Index(columnList = "ts"))
public class PostType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_type")
    private String postType;

    public PostTypeDTO toPostTypeDTO(){
        return new PostTypeDTO()
                .setId(id)
                .setPostType(postType);
    }
}

