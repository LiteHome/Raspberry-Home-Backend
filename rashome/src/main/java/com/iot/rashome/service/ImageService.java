package com.iot.rashome.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import com.iot.rashome.commons.exception.IotBackendException;
import com.iot.rashome.commons.util.DateUtil;
import com.iot.rashome.vo.DeviceDataVO;

@Service
public class ImageService {

    @Value("${web.dav.url}")
    private String webDavUrl;

    @Value("${web.dav.image.format}")
    private String imageFormat;
    
    private Sardine sardine;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter timeStampFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final Decoder decoder = Base64.getDecoder();

    public ImageService(@Value("${web.dav.user}") String webDavUser, @Value("${web.dav.password}") String webDavPassword) {
        this.sardine = SardineFactory.begin(webDavUser, webDavPassword);
    }


    public String imageToUrl(DeviceDataVO deviceDataVO) throws IotBackendException {
        
        if (ObjectUtils.isEmpty(sardine)) {
            throw new IotBackendException("web dav 客户端没有初始化");
        }

        if (StringUtils.isBlank(imageFormat)) {
            imageFormat = "jpeg";
        }

        String fileName;
        try {
            String uploadPath = createDirectoryWithCurrentDateAndDeviceId(deviceDataVO.getDeviceId());

            String prefixDate = DateUtil.getCurDateAndFormatted(timeStampFormatter);
            String uuid = UUID.randomUUID().toString();
            String suffix = "." + imageFormat;
            fileName = uploadPath + prefixDate + "-" + uuid + suffix;
        } catch (IOException e) {
            throw new IotBackendException("创建目录失败", e);
        }

        byte[] imageBytesArray;
        try {
            imageBytesArray = resizeImageFromBase64String(deviceDataVO.getCameraImageBase64());
        } catch (IotBackendException e) {
            throw new IotBackendException("图片转换失败", e);
        }

        try {
            String contentType = "image/" + imageFormat;
            sardine.put(fileName, imageBytesArray, contentType);
        } catch (IOException e) {
            throw new IotBackendException("上传图片失败", e);
        }

        return fileName;
    }

    private byte[] resizeImageFromBase64String(String base64String) throws IotBackendException {

        byte[] imageBytesArray = decoder.decode(base64String);;

        BufferedImage image;
        try {
            image = ImageIO.read(new ByteArrayInputStream(imageBytesArray));
        } catch (IOException e) {
            throw new IotBackendException("无法读取 byte array 到图片", e);
        }

        BufferedImage resizedImage = Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.AUTOMATIC, 640, 480, Scalr.OP_ANTIALIAS);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(resizedImage, "jpeg", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new IotBackendException("无法写入图片到 jpg byte array", e);
        }
    }

    private String createDirectoryWithCurrentDateAndDeviceId(Long deviceId) throws IOException {

        String[] directoryNames = new String[3];

        directoryNames[0] = "tmp";
        directoryNames[1] = String.valueOf(deviceId);
        directoryNames[2] = DateUtil.getCurDateAndFormatted(dateFormatter);

        return createDirectoryIfNotExist(webDavUrl, directoryNames);
    }

    private String createDirectoryIfNotExist(String webDavUrl, String ...directoryNames) throws IOException {

        for (String directoryName : directoryNames) {

            if (!isDirectoryExist(webDavUrl, directoryName)) {
                createDirectory(webDavUrl, directoryName);
            }
            webDavUrl = cd(webDavUrl, directoryName);
        }

        return webDavUrl;
    }

    private boolean isDirectoryExist(String webDavUrl, String directoryName) throws IOException {
            
        List<DavResource> resourcesList = sardine.list(webDavUrl);
        for (DavResource resources : resourcesList) {
            if(resources.isDirectory() && resources.getName().equals(directoryName)) {
                return true;
            }
        }

        return false;
    }

    private String cd(String webDavUrl, String directoryName) {

        directoryName = directoryName + "/";

        if (webDavUrl.endsWith("/")) {
            return webDavUrl + directoryName;
        }

        return webDavUrl + "/" + directoryName;

    }

    private void createDirectory(String webDavUrl, String directoryName) throws IOException {

        directoryName = directoryName + "/";

        if (webDavUrl.endsWith("/")) {
            sardine.createDirectory(webDavUrl + directoryName);
        } else {
            sardine.createDirectory(webDavUrl + "/" + directoryName);
        }

    }
}
