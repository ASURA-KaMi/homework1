package ru.liga.internship.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum ForecastRange {
    date(1),
    week(7),
    month(30);
    private int range;

    public String getRanges(){
        return "[" + Arrays.stream(ForecastRange.values()).map(ForecastRange::name).filter(s -> !s.equals("date")).collect(Collectors.joining(",")) + "]";
    }
}
