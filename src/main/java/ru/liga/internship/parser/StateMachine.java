package ru.liga.internship.parser;

import lombok.Getter;
import lombok.Setter;
import ru.liga.internship.service.forecast.ForecastAlgorithm;
import ru.liga.internship.domain.ForecastRange;
import ru.liga.internship.utils.CsvUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Stack;

@Getter
@Setter
public class StateMachine {
    public StateMachine copy(StateMachine stateMachine) {
        this.csvUtils = this.csvUtils == null ? stateMachine.getCsvUtils() : this.csvUtils;
        this.date = this.date == null ? stateMachine.getDate() : this.date;
        this.forecastAlgorithm = this.forecastAlgorithm == null ? stateMachine.getForecastAlgorithm() : this.forecastAlgorithm;
        this.forecastRange = this.forecastRange == null ? stateMachine.getForecastRange() : this.forecastRange;
        this.errorStack = this.errorStack == null ? stateMachine.getErrorStack() : this.errorStack;
        this.isSingletonList = this.isSingletonList == null ? stateMachine.getIsSingletonList() : this.isSingletonList;
        this.isGraph = this.isGraph == null ? stateMachine.getIsGraph() : this.isGraph;
        this.command = stateMachine.getCommand();
        return this;
    }

    public StateMachine(String command){
        this.command = command;
    }
    private List<CsvUtils> csvUtils;
    private LocalDate date;
    private ForecastAlgorithm forecastAlgorithm;

    private ForecastRange forecastRange;

    private Stack<StateAndResult> errorStack;

    private Boolean isSingletonList;

    private Boolean isGraph;
    private String command;
}
