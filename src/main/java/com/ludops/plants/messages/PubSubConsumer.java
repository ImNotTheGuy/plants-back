package com.ludops.plants.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.ludops.plants.models.HumidityLevelsMessage;
import com.ludops.plants.websocket.WebsocketService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.MessageFormat;

@RequiredArgsConstructor
@Setter
@Component
@Slf4j
public class PubSubConsumer {
    private final PubSubTemplate pubSubTemplate;
    private final ObjectMapper objectMapper;
    private final WebsocketService websocketService;

    private void consumeMessage(BasicAcknowledgeablePubsubMessage message) {

        String data = message.getPubsubMessage().getData().toStringUtf8();
        try {
            HumidityLevelsMessage humidityLevels = objectMapper.readValue(data, HumidityLevelsMessage.class);
            if (humidityLevels.getSensors() == null || humidityLevels.getId() == null) {
                throw new IOException("Message received is not valid");
            }
            log.info("Received message id : " + humidityLevels.getId());
            websocketService.send(humidityLevels);
        } catch (IOException e) {
            log.error(MessageFormat.format("Error parsing message : {0} - Acknowledging it anyway", message), e);
            message.ack();
        }
        message.ack();
    }

    @PostConstruct
    private void subscribe() {
        pubSubTemplate.subscribe("subscription-sensor-data", this::consumeMessage);
    }
}
