package com.ludops.plants.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.ludops.plants.models.HumidityLevelsMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class PubSubConsumer {

    private final PubSubTemplate pubSubTemplate;
    private final ObjectMapper objectMapper;

    private void consumeMessage(BasicAcknowledgeablePubsubMessage message) {

        String data = message.getPubsubMessage().getData().toStringUtf8();
        try {
            HumidityLevelsMessage humidityLevels = objectMapper.readValue(data, HumidityLevelsMessage.class);
            System.out.println("Received message id : " + humidityLevels.getId());
            humidityLevels.getSensors().stream().forEach(sensor -> {
                System.out.println("Plant number : " + sensor.getPlantNumber());
                System.out.println("Humidity level : " + sensor.getValue());
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(data);
        message.ack();
    }

    @PostConstruct
    private void subscribe() {
        pubSubTemplate.subscribe("subscription-sensor-data", this::consumeMessage);
    }
}
