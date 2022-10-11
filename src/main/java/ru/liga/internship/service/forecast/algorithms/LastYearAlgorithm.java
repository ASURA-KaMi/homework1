package ru.liga.internship.service.forecast.algorithms;

import ru.liga.internship.domain.MonetaryUnit;
import ru.liga.internship.utils.CsvReader;

import java.time.LocalDate;
import java.util.List;

public class LastYearAlgorithm implements ForecastAlgorithm{
    @Override
    public MonetaryUnit findCurrency(LocalDate date, CsvReader csvReader) {
        List<Integer> minMax = csvReader.findMinMaxDate();
        LocalDate lastYearDate;
        if (date.minusYears(1).getYear() < minMax.get(1))
            lastYearDate = date.minusYears(1);
        else
            lastYearDate = date.minusYears(date.getYear()).plusYears(minMax.get(1));
        return new MonetaryUnit(csvReader.findValuesByDate(lastYearDate), date);
    }
}
