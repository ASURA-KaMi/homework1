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
@NoArgsConstructor
@Setter
public class ForecastingService {
    private CsvUtils csvUtils;
    private ForecastAlgorithm forecastAlgorithm;
    private LocalDate date;
    private ForecastRange range;

    public List<MonetaryUnit> rangeForecast(){
        List<MonetaryUnit> outputUnits = new ArrayList<>();
        for (int i = 0; i < this.range.getRange(); i++){
            this.date = date.plusDays(i);
            outputUnits.add(dayForecast());
        }
        return outputUnits;
    }

    public MonetaryUnit dayForecast(){
        return this.forecastAlgorithm.findCurrency(this.date, this.csvUtils);
    }
}
