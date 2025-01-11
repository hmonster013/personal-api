package com.de013.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.de013.service.ImageStorageService;
import com.de013.utils.URI;

@RestController
@RequestMapping(URI.V1 + URI.IMAGE)
public class ImageController extends BaseController{
    static Logger log = LoggerFactory.getLogger(ImageController.class.getName());

    @Autowired
    private ImageStorageService imageStorageService;

    @PostMapping(value= URI.UPLOAD, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadImage(@RequestParam("image") MultipartFile image) {
        try {
            String imageName = imageStorageService.saveFile(image);
            return response(imageName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @GetMapping(URI.VIEW + URI.FILENAME)
    public ResponseEntity viewFile(@PathVariable("filename") String imageName) {
        try {
            byte[] imageData = imageStorageService.readFile(imageName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; imagename=\"" + imageName + "\"")
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping(value = URI.DELETE + URI.FILENAME)
    public ResponseEntity deleteImage(@PathVariable String imageName) {
        try {
            imageStorageService.deleteFile(imageName);
            return response("File deleted successfully: " + imageName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete image");
        }
    }
}
