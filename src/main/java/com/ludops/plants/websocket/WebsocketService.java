package com.ludops.plants.websocket;

import com.ludops.plants.models.HumidityLevelsMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebsocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void send(HumidityLevelsMessage message) {
        this.messagingTemplate.convertAndSend("/topic/humidity-levels", message);
    }
}
