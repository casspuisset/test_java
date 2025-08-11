package com.api.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImageService {

    private final Cloudinary cloudinary;

    public ImageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    // upload image and retrieve the new url to use it
    public String uploadImage(MultipartFile image) {
        try {
            Map params1 = ObjectUtils.asMap(
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true);
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = cloudinary.uploader().upload(image.getBytes(), params1);
            return uploadResult.get("url").toString();
        } catch (Exception e) {
            return null;
        }

    }
}
