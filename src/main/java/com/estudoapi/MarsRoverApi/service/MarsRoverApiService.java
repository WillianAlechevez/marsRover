package com.estudoapi.MarsRoverApi.service;

import com.estudoapi.MarsRoverApi.response.MarsRoverApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MarsRoverApiService {

    public MarsRoverApiResponse getRoverData(String roverType){
        RestTemplate rt = new RestTemplate();

        ResponseEntity<MarsRoverApiResponse> response = rt.getForEntity("https://api.nasa.gov/mars-photos/api/v1/rovers/"+roverType+"/photos?sol=2&api_key=DEMO_KEY",
                MarsRoverApiResponse.class);

        return response.getBody();
    }
}
