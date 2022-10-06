package ru.liga.internship;


import ru.liga.internship.parser.CommandParser;
import ru.liga.internship.parser.StateMachine;
import ru.liga.internship.service.forecast.service.CalculateView;


import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        CommandParser eventHandler = new CommandParser();
        Scanner scanner = new Scanner(System.in);
        Boolean run = true;
        while (run) {
            StateMachine stateMachine = eventHandler.parse(scanner.nextLine());
            CalculateView calculateView = new CalculateView(true);
            calculateView.calc(stateMachine);
        }
    }
}
