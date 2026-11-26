package controller;

import com.my.consumer.controller.StatisticsController;
import com.my.consumer.service.TelemetryConsumerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticsControllerTest {

    @Mock
    private TelemetryConsumerService telemetryConsumerService;

    @InjectMocks
    private StatisticsController statisticsController;

    @Test
    public void getMessageCounts_shouldCallServiceAndReturnMessageCountsPerDevice() {
        Map<String, Long> expectedCounts = Map.of("server_01", 251_000L, "server_02", 333_333L, "server_99", 999_999L);

        when(telemetryConsumerService.getDeviceMessageCountsSnapshot()).thenReturn(expectedCounts);

        Map<String, Long> actualStatistics = statisticsController.getDeviceMessageCounts();

        assertThat(actualStatistics).isEqualTo(expectedCounts);
        verify(telemetryConsumerService).getDeviceMessageCountsSnapshot();
    }
}
