//package com.cg.domain.dto.postDTO;
//
//import com.cg.domain.dto.userDTO.UserDTO;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class PostDbDTO implements IPostDTO {
//    private String id;
//    private Date createdAt;
//    private String createdBy;
//    private String email;
//    private Date updatedAt;
//    private String updatedBy;
//    private Boolean deleted;
//    private Long ts;
//    private String content;
//    private Long postTypeId;
//    private String postType;
//    private String urlMedia;
//    private String userId;
//    private String mediaCoverUrl;
//    private String fullName;
//    private Long countReactions;
//    private boolean liked;
//
//    public UserDTO toUserDTO(){
//        return new UserDTO()
//                .setId(getUserId())
//                .setAvatarUrl(getAvatarUrl())
//                .setFullName(getFullName());
//    }
//
//    public PostTypeDTO toPostTypeDTO(){
//        return new PostTypeDTO()
//                .setId(getPostTypeId())
//                .setPostType(getPostType());
//    }
//
//    public PostDTO toPostDTO(){
//        return new PostDTO()
//                .setId(getId())
//                .setUserDTO(toUserDTO())
//                .setPostTypeDTO(toPostTypeDTO())
//                .setContent(getContents())
//                .setCreatedAt(getCreatedAt())
//                .setCreatedBy(getCreatedBy())
//                .setUpdatedBy(getUpdatedBy())
//                .setUpdatedAt(getUpdatedAt())
//                .setUrlMedia(getUrlMedia())
//                .setLiked(liked)
//                .setCountReaction(getCountReactions());
//    }
//
//    @Override
//    public String getId() {
//        return id;
//    }
//
//    @Override
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    @Override
//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    @Override
//    public String getEmail() {
//        return email;
//    }
//
//    @Override
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    @Override
//    public String getUpdatedBy() {
//        return updatedBy;
//    }
//
//    @Override
//    public boolean isDeleted() {
//        return deleted;
//    }
//
//    @Override
//    public Long getTs() {
//        return ts;
//    }
//
//    @Override
//    public String getContents() {
//        return content;
//    }
//
//    @Override
//    public Long getPostTypeId() {
//        return postTypeId;
//    }
//
//    @Override
//    public String getPostType() {
//        return postType;
//    }
//
//    @Override
//    public String getUrlMedia() {
//        return urlMedia;
//    }
//
////    @Override
////    public String getType() {
////        return postType;
////    }
//
//    @Override
//    public String getUserId() {
//        return userId;
//    }
//
//    @Override
//    public String getUsed() {
//        return null;
//    }
//
//    @Override
//    public String getAvatarUrl() {
//        return mediaCoverUrl;
//    }
//
//    @Override
//    public String getFullName() {
//        return fullName;
//    }
//
//    @Override
//    public Long getCountReactions() {
//        return countReactions;
//    }
//
//    @Override
//    public boolean isLiked() {
//        return liked;
//    }
//}
