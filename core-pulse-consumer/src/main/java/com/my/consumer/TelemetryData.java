package com.my.consumer;

public record TelemetryData(
        String deviceId,
        double cpuUsage,
        double memoryUsage,
        long timestamp
) {
}
