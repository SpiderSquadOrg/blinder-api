package com.blinder.api.image.controller;
import com.blinder.api.image.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/images")
@RestController
public class ImageController {
    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    // TO DO: Security
    public String uploadImage(@RequestParam("image") MultipartFile image,
                              @RequestParam("publicId") String publicId) {
        try {
            byte[] imageBytes = image.getBytes();
            return cloudinaryService.uploadImage(imageBytes, publicId);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error uploading image.";
        }
    }

    @GetMapping("/{publicId}")
    // TO DO: Security
    public String getImageUrl(@PathVariable(name ="publicId") String publicId) {
        try {
            return cloudinaryService.getImageUrl(publicId);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error getting image URL.";
        }
    }
}
