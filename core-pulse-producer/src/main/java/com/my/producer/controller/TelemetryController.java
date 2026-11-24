package com.my.producer.controller;

import com.my.producer.TelemetryData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/metrics")
public class TelemetryController {

    private static final String TOPIC = "core-pulse-telemetry";

    private final KafkaTemplate<String, TelemetryData> kafkaTemplate;

    public TelemetryController(KafkaTemplate<String, TelemetryData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<Void> ingestMetrics(@RequestBody TelemetryData data) {
        String deviceId = data.deviceId();
        log.info("Telemetry for device received: {}", deviceId);

        kafkaTemplate.send(TOPIC, deviceId, data);

        return ResponseEntity.accepted().build();
    }
}
