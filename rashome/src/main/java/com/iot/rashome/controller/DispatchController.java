package com.iot.rashome.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class DispatchController {


    private static final Map<String, String> urlMap = new HashMap<>();

    @PostMapping("/gateway")
    public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String value = new String();

        if (ObjectUtils.isNotEmpty(request.getReader())) {
            value = IOUtils.toString(request.getReader());
        }else{
            return;
        }

        String tag = new String();


        if (StringUtils.isNotBlank(value)) {
            JSONObject jsonObject = JSON.parseObject(value);

            if (ObjectUtils.isNotEmpty(jsonObject.get("tag"))) {


                tag = jsonObject.get("tag").toString();

            }
        }



        request.getRequestDispatcher(urlMap.get(tag)).forward(request, response);

    }
    
}
