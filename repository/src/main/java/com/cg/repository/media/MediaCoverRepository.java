package com.cg.repository.media;

import com.cg.domain.entity.media.MediaCover;
import com.cg.domain.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MediaCoverRepository extends JpaRepository<MediaCover, String> {

    Optional<MediaCover> findByPost(Post post);


    @Query(value = "" +
            "SELECT " +
                "mc.id, " +
                "mc.created_at, " +
                "mc.created_by, " +
                "mc.updated_at, " +
                "mc.updated_by, " +
                "mc.deleted, " +
                "mc.caption, " +
                "mc.file_name, " +
                "mc.file_folder, " +
                "mc.file_url, " +
                "p.id AS post_id, " +
                "mc.ts, " +
                "mc.used " +
            "FROM halolo.media_cover AS mc " +
            "JOIN halolo.posts AS p " +
            "ON mc.post_id = p.id " +
            "WHERE p.user_id=:userId "
            , nativeQuery = true
    )
    List<MediaCover> findMediaCoversByUserId(@Param("userId") String userId);


    @Query(value = "" +
                "SELECT " +
                "mc.id, " +
                "mc.created_at, " +
                "mc.created_by, " +
                "mc.updated_at, " +
                "mc.updated_by, " +
                "mc.deleted, " +
                "mc.caption, " +
                "mc.file_name, " +
                "mc.file_folder, " +
                "mc.file_url, " +
                "mc.width, " +
                "mc.height, " +
                "p.id AS post_id, " +
                "mc.ts, " +
                "mc.used " +
            "FROM media_cover AS mc " +
            "JOIN posts AS p " +
            "ON mc.post_id = p.id " +
            "WHERE p.user_id=:userId " +
            "AND mc.used = false " +
            "AND mc.deleted = false "
            , nativeQuery = true
    )
    Optional<MediaCover> findMediaCoverUsedByUserId(@Param("userId") String userId);

}
