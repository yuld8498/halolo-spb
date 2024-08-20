package com.cg.api;

import com.cg.domain.dto.mediaDTO.MediaCoverDTO;
import com.cg.domain.dto.mediaDTO.MediaPostDTO;
import com.cg.domain.dto.mediaDTO.MediaProfileDTO;
import com.cg.domain.entity.media.MediaCover;
import com.cg.domain.entity.media.MediaPost;
import com.cg.domain.entity.media.MediaProfile;
import com.cg.domain.entity.post.Post;
import com.cg.service.mediaCover.IMediaCoverService;
import com.cg.service.mediaPost.MediaPostServiceImp;
import com.cg.service.mediaProfile.MediaProfileServiceImp;
import com.cg.service.post.PostServiceImp;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/medias")
public class MediaAPI {


    public static ArrayList<MediaProfileDTO> listAllMediaProfiles = new ArrayList<>();

    public static ArrayList<MediaPostDTO> listAllMediaPosts = new ArrayList<>();

    @Autowired
    private IMediaCoverService mediaCoverService;

    @Autowired
    private MediaProfileServiceImp mediaProfileService;

    @Autowired
    private PostServiceImp postService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private MediaPostServiceImp mediaPostService;

    @GetMapping()
    public ResponseEntity<?> getAllMedia(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cover/create")
    public ResponseEntity<?> doCreateMediaCover(@RequestBody MediaCoverDTO mediaCoverDTO) {


        try {
            MediaCoverDTO newMediaCoverDTO = mediaCoverService.createMediaCover(mediaCoverDTO);

            return new ResponseEntity<>(newMediaCoverDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Server không xử lý được", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/profile/create")
    public ResponseEntity<?> doCreateMediaProfile(@RequestBody MediaProfileDTO mediaProfileDTO) {

        try {
            MediaProfileDTO newMediaProfileDTO = mediaProfileService.createMediaProfile(mediaProfileDTO);

            listAllMediaProfiles.add(newMediaProfileDTO);

            return new ResponseEntity<>(newMediaProfileDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Server không xử lý được", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/post/create")
    public ResponseEntity<?> doCreateMediaPost(@Validated @RequestBody MediaPostDTO mediaPostDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        try {
            Post post = new Post();
            post.setUser(mediaPostDTO.getPostCreateDTO().toPost().getUser());
            Post newPost = postService.save(post);
            MediaPost mediaPost = mediaPostDTO.toMediaPost();
            mediaPost.setPost(newPost);
            MediaPost newMediaPost = mediaPostService.save(mediaPost);

            return new ResponseEntity<>(newMediaPost.toMediaPostDTO(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Server không xử lý được", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cover/block/{id}")
    public ResponseEntity<?> doDeleteMediaCover(@PathVariable String id) {
        Optional<MediaCover> mediaCoverOptional = mediaCoverService.findById(id);

        if (!mediaCoverOptional.isPresent()) {
            return new ResponseEntity<>("Media Cover is null!", HttpStatus.NO_CONTENT);
        }

        try {
            mediaCoverOptional.get().setDeleted(true);
            mediaCoverService.save(mediaCoverOptional.get());

            MediaCoverDTO newMediaCoverDTO = new MediaCoverDTO();

            return new ResponseEntity<>("Delete is success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server can't handle!", HttpStatus.BAD_REQUEST);
        }
//        if (mediaCoverOptional.isPresent()) {
//            try {
//                mediaCoverOptional.get().setDeleted(true);
//                mediaCoverService.save(mediaCoverOptional.get());
//
//                return new ResponseEntity<>(HttpStatus.ACCEPTED);
//            } catch (DataIntegrityViolationException e) {
//                throw new DataInputException("Xoá ảnh bìa không thành công.");
//            }
//        } else {
//            throw new DataInputException("Không hợp lệ!");
//        }
    }

    @DeleteMapping("/profile/block/{id}")
    public ResponseEntity<?> doDeleteMediaProfile(@PathVariable String id) {
        Optional<MediaProfile> mediaProfileOptional = mediaProfileService.findById(id);

        if (!mediaProfileOptional.isPresent()) {
            return new ResponseEntity<>("Media Cover is null!", HttpStatus.NO_CONTENT);
        }

        try {
            mediaProfileOptional.get().setDeleted(true);
            mediaProfileService.save(mediaProfileOptional.get());

            MediaProfileDTO newMediaProfileDTO = new MediaProfileDTO();

            for (MediaProfileDTO mediaProfileDTO : listAllMediaProfiles) {
                if (mediaProfileDTO.getId().equals(mediaProfileOptional.get().getId())) {
                    newMediaProfileDTO = mediaProfileDTO;
                }
            }

            listAllMediaProfiles.remove(newMediaProfileDTO);

            return new ResponseEntity<>("Delete is success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server can't handle!", HttpStatus.BAD_REQUEST);
        }
//        Optional<MediaProfile> mediaProfileOptional = mediaProfileRepository.findById(id);
//
//        if (mediaProfileOptional.isPresent()) {
//            try {
//                mediaProfileOptional.get().setDeleted(true);
//                mediaProfileService.save(mediaProfileOptional.get());
//
//                return new ResponseEntity<>(HttpStatus.ACCEPTED);
//            } catch (DataIntegrityViolationException e) {
//                throw new DataInputException("Xoá ảnh đại diện không thành công.");
//            }
//        } else {
//            throw new DataInputException("Không hợp lệ!");
//        }
    }

    @DeleteMapping("/post/block/{id}")
    public ResponseEntity<?> doDeleteMediaPost(@PathVariable String id) {
//        Optional<MediaPost> mediaPostOptional = mediaPostRepository.findById(id);
//
//        if (mediaPostOptional.isPresent()) {
//            try {
//                mediaPostOptional.get().setDeleted(true);
//                mediaPostService.save(mediaPostOptional.get());
//
//                return new ResponseEntity<>(HttpStatus.ACCEPTED);
//            } catch (DataIntegrityViolationException e) {
//                throw new DataInputException("Xoá ảnh không thành công.");
//            }
//        } else {
//            throw new DataInputException("Deleted Media Post Invalid");
//        }
        Optional<MediaPost> mediaPostOptional = mediaPostService.findById(id);

        if (!mediaPostOptional.isPresent()) {
            return new ResponseEntity<>("Media Cover is null!", HttpStatus.NO_CONTENT);
        }

        try {
            mediaPostOptional.get().setDeleted(true);
            mediaPostService.save(mediaPostOptional.get());

            MediaPostDTO newMediaPostDTO = new MediaPostDTO();

            for (MediaPostDTO mediaPostDTO : listAllMediaPosts) {
                if (mediaPostDTO.getId().equals(mediaPostOptional.get().getId())) {
                    newMediaPostDTO = mediaPostDTO;
                }
            }

            listAllMediaPosts.remove(newMediaPostDTO);

            return new ResponseEntity<>("Delete is success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server can't handle!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cover/{userId}")
    public ResponseEntity<?> getAllMediaCoverByUserId(@PathVariable String userId) {
        try {
            List<MediaCoverDTO> mediaCoverDTOS = mediaCoverService.getAllMediaCoverByUserId(userId);

            return new ResponseEntity<>(mediaCoverDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cover/use/{userId}")
    public ResponseEntity<?> getMediaCoverUseByUserId(@PathVariable String userId) {
        Optional<MediaCover> mediaCovers = mediaCoverService.findByUserIdAndUsedIsFalse(userId);

        if (mediaCovers.isPresent()) {
            return new ResponseEntity<>(mediaCovers.get().toMediaCoverDTO(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getAllMediaProfileByUserId(@PathVariable String userId) {
        try {
            List<MediaProfileDTO> mediaProfileDTOS = mediaProfileService.getAllMediaProfileByUserId(userId);

            return new ResponseEntity<>(mediaProfileDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/post/{userId}")
    public ResponseEntity<?> getAllMediaPostByUserId(@PathVariable String userId) {
        List<MediaPostDTO> mediaPosts = mediaPostService.findMediaPostByUserId(userId);
        return new ResponseEntity<>(mediaPosts, HttpStatus.OK);
    }
}

