package ru.liga.internship.service.forecast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ForecastRange {
    date(1),
    week(7),
    month(30);
    private int range;

}
