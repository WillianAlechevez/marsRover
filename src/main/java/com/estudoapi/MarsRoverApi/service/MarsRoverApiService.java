package com.estudoapi.MarsRoverApi.service;

import com.estudoapi.MarsRoverApi.dto.HomeDto;
import com.estudoapi.MarsRoverApi.response.MarsPhoto;
import com.estudoapi.MarsRoverApi.response.MarsRoverApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class MarsRoverApiService {

    private Map<String, List<String>> validCameras = new HashMap<>();

    public MarsRoverApiService(){
        validCameras.put("Opportunity", Arrays.asList("FHAZ", "RHAZ", "NAVCAM", "PANCAM", "MINITES"));
        validCameras.put("Curiosity", Arrays.asList("FHAZ", "RHAZ", "MAST", "CHEMCAM", "MAHLI", "MARDI", "NAVCAM"));
        validCameras.put("Spirit", Arrays.asList("FHAZ", "RHAZ", "NAVCAM", "PANCAM", "MINITES"));
    }

    public MarsRoverApiResponse getRoverData(HomeDto homeDto) throws InvocationTargetException, IllegalAccessException {
        RestTemplate rt = new RestTemplate();

        List<String> apiUrlEndPoints = getApiUrlEndpoints(homeDto);
        List<MarsPhoto> photos = new ArrayList<>();
        MarsRoverApiResponse response = new MarsRoverApiResponse();

        apiUrlEndPoints.stream()
                       .forEach(url -> {
                           MarsRoverApiResponse apiResponse = rt.getForObject(url, MarsRoverApiResponse.class);
                           photos.addAll(apiResponse.getPhotos());
                       });

        response.setPhotos(photos);

        return response;
    }

    public List<String> getApiUrlEndpoints (HomeDto homeDto) throws InvocationTargetException, IllegalArgumentException, IllegalAccessException {
        List<String> urls = new ArrayList<>();

        Method[] methods = homeDto.getClass().getMethods();

        //Esse código pega todos os getCamera* métodos e, em caso de serem TRUE, construímos a url
        //que contém as imagens conforme o rover / camera/ sol
        for(Method method : methods){
            if(method.getName().indexOf("getCamera") > -1 && Boolean.TRUE.equals(method.invoke(homeDto))){
                String cameraName = method.getName().split("getCamera")[1].toUpperCase();
                if(validCameras.get(homeDto.getMarsApiRoverData()).contains(cameraName)){
                    urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=DEMO_KEY" + "&camera=" + cameraName);
                }
            }
        }
        return urls;
    }
    public Map<String, List<String>> getValidCameras() {
        return validCameras;
    }
}
