package com.ludops.plants.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HumidityLevelsMessage {

    @JsonProperty("message_id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("sensors")
    private List<HumiditySensor> sensors;

}
