package com.rashome.rashome.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.rashome.rashome.dto.CameraInfo;
import com.rashome.rashome.utils.BytesConvert;
import com.rashome.rashome.utils.CommonConvert;

@RestController
@RequestMapping(value = "/sensors/camera")
public class CameraController {

    @PostMapping(value = "/data")
    public void uploadImage(MultipartFile[] files) throws JsonMappingException, JsonProcessingException, IOException{

            // System.out.println(files.getSize());

        for (MultipartFile file : files){

            String name = file.getOriginalFilename();

            if ("image".equals(name)) {
                System.out.println(name + ":" + BytesConvert.convertToKB(file.getSize()) + " KB");
            }else if ("json".equals(name)) {

                CameraInfo cameraInfo = CommonConvert.multipartJsonFileToObject(file, CameraInfo.class);
                System.out.println(cameraInfo.toString());
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
