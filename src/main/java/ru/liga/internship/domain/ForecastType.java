package ru.liga.internship.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.liga.internship.service.forecast.algorithms.ExtrapolationAlgorithm;
import ru.liga.internship.service.forecast.algorithms.ForecastAlgorithm;
import ru.liga.internship.service.forecast.algorithms.LastYearAlgorithm;
import ru.liga.internship.service.forecast.algorithms.MysticalAlgorithm;

import java.util.Arrays;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum ForecastType {
    myst (new MysticalAlgorithm()),
    lyer (new LastYearAlgorithm()),
    expl (new ExtrapolationAlgorithm());

    private ForecastAlgorithm algorithm;

    public String getTypes(){
        return "[" + Arrays.stream(ForecastType.values()).map(ForecastType::name).collect(Collectors.joining(",")) + "]";
    }

}
