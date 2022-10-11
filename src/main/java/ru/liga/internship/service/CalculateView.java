package ru.liga.internship.service;

import lombok.AllArgsConstructor;
import org.jfree.chart.JFreeChart;
import ru.liga.internship.domain.Messages;
import ru.liga.internship.domain.MonetaryUnit;
import ru.liga.internship.parser.StateMachine;
import ru.liga.internship.service.chart.ChartService;
import ru.liga.internship.service.forecast.ForecastingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CalculateView {
    private boolean isEnglish;

    public void calc(StateMachine stateMachine) throws IOException {
        if (stateMachine.getErrorStack() == null) {
            List<List<MonetaryUnit>> lists = new ArrayList<>();
            for (int i = 0; i < stateMachine.getCsvUtils().size(); i++) {
                ForecastingService forecastingService = new ForecastingService(
                        stateMachine.getCsvUtils().get(i),
                        stateMachine.getForecastAlgorithm(),
                        stateMachine.getDate(),
                        stateMachine.getForecastRange());
                lists.add(forecastingService.rangeForecast());
            }
            System.out.println(lists);
            ChartService chartService = new ChartService(lists);
            chartService.draw();
        }else{
            System.out.println(stateMachine.getErrorStack().stream()
                    .map(stateAndResult -> {
                        if (stateAndResult.getNumber() != null)
                            return argsWithNumbers(stateAndResult.getMessages(), stateAndResult.getNumber()) + stateAndResult.getErrorPart();
                        else
                            return isEnglish ? stateAndResult.getMessages().getEnglish() : stateAndResult.getMessages().getRussian() + stateAndResult.getErrorPart();
                    }).collect(Collectors.joining("\n")));
        }
    }
    private String argsWithNumbers(Messages messages, Integer numbers){
        if (isEnglish)
            return messages.getEnglishInvalidByNumber(numbers);
        else
            return messages.getRussianInvalidByNumber(numbers);
    }
}
