package ru.liga.internship.ui;

import lombok.Getter;
import ru.liga.internship.service.forecast.ExtrapolationAlgorithm;
import ru.liga.internship.service.forecast.ForecastRange;
import ru.liga.internship.service.forecast.ForecastingService;
import ru.liga.internship.utils.CsvUtils;
import ru.liga.internship.utils.DateUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

@Getter
public class Commands {
    private List<Function<String, StateAndResult>> commandLineReader;
    Stack<Function<StateMachine,StateMachine>> commandStack;
    Stack<StateMachine> states;
    public Commands(){
        commandLineReader = new ArrayList<>();
        commandStack = new Stack<>();
        states = new Stack<>();
        setupCommandLineReader();

    }
    public void calculate(){
        System.out.println(commandStack.pop().apply(states.pop()).getCsvUtils().get(0).findValuesByDate(LocalDate.now()));
    }

    private void setupCommandLineReader(){
        commandLineReader.add(firstStep);
        commandLineReader.add(secondStep);
        commandLineReader.add(thirdStep);
        commandLineReader.add(fourthStep);
    }
    private Function<String,StateAndResult> firstStep = s -> {
        StateAndResult stateAndResult = new StateAndResult();
        stateAndResult.setPrognosisLength(2);
        if (!s.equals("rate")){
            stateAndResult.setState(false);
            stateAndResult.setResult("Неверный первый аргумент");
        } else
            stateAndResult.setState(true);
        return stateAndResult;
    };

    private Function<StateMachine, StateMachine> currencyExchanger = s -> {
        List<CsvUtils> csvUtils = new ArrayList<>();
        Arrays.stream(s.getCommand().split(",")).forEach(currency -> csvUtils.add(new CsvUtils(currency)));
        s.setCsvUtils(csvUtils);
        return s;
    };

    private Function<StateMachine, StateMachine> calculateByPeriod = s -> {
        String periodCommand = states.pop().getCommand();
        s = commandStack.pop().apply(states.pop());
        ForecastingService forecastingService = new ForecastingService();

        for (int i = 0; i < s.getCsvUtils().size(); i++){
            if (periodCommand.equals(ForecastRange.week.name())){
                forecastingService.setForecastAlgorithm(s.getForecastAlgorithm());
                forecastingService.setDate(LocalDate.now());
                forecastingService.setRange(ForecastRange.week);
                forecastingService.setCsvUtils(s.getCsvUtils().get(i));
                System.out.println(forecastingService.rangeForecast());
            }else if (periodCommand.equals(ForecastRange.month.name())){
                forecastingService.setForecastAlgorithm(s.getForecastAlgorithm());
                forecastingService.setDate(LocalDate.now());
                forecastingService.setRange(ForecastRange.month);
                forecastingService.setCsvUtils(s.getCsvUtils().get(i));
                System.out.println(forecastingService.rangeForecast());
            }else if (periodCommand.equals("tomorrow")){
                forecastingService.setForecastAlgorithm(s.getForecastAlgorithm());
                forecastingService.setDate(LocalDate.now().plusDays(1));
                forecastingService.setCsvUtils(s.getCsvUtils().get(i));
                System.out.println(forecastingService.dayForecast());
            }else {
                forecastingService.setForecastAlgorithm(s.getForecastAlgorithm());
                forecastingService.setDate(DateUtils.dateFromInputString(periodCommand));
                forecastingService.setCsvUtils(s.getCsvUtils().get(i));
                System.out.println(forecastingService.dayForecast());
            }

        }
        return s;
    };
    private Function<String,StateAndResult> secondStep = s -> {
        StateAndResult stateAndResult = new StateAndResult();
        stateAndResult.setPrognosisLength(8);
        if (!Arrays.stream(s.split(","))
                .allMatch(s1 -> Arrays.stream(CurrencyCode.values())
                        .anyMatch(s2 -> s1.equals(s2.name())))){
            stateAndResult.setState(false);
            stateAndResult.setResult("Неверный код валюты");
        } else
            states.add(new StateMachine(s));
            commandStack.add(currencyExchanger);
            stateAndResult.setState(true);
        return stateAndResult;
    };

    private Function<String,StateAndResult> thirdStep = s -> {
        StateAndResult stateAndResult = new StateAndResult();
        stateAndResult.setPrognosisLength(8);
        stateAndResult.setState(true);
        states.add(new StateMachine(s));
        if (s.equals("-period")){
            stateAndResult.setPrognosisLength(8);
        } else if (s.equals("-date")){
            stateAndResult.setPrognosisLength(6);
        }else{
            stateAndResult.setState(false);
            stateAndResult.setResult("Неверный третий аргумент");
        }
        return stateAndResult;
    };
    private Function<String,StateAndResult> fourthStep = s -> {
        StateAndResult stateAndResult = new StateAndResult();
        stateAndResult.setPrognosisLength(8);
        stateAndResult.setState(true);
        String lastCommand = states.pop().getCommand();
        if (lastCommand.equals("-period")){
            stateAndResult.setPrognosisLength(8);
            if (Arrays.stream(ForecastRange.values()).anyMatch(range -> range.name().equals(s))){
                states.add(new StateMachine(s));
                commandStack.add(calculateByPeriod);
                commandStack.pop().apply(new StateMachine(s));
            }else {
                stateAndResult.setState(false);
                stateAndResult.setResult("Неверный тип для флага -period");
            }
        } else if (lastCommand.equals("-date")){
            stateAndResult.setPrognosisLength(6);
            if(s.equals("tomorrow") || DateUtils.isValid(s)){
                states.add(new StateMachine(s));
                commandStack.add(calculateByPeriod);
                commandStack.pop().apply(new StateMachine(s));
            }else {
                stateAndResult.setState(false);
                stateAndResult.setResult("Неверный тип для флага -date");
            }
        }
        return stateAndResult;
    };
}
