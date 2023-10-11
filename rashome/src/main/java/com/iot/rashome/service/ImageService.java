package com.iot.rashome.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.ObjectUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import com.iot.rashome.commons.exception.IotBackendException;
import com.iot.rashome.commons.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageService {

    @Value("${web.dav.url}")
    private String webDavUrl;

    @Value("${web.dav.image.format}")
    private String imageFormat;
    
    private Sardine sardine;

    private static final Decoder decoder = Base64.getDecoder();

    // 目录格式, 形如 /tmp/{deviceId}/{yyyy-mm-dd}
    private static final String DIRECTORY_FORMATER = "tmp/%d/%tF";

    // 文件格式, 形如 HH:MM:SS-{uuid}.{文件格式}
    private static final String FILE_NAME_FORMATER = "%tT-%s.%s";

    private static final String URL_SPLITER = "/";

    private static final String CONTENT_TYPE_FORMATER = "%s/%s";

    public ImageService(@Value("${web.dav.user}") String webDavUser, @Value("${web.dav.password}") String webDavPassword) {
        this.sardine = SardineFactory.begin(webDavUser, webDavPassword);
    }


    public String imageToUrl(Long deviceId, String imageBase64String) throws IotBackendException {
        // 判断 webdav client 是否初始化
        if (ObjectUtils.isEmpty(sardine)) {
            throw new IotBackendException("web dav 客户端没有初始化");
        }
        // 创建目录, 形如 /tmp/{deviceId}/{yyyy-mm-dd}
        String targetDirectory = DIRECTORY_FORMATER.formatted(
            deviceId, 
            DateUtil.getCurDate());
        try {
            this.createDirectoryWithWebDavUlr(this.webDavUrl, targetDirectory);
        } catch (IOException e) {
            throw new IotBackendException("创建目录失败", e);
        }
        // 创建文件名, 形如 hh:mm:ss-uuid.jpeg
        String fileName = FILE_NAME_FORMATER.formatted(DateUtil.getCurDate(), UUID.randomUUID().toString(), imageFormat);
        // 拼接上传的url, 形如 {host}/tmp/{deviceId}/{yyyy-mm-dd}/{hh:mm:ss}-{uuid}.jpeg
        String completeUrl = this.webDavUrl  + targetDirectory + URL_SPLITER + fileName;
        // 缩放图片
        byte[] imageBytesArray;
        try {
            imageBytesArray = resizeImageFromBase64String(imageBase64String);
        } catch (IotBackendException e) {
            throw new IotBackendException("图片转换失败", e);
        }
        // 设置元数据, 上传图片
        try {
            String contentType = CONTENT_TYPE_FORMATER.formatted("image", imageFormat);
            sardine.put(completeUrl, imageBytesArray, contentType);
        } catch (IOException e) {
            throw new IotBackendException("上传图片失败", e);
        }

        return fileName;
    }

    private byte[] resizeImageFromBase64String(String base64String) throws IotBackendException {
        // base64 -> bytes
        byte[] imageBytesArray = decoder.decode(base64String);;
        // bytes -> resized image
        BufferedImage resizedImage;
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytesArray));
            resizedImage = Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.AUTOMATIC, 640, 480, Scalr.OP_ANTIALIAS);
        } catch (IOException e) {
            throw new IotBackendException("无法读取 byte array 到图片", e);
        }
        // resized image -> bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(resizedImage, imageFormat, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new IotBackendException(String.format("无法写入图片到 %s byte array", imageFormat), e);
        }
    }

    private void createDirectoryWithWebDavUlr(String webDavUrl, String directoryPath) throws IOException {
        // 循环创建目录
        String[] directoryNames = directoryPath.split(URL_SPLITER);
        // 没有就创建, 有就跳过
        for (String singleDirectoryName : directoryNames) {
            // 判断当前目录, 是否存在
            if (!isDirectoryExist(webDavUrl, singleDirectoryName)) {
                createDirectory(webDavUrl, singleDirectoryName);
            }
            // 创建后追加目录名
            webDavUrl = cd(webDavUrl, singleDirectoryName);
        }
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
        directoryName = directoryName + URL_SPLITER;
        if (webDavUrl.endsWith(URL_SPLITER)) {
            return webDavUrl + directoryName;
        }
        return webDavUrl + URL_SPLITER + directoryName;
    }

    private void createDirectory(String webDavUrl, String directoryName) throws IOException {
        directoryName = directoryName + URL_SPLITER;
        if (webDavUrl.endsWith(URL_SPLITER)) {
            sardine.createDirectory(webDavUrl + directoryName);
        } else {
            sardine.createDirectory(webDavUrl + URL_SPLITER + directoryName);
        }
    }
}
