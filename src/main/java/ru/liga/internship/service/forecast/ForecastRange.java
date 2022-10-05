package ru.liga.internship.service.forecast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ForecastRange {
    week(7),
    month(30);
    private int range;

}
