package ru.liga.internship.service.forecast;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.liga.internship.domain.MonetaryUnit;
import ru.liga.internship.utils.CsvUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
public class ForecastingService {
    private final CsvUtils csvUtils;
    private final ForecastAlgorithm forecastAlgorithm;
    private LocalDate date;
    private final ForecastRange range;

    public List<MonetaryUnit> rangeForecast(){
        List<MonetaryUnit> outputUnits = new ArrayList<>();
        for (int i = 0; i < this.range.getRange(); i++){
            this.date = date.plusDays(i);
            outputUnits.add(dayForecast());
        }
        return outputUnits;
    }

    private MonetaryUnit dayForecast(){
        return this.forecastAlgorithm.findCurrency(this.date, this.csvUtils);
    }
}
