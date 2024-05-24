package com.ludops.plants.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HumiditySensor {

    @JsonProperty("plant_number")
    private int plantNumber;
    @JsonProperty("value")
    private double value;

}
