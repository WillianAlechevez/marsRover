package com.estudoapi.MarsRoverApi.web;

import com.estudoapi.MarsRoverApi.dto.HomeDto;
import com.estudoapi.MarsRoverApi.response.MarsRoverApiResponse;
import com.estudoapi.MarsRoverApi.service.MarsRoverApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

@Controller
public class HomeController {

    @Autowired
    private MarsRoverApiService roverService;

    @GetMapping("/")
    public String getHomeView(ModelMap model, HomeDto homeDto) throws InvocationTargetException, IllegalAccessException {
        //if request param is empty, then set a default value
        if (StringUtils.isEmpty(homeDto.getMarsApiRoverData())){
            homeDto.setMarsApiRoverData("Oportunity");
        }
        if (homeDto.getMarsSol() == null){
            homeDto.setMarsSol(1);
        }
        MarsRoverApiResponse roverData = roverService.getRoverData(homeDto);
        model.put("roverData", roverData);
        model.put("homeDto", homeDto);
        model.put("validCameras", roverService.getValidCameras());

        return "index";
    }
}
