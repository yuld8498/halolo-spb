package com.cg.repository.media;

import com.cg.domain.entity.media.MediaProfile;
import com.cg.domain.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MediaProfileRepository extends JpaRepository<MediaProfile, String> {

    Optional<MediaProfile> findByPost(Post post);


    @Query(value =
            "SELECT " +
                "mp.id, " +
                "mp.created_at, " +
                "mp.created_by, " +
                "mp.updated_at, " +
                "mp.updated_by, " +
                "mp.deleted, " +
                "mp.caption, " +
                "mp.file_name, " +
                "mp.file_folder, " +
                "mp.file_url, " +
                "p.id AS post_id, " +
                "mp.ts, " +
                "mp.used," +
                "mp.width," +
                "mp.height " +
            "FROM media_profile AS mp " +
            "JOIN posts AS p " +
            "ON mp.post_id = p.id " +
            "WHERE p.user_id=:userId "
            , nativeQuery = true
    )
    List<MediaProfile> findMediaProfilesByUserId(@Param("userId") String userId);

}
