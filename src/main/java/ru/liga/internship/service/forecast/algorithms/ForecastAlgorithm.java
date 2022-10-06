package ru.liga.internship.service.forecast.algorithms;

import ru.liga.internship.domain.MonetaryUnit;
import ru.liga.internship.utils.CsvUtils;

import java.time.LocalDate;

public interface ForecastAlgorithm {
    MonetaryUnit findCurrency(LocalDate date, CsvUtils csvUtils);
}
