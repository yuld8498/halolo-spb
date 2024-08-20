package com.cg.repository.post;

import com.cg.domain.dto.postDTO.IPostDTO;
import com.cg.domain.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> findPostByUserId(String userId);

    List<Post> findAllByDeletedIsFalse();


    @Query(value = "CALL sp_get_all_posts_of_user_by_id(:userId);", nativeQuery = true)
    List<IPostDTO> findAllPostOfUserById(@Param("userId") String userId);

    @Query(value = "CALL sp_get_post_of_another_user_by_id(:anotherUserId,:userId);", nativeQuery = true)
    List<IPostDTO>findAllPostOfAnotherUserById(@Param("anotherUserId") String anotherUserId, @Param("userId")String userId);


    @Query(value = "CALL sp_get_all_posts(:userId);", nativeQuery = true)
    List<IPostDTO> findAllPost(@Param("userId") String userId);


    @Query(value = "call sp_get_all_post_of_user_in_relationship(:userId);", nativeQuery = true)
    List<IPostDTO> findAllPostOfUserWithRelationship(@Param("userId") String userId);


    @Query(value =
            "SELECT (" +
                "COUNT (p.id)" +
            ") " +
            "FROM Post AS p " +
            "WHERE p.user.id = :userId "
    )
    Long countPostsByUserId(@Param("userId") String userId);


    @Query(value = "call sp_get_all_post_of_user_in_relationship_limit(:userId,:offsets);", nativeQuery = true)
    List<IPostDTO> findAllPostOfUserWithRelationshipLimit(@Param("userId") String userId, @Param("offsets") String offsets);
}
