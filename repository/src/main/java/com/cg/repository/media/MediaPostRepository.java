package com.cg.repository.media;

import com.cg.domain.dto.mediaDTO.MediaPostDTO;
import com.cg.domain.entity.media.MediaPost;
import com.cg.domain.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MediaPostRepository extends JpaRepository<MediaPost, String> {
    @Query("SELECT NEW com.cg.domain.dto.mediaDTO.MediaPostDTO (" +
                "mp.id, " +
                "mp.fileName, " +
                "mp.fileFolder, " +
                "mp.fileUrl," +
                "mp.caption," +
                "mp.width, " +
                "mp.height " +
            ") " +
            "FROM MediaPost AS mp " +
            "JOIN Post AS p " +
            "ON mp.post.id = p.id " +
            "WHERE mp.post.id = p.id " +
            "AND p.user.id=:userId " +
            "AND p.deleted = false "
    )
    List<MediaPostDTO> findMediaPostByUserId(String userId);

    Optional<MediaPost> findByPost(Post post);
}
