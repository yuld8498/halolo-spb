package com.cg.service.post;

import com.cg.cloudinary.CloudinaryUploadUtil;
import com.cg.domain.dto.postDTO.IPostDTO;
import com.cg.domain.dto.postDTO.PostCreateDTO;
import com.cg.domain.dto.postDTO.PostDTO;
import com.cg.domain.entity.media.MediaCover;
import com.cg.domain.entity.media.MediaPost;
import com.cg.domain.entity.media.MediaProfile;
import com.cg.domain.entity.post.Post;
import com.cg.domain.entity.post.Reaction;
import com.cg.domain.entity.user.FriendList;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.user.UserDetail;
import com.cg.domain.enums.EPostType;
import com.cg.exception.DataInputException;
import com.cg.repository.media.MediaCoverRepository;
import com.cg.repository.media.MediaPostRepository;
import com.cg.repository.media.MediaProfileRepository;
import com.cg.repository.post.PostRepository;
import com.cg.repository.post.ReactionRepository;
import com.cg.repository.user.FriendListRepository;
import com.cg.repository.user.UserDetailRepository;
import com.cg.repository.user.UserRepository;
import com.cg.service.mediaCover.IMediaCoverService;
import com.cg.service.mediaPost.IMediaPostService;
import com.cg.service.mediaProfile.IMediaProfileService;
import com.cg.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Service
@Transactional
public class PostServiceImp implements IPostService {
    @Autowired
    private IMediaPostService mediaPostService;

    @Autowired
    private IMediaProfileService mediaProfileService;

    @Autowired
    private IMediaCoverService mediaCoverService;

    @Autowired
    private MediaPostRepository mediaPostRepository;

    @Autowired
    private MediaCoverRepository mediaCoverRepository;

    @Autowired
    private MediaProfileRepository mediaProfileRepository;

