package ru.liga.internship.service.forecast;

import ru.liga.internship.domain.MonetaryUnit;
import ru.liga.internship.utils.CsvUtils;
import ru.liga.internship.utils.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ExtrapolationAlgorithm implements ForecastAlgorithm{
    @Override
    public MonetaryUnit findCurrency(LocalDate date, CsvUtils csvUtils) {
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Map<String,String>> valuesByDate = csvUtils.getValuesList(30, 30);
        valuesByDate.stream().forEach(values ->{
            LocalDate localDate = DateUtils.localDateFromString(values.get("data"));
            Double currency = Double.valueOf(values.get("curs"));
            x.add((double) localDate.toEpochDay());
            y.add(currency);
        });
        LinearRegression linearRegression = new LinearRegression(x.toArray(new Double[0]),y.toArray(new Double[0]));
        return new MonetaryUnit(valuesByDate.get(0), date, (float) linearRegression.predict(date.toEpochDay()));
    }
}
