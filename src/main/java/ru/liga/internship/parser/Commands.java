package ru.liga.internship.parser;

import lombok.Getter;
import ru.liga.internship.domain.MonetaryUnit;
import ru.liga.internship.service.forecast.ForecastRange;
import ru.liga.internship.service.forecast.ForecastType;
import ru.liga.internship.service.forecast.ForecastingService;
import ru.liga.internship.utils.CsvUtils;
import ru.liga.internship.utils.DateUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

@Getter
public class Commands {
    private List<Function<String, StateAndResult>> commandLineReader;
    private Stack<Function<StateMachine,StateMachine>> commandStack;
    private Stack<StateMachine> states;

    private Integer prognosisLength = 8;
    public Commands(){
        commandLineReader = new ArrayList<>();
        commandStack = new Stack<>();
        states = new Stack<>();
        setupCommandLineReader();

    }
    public void calculate(){
        states.add(commandStack.pop().apply(states.pop()));
    }

    private Function<StateMachine, StateMachine> currencyExchanger = s -> {
        List<CsvUtils> csvUtils = new ArrayList<>();
        Arrays.stream(s.getCommand().split(",")).forEach(currency -> csvUtils.add(new CsvUtils(currency)));
        s.setCsvUtils(csvUtils);
        return s;
    };

    private Function<StateMachine, StateMachine> setForecastRange = s -> {
            if (s.getIsSingletonList()){
                if (s.getCommand().equals("tomorrow")){
                    s.setDate(LocalDate.now().plusDays(1));
                    s.setForecastRange(ForecastRange.date);
                }else {
                    s.setDate(DateUtils.dateFromInputString(s.getCommand()));
                    s.setForecastRange(ForecastRange.date);
                }
            }else {
                s.setIsSingletonList(false);
                s.setDate(LocalDate.now());
                s.setForecastRange(ForecastRange.valueOf(s.getCommand()));
            }

//rate EUR -date tomorrow -alg myst
        return commandStack.pop().apply(s.copy(states.pop()));
    };

    private Function<StateMachine, StateMachine> setForecastAlgorithm = s -> {
            s.setForecastAlgorithm(ForecastType.valueOf(s.getCommand()).getAlgorithm());
        return commandStack.pop().apply(s.copy(states.pop()));
    };

    private void setupCommandLineReader(){
        commandLineReader.add(firstStep);
        commandLineReader.add(secondStep);
        commandLineReader.add(thirdStep);
        commandLineReader.add(fourthStep);
        commandLineReader.add(fifthStep);
        commandLineReader.add(sixthStep);
        commandLineReader.add(seventhStep);
        commandLineReader.add(eighthStep);
    }
    private final Function<String,StateAndResult> firstStep = s -> {
        StateAndResult stateAndResult = new StateAndResult();
        if (!s.equals("rate")){
            stateAndResult.setState(false);
            stateAndResult.setMessages(Messages.INVALID_ARGUMENT);
            stateAndResult.setErrorPart(s);
        } else
            stateAndResult.setState(true);
        return stateAndResult;
    };


    private final Function<String,StateAndResult> secondStep = s -> {
        StateAndResult stateAndResult = new StateAndResult();
        List<String> currenciesInString = Arrays.stream(s.split(",")).map(String::strip).toList();
        List<String> currencies = Arrays.stream(CurrencyCode.values()).map(CurrencyCode::name).toList();
        if (!currencies.containsAll(currenciesInString)){
            stateAndResult.setState(false);
            stateAndResult.setMessages(Messages.INVALID_ARGUMENT);
            stateAndResult.setErrorPart(s);
        } else{
            states.add(new StateMachine(s));
            commandStack.add(currencyExchanger);
            stateAndResult.setState(true);
        }
        return stateAndResult;
    };

    private final Function<String,StateAndResult> thirdStep = s -> {
        StateAndResult stateAndResult = new StateAndResult();
        stateAndResult.setState(true);
        states.add(new StateMachine(s));
        if (s.equals("-period")){
            prognosisLength = 8;
        } else if (s.equals("-date")){
            prognosisLength = 6;
        }else{
            stateAndResult.setState(false);
            stateAndResult.setMessages(Messages.INVALID_ARGUMENT);
            stateAndResult.setErrorPart(s);
        }
        return stateAndResult;
    };
    private final Function<String,StateAndResult> fourthStep = s -> {
        StateAndResult stateAndResult = new StateAndResult();
        stateAndResult.setState(true);
        StateMachine stateMachine = new StateMachine(s);
        String lastCommand = states.pop().getCommand();
        if (lastCommand.equals("-period")){
            if (Arrays.stream(ForecastRange.values()).anyMatch(range -> range.name().equals(s))){
                stateMachine.setIsSingletonList(false);
                states.add(stateMachine);
                commandStack.add(setForecastRange);
            }else {
                stateAndResult.setState(false);
                stateAndResult.setMessages(Messages.INVALID_ARGUMENT);
                stateAndResult.setErrorPart(s);
            }
        } else if (lastCommand.equals("-date")){
            if(s.equals("tomorrow") || DateUtils.isValid(s)){
                stateMachine.setIsSingletonList(true);
                states.add(stateMachine);
                commandStack.add(setForecastRange);
            }else {
                stateAndResult.setState(false);
                stateAndResult.setMessages(Messages.INVALID_ARGUMENT);
                stateAndResult.setErrorPart(s);
            }
        }
        return stateAndResult;
    };

    private final Function<String,StateAndResult> fifthStep = s -> {
        StateAndResult stateAndResult = new StateAndResult();
        stateAndResult.setState(true);
        if (!s.equals("-alg")){
            stateAndResult.setState(false);
            stateAndResult.setMessages(Messages.INVALID_ARGUMENT);
            stateAndResult.setErrorPart(s);
        }
        return stateAndResult;
    };

    private final Function<String,StateAndResult> sixthStep = s -> {
        StateAndResult stateAndResult = new StateAndResult();
        stateAndResult.setState(true);
        List<String> algorithmsNames = Arrays.stream(ForecastType.values()).map(ForecastType::name).toList();
        if (!algorithmsNames.contains(s)){
            stateAndResult.setState(false);
            stateAndResult.setMessages(Messages.INVALID_ARGUMENT);
            stateAndResult.setErrorPart(s);
        }
        states.add(new StateMachine(s));
        commandStack.add(setForecastAlgorithm);
        return stateAndResult;
    };

    private final Function<String,StateAndResult> seventhStep = s -> {
        StateAndResult stateAndResult = new StateAndResult();
        stateAndResult.setState(true);
        if (!s.equals("-output")){
            stateAndResult.setState(false);
            stateAndResult.setMessages(Messages.INVALID_ARGUMENT);
            stateAndResult.setErrorPart(s);
        }
        return stateAndResult;
    };

    private final Function<String,StateAndResult> eighthStep = s -> {
        StateAndResult stateAndResult = new StateAndResult();
        stateAndResult.setState(true);
        if (s.equals("graph")) {
            states.peek().setIsGraph(true);
        }else if (s.equals("list")){
            states.peek().setIsGraph(false);
        }else{
            stateAndResult.setState(false);
            stateAndResult.setMessages(Messages.INVALID_ARGUMENT);
            stateAndResult.setErrorPart(s);
        }
        return stateAndResult;
    };
}
