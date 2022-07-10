package com.rashome.rashome.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/sensors/camera")
public class CameraController {

    @PostMapping(value = "/data")
    public void uploadImage(MultipartFile[] files){

        for (MultipartFile file : files){
            System.out.println(file.getOriginalFilename());
        }
    }
}
