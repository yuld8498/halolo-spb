package com.cg.cloudinary;

import com.cg.domain.entity.media.MediaPost;
import com.cg.exception.DataInputException;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class CloudinaryUploadUtil {

    public final String IMAGE_UPLOAD_FOLDER_POST_MEDIA = "post_media";
    public final String IMAGE_UPLOAD_FOLDER_POST_COVER = "post_cover";
    public final String IMAGE_UPLOAD_FOLDER_POST_PROFILE = "post_profile";


    public Map buildImageUploadParams(String postId, String imageFolder) {
        if (postId == null)
            throw new DataInputException("Không thể upload hình ảnh của bài viết chưa được lưu");

        String publicId = String.format("%s/%s", imageFolder, postId);

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

//    public Map buildImageDestroyParams(Product product, String publicId) {
//        if (product == null || product.getId() == null)
//            throw new DataInputException("Không thể destroy hình ảnh của sản phẩm không xác định");
//
//        return ObjectUtils.asMap(
//                "public_id", publicId,
//                "overwrite", true,
//                "resource_type", "image"
//        );
//    }

}

