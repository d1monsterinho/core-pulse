package service;

import com.my.consumer.TelemetryData;
import com.my.consumer.service.TelemetryConsumerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;

public class TelemetryConsumerServiceTest {

    private TelemetryConsumerService telemetryConsumerService;

    @BeforeEach
    public void setUp() {
        telemetryConsumerService = new TelemetryConsumerService();
    }

    @Test
    public void consume_shouldIncrementMessageCountForGivenDeviceById() {
        telemetryConsumerService.consume(new TelemetryData("server_01", 25.5, 1421.2, 1795549802));
        telemetryConsumerService.consume(new TelemetryData("server_02", 34.3, 2221.9, 1795549805));
        telemetryConsumerService.consume(new TelemetryData("server_02", 36.7, 2475.7, 1795549810));

        Map<String, Long> countsSnapshot = telemetryConsumerService.getDeviceMessageCountsSnapshot();

        assertThat(countsSnapshot)
                .hasSize(2)
                .containsEntry("server_01", 1L)
                .containsEntry("server_02", 2L);
    }

    @Test
    public void getDeviceCounts_shouldReturnImmutableMap() {
        Map<String, Long> countsSnapshot = telemetryConsumerService.getDeviceMessageCountsSnapshot();

        assertThatThrownBy(() -> countsSnapshot.put("server_99", 99L)).isInstanceOf(UnsupportedOperationException.class);
    }

}
