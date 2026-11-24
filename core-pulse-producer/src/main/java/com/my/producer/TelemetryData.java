package com.my.producer;

public record TelemetryData(
        String deviceId,
        double cpuUsage,
        double memoryUsage,
        long timestamp
) {
}
