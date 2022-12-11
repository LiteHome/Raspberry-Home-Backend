package com.iot.rashome.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/gateway")
public class DispatchController implements ApplicationListener<ContextRefreshedEvent> {

    // tag(sensorEnviroment, camera......) -> url(/sensorEnviroment/, /camera/......)
    private static final Map<String, String> urlMap = new HashMap<>();

    @PostMapping("/")
    public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String tag = request.getHeader("tag");

        if (StringUtils.isNotBlank(tag)) {
            request.getRequestDispatcher(urlMap.get(tag)).forward(request, response);
        }
    }

    // handle url mapping
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();

        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
            .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);

        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

        map.forEach((k, v)->{
            
            Set<String> directPaths = k.getDirectPaths();

            for (String path : directPaths) {
                
                String[] pathSplicWithStash = path.split("/");

                urlMap.put(pathSplicWithStash[1], "/" + pathSplicWithStash[1] + "/");
            }
        });
    }
}
