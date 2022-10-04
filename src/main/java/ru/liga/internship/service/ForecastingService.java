package ru.liga.internship.service;

import ru.liga.internship.domain.MonetaryUnit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ForecastingService {
    public static List<MonetaryUnit> weekForecast(List<MonetaryUnit> monetaryUnits){
        Integer offset = monetaryUnits.size();
        List<MonetaryUnit> outputUnits = new ArrayList<>();
        outputUnits.addAll(monetaryUnits);
        for (int i = 0; i < offset; i++){
            outputUnits.add(dayForecast(outputUnits, i));
            outputUnits.remove(0);
        }
        return outputUnits;
    }

    public static MonetaryUnit dayForecast(List<MonetaryUnit> monetaryUnits, Integer dayOffset){
        Float rate = (float) monetaryUnits.stream()
                .mapToDouble(MonetaryUnit::getRatePerUnit)
                .average()
                .getAsDouble();
        return new MonetaryUnit(monetaryUnits.get(0).getMultiplier(),
                LocalDate.now().plusDays(1 + dayOffset),
                rate * monetaryUnits.get(0).getMultiplier(),
                monetaryUnits.get(0).getCdx());
    }
}
