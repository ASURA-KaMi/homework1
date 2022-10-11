package ru.liga.internship.service.forecast.algorithms;

import ru.liga.internship.domain.MonetaryUnit;
import ru.liga.internship.utils.CsvReader;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class MysticalAlgorithm implements ForecastAlgorithm{
    @Override
    public MonetaryUnit findCurrency(LocalDate date, CsvReader csvReader) {
        Random random = new Random();
        List<Integer> minMax = csvReader.findMinMaxDate();
        if (date.minusYears(1).getYear() < minMax.get(1))
            minMax.add(1, date.getYear());
        int diff = minMax.get(1) - minMax.get(0);
        LocalDate randomDate = date.minusYears(date.getYear()).plusYears(random.nextLong(diff) + minMax.get(0));
        return new MonetaryUnit(csvReader.findValuesByDate(randomDate), date);
    }
}
