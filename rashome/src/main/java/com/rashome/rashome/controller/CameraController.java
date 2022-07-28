package com.rashome.rashome.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.sardine.impl.SardineException;
import com.rashome.rashome.dto.CameraInfo;
import com.rashome.rashome.po.CameraData;
import com.rashome.rashome.service.CameraDataService;
import com.rashome.rashome.service.NextcloudService;
import com.rashome.rashome.utils.CommonConvert;
import com.rashome.rashome.utils.TimestampUtils;

@RestController
@RequestMapping(value = "/sensors/camera")
public class CameraController {

    private NextcloudService nextcloudService;
    private CameraDataService cameraDataService;

    public CameraController(
        NextcloudService nextcloudService,
        CameraDataService cameraDataService) {
            this.nextcloudService = nextcloudService;
            this.cameraDataService = cameraDataService;
    }

    @PostMapping(value = "/data")
    public void uploadImage(MultipartFile[] files) 
    throws JsonMappingException, JsonProcessingException, IOException, SardineException{

        CameraInfo cameraInfo = CommonConvert.multipartJsonFileToObject(files[1], CameraInfo.class);

        var foldersName = new String[]{
            Long.toString(cameraInfo.getRasberryPiID()), 
            Long.toString(cameraInfo.getSensorID()),
            TimestampUtils.timestampToYear(cameraInfo.getCollectTime()),
            TimestampUtils.timestampToMonthOfYear(cameraInfo.getCollectTime()),
            TimestampUtils.timestampToDayOfYear(cameraInfo.getCollectTime())
        };

        var fileName = TimestampUtils.timestampToSecondOfYear(cameraInfo.getCollectTime()) + ".jpeg";

        var cameraData = new CameraData(cameraInfo);

        cameraData.setImageUrl(this.nextcloudService.appendFileUrl(foldersName, fileName));

        nextcloudService.uploadFile(
            foldersName,
            fileName, 
            files[0].getBytes()
            );

        cameraDataService.addRecord(cameraData);
    }
}
