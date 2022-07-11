package com.rashome.rashome.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rashome.rashome.dto.CameraInfo;

@RestController
@RequestMapping(value = "/sensors/camera")
public class CameraController {

    @PostMapping(value = "/data")
    public void uploadImage(MultipartFile[] files){

            // System.out.println(files.getSize());

        for (MultipartFile file : files){

            String name = file.getOriginalFilename();

            if ("image".equals(name)) {
                System.out.println(name + file.getSize());
            }else if ("json".equals(name)) {
                System.out.println(name + file.getSize());
            }
        }
    }

    @PostMapping(value = "/datatest")
    public void uploadImageTest(
        @RequestParam(value = "files") MultipartFile[] files, 
        @RequestParam(value = "cameraInfo") String cameraInfo){

        for (MultipartFile file : files){
            System.out.println(file.getOriginalFilename());
        }
        System.out.println(cameraInfo);
    }
}
