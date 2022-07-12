package com.rashome.rashome.utils;

import java.io.IOException;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonConvert {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T multipartJsonFileToObject(
        MultipartFile json, Class<T> template) throws JsonMappingException, JsonProcessingException, IOException{
            return objectMapper.readValue(new String(json.getBytes()), template);
    }

    public static String authInfoToBasicAuth(String name, String password){

        String combine = name + ":" + password;

        return "Basic" + " " + Base64.getEncoder().encodeToString(combine.getBytes());
    }
}
