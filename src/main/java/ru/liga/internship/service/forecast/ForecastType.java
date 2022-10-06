package ru.liga.internship.service.forecast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ForecastType {
    myst (new MysticalAlgorithm()),
    lyer (new LastYearAlgorithm()),
    expl (new ExtrapolationAlgorithm());

    private ForecastAlgorithm algorithm;

}
