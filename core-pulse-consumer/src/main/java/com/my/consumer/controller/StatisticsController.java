package com.my.consumer.controller;

import com.my.consumer.service.TelemetryConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/statistics")
public class StatisticsController {

    private final TelemetryConsumerService telemetryConsumerService;

    public StatisticsController(TelemetryConsumerService telemetryConsumerService) {
        this.telemetryConsumerService = telemetryConsumerService;
    }

    @GetMapping("/device-message-counts")
    public Map<String, Long> getDeviceMessageCounts() {
        return telemetryConsumerService.getDeviceMessageCountsSnapshot();
    }
}
