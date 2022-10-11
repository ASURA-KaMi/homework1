package ru.liga.internship;


import ru.liga.internship.parser.CommandParser;
import ru.liga.internship.domain.Messages;
import ru.liga.internship.parser.StateAndResult;
import ru.liga.internship.parser.StateMachine;
import ru.liga.internship.service.CalculateView;
import ru.liga.internship.service.chart.ChartService;


import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        CommandParser eventHandler = new CommandParser();
        Scanner scanner = new Scanner(System.in);
        Boolean run = true;
        while (run) {
            CalculateView calculateView = new CalculateView(true);
            String input = scanner.nextLine();
            if (input.equals("/help")){
                StateMachine stateMachine = new StateMachine("help");
                stateMachine.setErrorStack(new Stack<>());
                stateMachine.getErrorStack().add(new StateAndResult(false, Messages.HELP_MESSAGE));
                calculateView.calc(stateMachine);
            }else {
                StateMachine stateMachine = eventHandler.parse(input);
                calculateView.calc(stateMachine);
            }
        }
    }
}
