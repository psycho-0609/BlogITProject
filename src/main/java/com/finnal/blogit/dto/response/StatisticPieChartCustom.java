package com.finnal.blogit.dto.response;

import lombok.Data;

@Data
public class StatisticPieChartCustom {
    private Float percent;
    private CustomTopicDTO topic;
    private String color;
}
