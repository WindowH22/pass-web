package com.fastcampus.pass.service.statistics;

import com.fastcampus.pass.repository.statistics.StatisticsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceTest {

    @Mock
    private StatisticsRepository statisticsRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    public void test_makeChartData(){
        //given
        final LocalDateTime to = LocalDateTime.of(2022,9,10,0,0);

        List<AggregatedStatistics> statisticsList = List.of(
                new AggregatedStatistics(to.minusDays(1), 15,10,5),
                new AggregatedStatistics(to, 10,8,2)
        );

        //when
        when(statisticsRepository.findByStatisticsAtBetweenAndGroupBy(eq(to.minusDays(10)),eq(to))).thenReturn(statisticsList);
        final ChartData chartData = statisticsService.makeChartData(to);

        //then
        verify(statisticsRepository, times(1)).findByStatisticsAtBetweenAndGroupBy(eq(to.minusDays(10)),eq(to));

        assertNotNull(chartData);
        assertEquals(new ArrayList<>(List.of("09-09","09-10")),chartData.getLabels());
        assertEquals(new ArrayList<>(List.of(10L,8L)),chartData.getAttendedCounts());
        assertEquals(new ArrayList<>(List.of(5L,2L)),chartData.getCancelledCounts());


    }
}