    @Autowired
    private UploadService uploadService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CloudinaryUploadUtil cloudinaryUploadUtil;
    @Autowired
    private FriendListRepository friendListRepository;
    @Autowired
    private ReactionRepository reactionRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAllByDeletedIsFalse();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Post> findById(String id) {
        return postRepository.findById(id);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setDeleted(true);
            postRepository.save(post);
        }
    }

    @Override
    public List<IPostDTO> findAllPostOfUserById(String userId) {
        return postRepository.findAllPostOfUserById(userId);
    }

    @Override
    public List<IPostDTO> findAllPosts(String userId) {
        return postRepository.findAllPost(userId);
    }

    @Override
    public Long countPostByUserId(String userId) {
        return postRepository.countPostsByUserId(userId);
    }

    @Override
    public List<PostDTO> getPostOffset(String offset) {
        User user = userRepository.findByEmail(getPrincipal()).get();
        List<IPostDTO> posts = postRepository.findAllPostOfUserWithRelationshipLimit(user.getId(), offset);
        if (posts.isEmpty()) {
            posts = postRepository.findAllPost(user.getId());
        }
        List<PostDTO> postDTOS = new ArrayList<>();

        for (IPostDTO post : posts) {
            PostDTO postDTO = new PostDTO(post);
            postDTOS.add(postDTO);
        }
        return postDTOS;
    }


    @Override
    public PostDTO createPostWithMediaOfUser(MultipartFile file, PostCreateDTO postCreateDTO) {
        User user = userRepository.findById(postCreateDTO.getCreatedBy()).get();

        postCreateDTO.setUserDTO(user.toUserDTO());

        postCreateDTO.setPostType(EPostType.POST);

        Post post = postRepository.save(postCreateDTO.toPost());

        PostDTO newDto = post.toPostDTO();

        if (file != null) {
            MediaPost mediaPost = new MediaPost();
            int imageWidth;
            int imageHeight;

            try {
                byte[] bytes = file.getBytes();
                InputStream inputStream = new ByteArrayInputStream(bytes);
                BufferedImage image = ImageIO.read(inputStream);
                imageWidth = image.getWidth();
                imageHeight = image.getHeight();
                mediaPost.setWidth(imageWidth);
                mediaPost.setHeight(imageHeight);
            } catch (Exception e) {
            }

            mediaPost.setId(null);
            mediaPost.setPost(post);
            mediaPost.setCaption(post.getContent());
            mediaPostService.save(mediaPost);

            uploadAndSaveMediaPost(file, mediaPost.getId(), mediaPost);

            newDto.setUrlMedia(mediaPost.getFileUrl());
        }

        Post newPost = postRepository.findById(post.getId()).get();

        UserDetail userDetail = userDetailRepository.findByUserId(user.getId());

        newDto.setId(newPost.getId());
        newDto.setCreatedAt(new Date());
        newDto.setCreatedBy(post.getCreatedBy());
        newDto.setUpdatedAt(new Date());
        newDto.setUpdatedBy(post.getUpdatedBy());
        newDto.setFullName(userDetail.getFullName());
        newDto.setAvatarUrl(userDetail.getAvatarFileName());

        FriendList friendList = friendListRepository.findByUserId(user.getId());
        String listFr = friendList.getFriends();
        List<String> listFriendOfUser = new ArrayList<>();
        if (listFr != null) {
            listFriendOfUser = Arrays.asList(listFr.split(","));
        }
        newDto.setFriendsList(listFriendOfUser);

        return newDto;
    }

    @Override
    public PostDTO createPostMediaProfile(MultipartFile file, PostCreateDTO postCreateDTO) {

        User user = userRepository.findById(postCreateDTO.getCreatedBy()).get();

        postCreateDTO.setUserDTO(user.toUserDTO());

        postCreateDTO.setPostType(EPostType.PROFILE);

        Post post = postRepository.save(postCreateDTO.toPost());

        PostDTO newDto = post.toPostDTO();

        MediaProfile mediaProfile = new MediaProfile();
        mediaProfile.setPost(post);
        mediaProfile.setCaption(post.getContent());
        mediaProfile.setId(null);

        int imageWidth;
        int imageHeight;
        try {
            byte[] bytes = file.getBytes();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(inputStream);
            imageWidth = image.getWidth();
            imageHeight = image.getHeight();
            mediaProfile.setHeight(imageHeight);
            mediaProfile.setWidth(imageWidth);
        } catch (Exception e) {
        }

        mediaProfileService.save(mediaProfile);

        uploadAndSaveMediaProfile(file, mediaProfile.getId(), mediaProfile);

        newDto.setUrlMedia(mediaProfile.getFileUrl());

        UserDetail newUserDetail = userDetailRepository.findByUserId(user.getId());
        newUserDetail.setAvatarFileFolder(mediaProfile.getFileFolder());
        newUserDetail.setAvatarFileName(mediaProfile.getFileName());
        newUserDetail.setAvatarFileUrl(mediaProfile.getFileUrl());
        userDetailRepository.save(newUserDetail);

        Post newPost = postRepository.findById(post.getId()).get();

        newDto.setId(newPost.getId());
        newDto.setCreatedAt(new Date());
        newDto.setCreatedBy(post.getCreatedBy());
        newDto.setUpdatedAt(new Date());
        newDto.setUpdatedBy(post.getUpdatedBy());
        newDto.setFullName(newUserDetail.getFullName());
        newDto.setAvatarFolder(mediaProfile.getFileFolder());
        newDto.setAvatarName(mediaProfile.getFileName());
        newDto.setAvatarUrl(mediaProfile.getFileUrl());

        FriendList friendList = friendListRepository.findByUserId(user.getId());
        String listFr = friendList.getFriends();
        List<String> listFriendOfUser = new ArrayList<>();
        if (listFr != null) {
            listFriendOfUser = Arrays.asList(listFr.split(","));
        }
        newDto.setFriendsList(listFriendOfUser);
        return newDto;
    }

    @Override
    public PostDTO createPostMediaCover(MultipartFile file, PostCreateDTO postCreateDTO) {

        User user = userRepository.findById(postCreateDTO.getCreatedBy()).get();

        postCreateDTO.setUserDTO(user.toUserDTO());
        postCreateDTO.setPostType(EPostType.COVER);

        Post post = postRepository.save(postCreateDTO.toPost());

        PostDTO newDto = post.toPostDTO();
        Optional<MediaCover> mediaCover1 = mediaCoverService.findByUserIdAndUsedIsFalse(user.getId());

        MediaCover mediaCover = new MediaCover();
        mediaCover.setId(null);
        mediaCover.setPost(post);
        mediaCover.setCaption(post.getContent());

        int imageWidth;
        int imageHeight;
        try {
            byte[] bytes = file.getBytes();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(inputStream);
            imageWidth = image.getWidth();
            imageHeight = image.getHeight();
            mediaCover.setHeight(imageHeight);
            mediaCover.setWidth(imageWidth);
        } catch (Exception e) {
        }

        mediaCoverRepository.save(mediaCover);

        uploadAndSaveMediaCover(file, mediaCover.getId(), mediaCover);

        newDto.setUrlMedia(mediaCover.getFileUrl());


        if (mediaCover1.isPresent()) {
            MediaCover useMediaCover = mediaCover1.get();
            useMediaCover.setUsed(true);
            mediaCoverRepository.save(useMediaCover);
            mediaCoverRepository.save(mediaCover);
        }

        Post newPost = postRepository.findById(post.getId()).get();
        UserDetail userDetail = userDetailRepository.findByUserId(user.getId());

        newDto.setId(newPost.getId());
        newDto.setCreatedAt(new Date());
        newDto.setCreatedBy(post.getCreatedBy());
        newDto.setUpdatedAt(new Date());
        newDto.setUpdatedBy(post.getUpdatedBy());
        newDto.setFullName(userDetail.getFullName());
        newDto.setAvatarUrl(userDetail.getAvatarFileName());
        newDto.setAvatarFolder(userDetail.getAvatarFileFolder());
        newDto.setAvatarName(userDetail.getAvatarFileName());

        FriendList friendList = friendListRepository.findByUserId(user.getId());
        String listFr = friendList.getFriends();
        List<String> listFriendOfUser = new ArrayList<>();
        if (listFr != null) {
            listFriendOfUser = Arrays.asList(listFr.split(","));
        }
        newDto.setFriendsList(listFriendOfUser);
        return newDto;
    }

    @Override
    public List<PostDTO> getAllPostOfUser(String userId) {
        Optional<User> user = userRepository.findByEmail(getPrincipal());
        String userIdPrincipalId = user.get().getId();
        List<IPostDTO> posts = postRepository.findAllPostOfAnotherUserById( userId,userIdPrincipalId);
        List<PostDTO> postDTOS = new ArrayList<>();

        for (IPostDTO post : posts) {
            PostDTO postDTO = new PostDTO(post);
            postDTOS.add(postDTO);

//            UserDTO userDTO = post.toUserDTO();
//            userDTO.setId(post.getId());
//            userDTO.setAvatarUrl(post.getMediaCoverUrl());
//            userDTO.setFullName(post.getFullName());
//
//            PostTypeDTO postTypeDTO = post.toPostTypeDTO();
//            PostDTO newPostDto = post.toPostDTO();
//
//            newPostDto.setId(post.getId());
//            newPostDto.setUserDTO(userDTO);
//            newPostDto.setPostTypeDTO(postTypeDTO);
//            newPostDto.setContent(post.getContents());
////            newPostDto.setDeleted(post.getDeleted());
//            newPostDto.setCreatedAt(post.getCreatedAt());
//            newPostDto.setCreatedBy(post.getCreatedBy());
//            newPostDto.setUpdatedAt(post.getUpdatedAt());
//            newPostDto.setUpdatedBy(post.getUpdatedBy());
//            newPostDto.setUrlMedia(post.getUrlMedia());
//            newPostDto.setCountReaction(post.getCountReactions());

//            if (post.getLiked() == 0) {
//                newPostDto.setLiked(false);
//            } else {
//                newPostDto.setLiked(true);
//            }
//            postDTOS.add(newPostDto);
        }
        return postDTOS;
    }

    @Override
    public List<PostDTO> getAllPost() {
        User user = userRepository.findByEmail(getPrincipal()).get();
        List<IPostDTO> posts = postRepository.findAllPost(user.getId());
        if (posts.isEmpty()) {
            posts = postRepository.findAllPost(user.getId());
        }
        List<PostDTO> postDTOS = new ArrayList<>();

        for (IPostDTO post : posts) {
            PostDTO postDTO = new PostDTO(post);

            FriendList friendList = friendListRepository.findByUserId(post.getUserId());
            String listFr = friendList.getFriends();
            if (listFr != null) {
                List<String> listFriendOfUser = Arrays.asList(listFr.split(","));
                postDTO.setFriendsList(listFriendOfUser);
            }
            postDTOS.add(postDTO);
        }
        return postDTOS;
    }

    private String getPrincipal() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = "";
        }

        return username;
    }

    private void uploadAndSaveMediaPost(MultipartFile file, String postId, MediaPost mediaPost) {
        try {
            Map uploadResult = uploadService.uploadImage(file, cloudinaryUploadUtil.buildImageUploadParams(postId, cloudinaryUploadUtil.IMAGE_UPLOAD_FOLDER_POST_MEDIA));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            System.out.println(fileUrl);

            mediaPost.setFileName(mediaPost.getId() + "." + fileFormat);
            mediaPost.setFileUrl(fileUrl);
            mediaPost.setFileFolder(cloudinaryUploadUtil.IMAGE_UPLOAD_FOLDER_POST_MEDIA);
            mediaPostRepository.save(mediaPost);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    private void uploadAndSaveMediaCover(MultipartFile file, String postId, MediaCover mediaCover) {
        try {
            Map uploadResult = uploadService.uploadImage(file, cloudinaryUploadUtil.buildImageUploadParams(postId, cloudinaryUploadUtil.IMAGE_UPLOAD_FOLDER_POST_COVER));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            mediaCover.setFileName(mediaCover.getId() + "." + fileFormat);
            mediaCover.setFileUrl(fileUrl);
            mediaCover.setFileFolder(cloudinaryUploadUtil.IMAGE_UPLOAD_FOLDER_POST_COVER);
            mediaCoverRepository.save(mediaCover);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    private void uploadAndSaveMediaProfile(MultipartFile file, String postId, MediaProfile mediaProfile) {
        try {
            Map uploadResult = uploadService.uploadImage(file, cloudinaryUploadUtil.buildImageUploadParams(postId, cloudinaryUploadUtil.IMAGE_UPLOAD_FOLDER_POST_PROFILE));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            mediaProfile.setFileName(mediaProfile.getId() + "." + fileFormat);
            mediaProfile.setFileUrl(fileUrl);
            mediaProfile.setFileFolder(cloudinaryUploadUtil.IMAGE_UPLOAD_FOLDER_POST_PROFILE);
            mediaProfileRepository.save(mediaProfile);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    public PostDTO updatePost(PostCreateDTO postCreateDTO) {

        Post post = postRepository.findById(postCreateDTO.getId()).get();

        post.setContent(postCreateDTO.getContent());
        post.setCreatedAt(new Date());
        post.setCreatedBy(post.getCreatedBy());
        post.setUpdatedAt(new Date());
        post.setUpdatedBy(post.getUpdatedBy());

        PostDTO postDTO = postRepository.save(post).toPostDTO();

        EPostType ePostType = post.getPostType();

        switch (ePostType) {
            case POST:
                Optional<MediaPost> mediaPostOptional = mediaPostService.findByPost(post);

                if (mediaPostOptional.isPresent()) {
                    postDTO.setFileFolder(mediaPostOptional.get().getFileFolder());
                    postDTO.setFileName(mediaPostOptional.get().getFileName());
                    postDTO.setUrlMedia(mediaPostOptional.get().getFileUrl());
                    postDTO.setWidth(mediaPostOptional.get().getWidth());
                    postDTO.setHeight(mediaPostOptional.get().getHeight());
                }

                break;
            case PROFILE:
                Optional<MediaProfile> mediaProfileOptional = mediaProfileService.findByPost(post);

                if (mediaProfileOptional.isPresent()) {
                    postDTO.setFileFolder(mediaProfileOptional.get().getFileFolder());
                    postDTO.setFileName(mediaProfileOptional.get().getFileName());
                    postDTO.setUrlMedia(mediaProfileOptional.get().getFileUrl());
                    postDTO.setWidth(mediaProfileOptional.get().getWidth());
                    postDTO.setHeight(mediaProfileOptional.get().getHeight());
                }

                break;
            case COVER:
                Optional<MediaCover> mediaCoverOptional = mediaCoverService.findByPost(post);

                if (mediaCoverOptional.isPresent()) {
                    postDTO.setFileFolder(mediaCoverOptional.get().getFileFolder());
                    postDTO.setFileName(mediaCoverOptional.get().getFileName());
                    postDTO.setUrlMedia(mediaCoverOptional.get().getFileUrl());
                    postDTO.setWidth(mediaCoverOptional.get().getWidth());
                    postDTO.setHeight(mediaCoverOptional.get().getHeight());
                }

                break;
        }

        FriendList friendList = friendListRepository.findByUserId(post.getUser().getId());
        String listFr = friendList.getFriends();
        List<String> listFriendOfUser = Arrays.asList(listFr.split(","));
        postDTO.setFriendsList(listFriendOfUser);

        return postDTO;
    }
}
