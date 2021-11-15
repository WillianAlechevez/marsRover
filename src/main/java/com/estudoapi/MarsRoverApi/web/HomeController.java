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
    public String getHomeView(ModelMap model, Long userId, Boolean createUser) throws InvocationTargetException, IllegalAccessException, IllegalArgumentException {
        HomeDto homeDto = createDefaltHomeDto(userId);

        if (Boolean.TRUE.equals(createUser) && userId == null){
            homeDto = roverService.save(homeDto);
        } else {
            homeDto = roverService.finfByUserId(userId);
            if (homeDto == null){
                homeDto = createDefaltHomeDto(userId);
            }
        }

        MarsRoverApiResponse roverData = roverService.getRoverData(homeDto);
        model.put("roverData", roverData);
        model.put("homeDto", homeDto);
        model.put("validCameras", roverService.getValidCameras().get(homeDto.getMarsApiRoverData()));

        if (!Boolean.TRUE.equals(homeDto.getRememberPreferences()) && userId != null){
            HomeDto defaultHomeDto = createDefaltHomeDto(userId);
            roverService.save(defaultHomeDto);
        }

        return "index";
    }

    private HomeDto createDefaltHomeDto(Long userId){
        HomeDto homeDto = new HomeDto();
        homeDto.setMarsApiRoverData("Opportunity");
        homeDto.setMarsSol(1);
        homeDto.setUserId(userId);
        return homeDto;
    }

    @PostMapping("/")
    public String postHomeView (HomeDto homeDto){
        homeDto = roverService.save(homeDto);
        
        return "redirect:/?userId="+homeDto.getUserId();
    }
}
