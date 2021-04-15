package com.estudoapi.MarsRoverApi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter @Setter
public class MarsRover {
    private Long id;
    private String name;
    @JsonProperty("landing_date")
    private String landingDate;
    @JsonProperty("louch_date")
    private String launchDate;

}
