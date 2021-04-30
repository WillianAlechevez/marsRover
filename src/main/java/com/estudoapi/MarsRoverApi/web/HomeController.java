package com.estudoapi.MarsRoverApi.web;

import com.estudoapi.MarsRoverApi.dto.HomeDto;
import com.estudoapi.MarsRoverApi.response.MarsRoverApiResponse;
import com.estudoapi.MarsRoverApi.service.MarsRoverApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.InvocationTargetException;

@Controller
public class HomeController {

    @Autowired
    private MarsRoverApiService roverService;

    @GetMapping("/")
    public String getHomeView(ModelMap model, Long userId) throws InvocationTargetException, IllegalAccessException, IllegalArgumentException {
        HomeDto homeDto = new HomeDto();
        homeDto.setMarsApiRoverData("Opportunity");
        homeDto.setMarsSol(1);

        if (userId == null){
            homeDto = roverService.save(homeDto);
        } else {
            homeDto = roverService.finfByUserId(userId);
        }

        MarsRoverApiResponse roverData = roverService.getRoverData(homeDto);
        model.put("roverData", roverData);
        model.put("homeDto", homeDto);
        model.put("validCameras", roverService.getValidCameras().get(homeDto.getMarsApiRoverData()));

        return "index";
    }

    @PostMapping("/")
    public String postHomeView (HomeDto homeDto){
        homeDto = roverService.save(homeDto);
        return "redirect:/?userId="+homeDto.getUserId();
    }
}
