package com.my.producer.controller;

import com.my.producer.TelemetryData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TelemetryControllerTest {

    private static final String TOPIC = "core-pulse-telemetry";

    @Mock
    private KafkaTemplate<String, TelemetryData> kafkaTemplate;

    @InjectMocks
    private TelemetryController telemetryController;

    @Test
    public void ingestMetrics_shouldSendMessageToBrokerAndReturnAccepted() {
        String messageId = "server_01";
        int cpuUsage = 54;
        int memoryUsage = 6188;
        long timestamp = 1792859914;

        TelemetryData message = new TelemetryData(messageId, cpuUsage, memoryUsage, timestamp);

        ResponseEntity<Void> responseEntity = telemetryController.ingestMetrics(message);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        verify(kafkaTemplate).send(TOPIC, messageId, message);
    }
}
