package ru.liga.internship.ui;

import lombok.Getter;
import ru.liga.internship.domain.MonetaryUnit;
import ru.liga.internship.service.ForecastingService;
import ru.liga.internship.utils.CsvUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
public class Commands {
    private Map<String, Function> firstStep = new HashMap<>();
    private Set<String> secondStep = new HashSet<>();
    private Map<String, Function> thirdStep = new HashMap<>();
    public Commands(){
        Function<Scanner, Boolean> quit = scanner -> {
            scanner.close();
            return false;
        };
        Function<Scanner, Boolean> rate = scanner -> {
            System.out.println("Недостаточное количество аргументов");
            return true;
        };
        Function<String, String> tomorrow = code -> {
            CsvUtils csvUtils = new CsvUtils(code);
            String output =
            ForecastingService.dayForecast(csvUtils.getValuesList(7).stream()
                    .map(MonetaryUnit::new)
                    .toList(), 0).toString();
            return output;
        };
        Function<String, String> week = code -> {
            CsvUtils csvUtils = new CsvUtils(code);
            String output =
                    ForecastingService.weekForecast(csvUtils.getValuesList(7).stream()
                            .map(MonetaryUnit::new)
                            .toList()).stream().map(monetaryUnit -> monetaryUnit.toString() + "\n").collect(Collectors.joining(""));
            return output;
        };


        firstStep.put("quit", quit);
        firstStep.put("rate", rate);
        secondStep.add("EUR");
        secondStep.add("USD");
        secondStep.add("TRY");
        thirdStep.put("tomorrow", tomorrow);
        thirdStep.put("week", week);
    }
}
