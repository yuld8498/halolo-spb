package com.cg.service.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface UploadService {
    Map uploadImage(MultipartFile file, Map options) throws IOException;

    Map destroyImage(String publicId, Map params) throws IOException;

}
