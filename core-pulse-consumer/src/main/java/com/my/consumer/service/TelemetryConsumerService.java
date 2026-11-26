package com.my.consumer.service;

import com.my.consumer.TelemetryData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
@Service
public class TelemetryConsumerService {
    private static final String TOPIC = "core-pulse-telemetry";

    private final Map<String, LongAdder> deviceMessageCounts = new ConcurrentHashMap<>();

    @KafkaListener(topics = TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void consume(TelemetryData data) {
        deviceMessageCounts
                .computeIfAbsent(data.deviceId(), _ -> new LongAdder())
                .increment();
    }

    public Map<String, Long> getDeviceMessageCountsSnapshot() {
        Map<String, Long> snapshot = new ConcurrentHashMap<>();
        deviceMessageCounts.forEach((deviceId, count) -> snapshot.put(deviceId, count.sum()));

        return Collections.unmodifiableMap(snapshot);
    }
}
