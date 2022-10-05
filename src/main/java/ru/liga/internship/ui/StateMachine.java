package ru.liga.internship.ui;

import lombok.Getter;
import lombok.Setter;
import ru.liga.internship.domain.MonetaryUnit;
import ru.liga.internship.service.forecast.ForecastAlgorithm;
import ru.liga.internship.utils.CsvUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Stack;
import java.util.function.Function;

@Getter
@Setter
public class StateMachine {
    public StateMachine(String command){
        this.command = command;
    }
    private List<CsvUtils> csvUtils;

    private LocalDate date;

    private ForecastAlgorithm forecastAlgorithm;

    private List<List<MonetaryUnit>> results;

    private Stack<Function<StateMachine,StateMachine>> commandStack;
    private String command;
}
